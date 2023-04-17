package simulation;

import javafx.util.Pair;
import operations.IOperation;
import operations.binary.IBinary;
import operations.unary.IUnary;

import java.util.*;

public class Simulator {

    private final Processor processor = new Processor();
    private final HashSet<String> keywords = new LinkedHashSet<>();
    private final LinkedList<Pair<IOperation, Argument[]>> operations = new LinkedList<>();
    private final List<String> instructions;
    private final HashMap<String, Integer> labels = new HashMap<>();

    private final HashMap<Long, Long> lineNumberToAddress = new HashMap<>(); // RIP next instruction
    private final HashMap<Byte, String> operationsEncoded = new HashMap<>();

    private final String breakpoint = "BREAKPOINT";
    private final String next = "NEXT";
    private final String _continue = "CONTINUE";
    private final String switchToMachineCode = "SWITCH";
    private long lineNumber = 0;
    private boolean debug = false;

    public Simulator(List<String> instructions) {
        this.instructions = instructions;
        setKeywords();
    }

    private void setKeywords() {
        keywords.addAll(processor.getUnaryOperations().keySet());
        keywords.addAll(processor.getBinaryOperations().keySet());
        keywords.add(breakpoint);
        keywords.add(switchToMachineCode);
    }

    public void run() {
        encodeOperations();
        try {
            analyseInstructions();
        } catch (InvalidCodeException e) {
            System.out.println(e.getMessage());
            return;
        }
        translateToMachineCode();
        interpretCode();
        printRegisterValues();
    }


    private void interpretCode() {
        for (lineNumber = 0; lineNumber < operations.size(); lineNumber++) {
            Pair<IOperation, Argument[]> pair;
            if ((pair = operations.get((int) lineNumber)) != null) {
                IOperation operation = pair.getKey();
                Argument[] args = pair.getValue();
                printPair(operation, args[0], args[1]);
                if (operation instanceof IUnary)
                    ((IUnary) operation).execute(args[0]);
                else if (operation instanceof IBinary)
                    ((IBinary) operation).execute(args[0], args[1]);

                if (debug)
                    debug();
            } else if (breakpoint.equals(instructions.get((int) lineNumber))) {
                debug = true;
                debug();
            } else if (switchToMachineCode.equals(instructions.get((int) lineNumber))) {
                executeMachineCode();
            }
        }
    }

    private void executeMachineCode() {
        for (; ; lineNumber++) {
            if (!lineNumberToAddress.containsKey(lineNumber))
                return;
            if (lineNumberToAddress.get(lineNumber) == 0L)
                continue;
            long instructionAddress = lineNumberToAddress.get(lineNumber);
            byte operationEncoding = processor.getAddresses().get(instructionAddress);
            instructionAddress += 2; // skip delimiter
            String operationName = operationsEncoded.get(operationEncoding);

            // fetch arguments
            byte value;
            StringBuilder sb = new StringBuilder();
            while ((value = processor.getAddresses().get(instructionAddress++)) != (byte) 0xFF) {
                sb.append((char) (int) value);
            }
            String[] args = sb.toString().split(",");
            Argument arg1, arg2;
            if (args.length == 1) {
                arg1 = checkArgument(args[0]);
                IUnary unary = processor.getUnaryOperations().get(operationName);
                unary.execute(arg1);
            } else if (args.length == 2) {
                arg1 = checkArgument(args[0]);
                arg2 = checkArgument(args[1]);
                IBinary binary = processor.getBinaryOperations().get(operationName);
                binary.execute(arg1, arg2);
            } else if (switchToMachineCode.equals(operationName)) {
                System.out.println("SWITCH");
                lineNumber--;
                return;
            }
        }
    }

    private Argument checkArgument(String arg) {
        Argument argument;
        if ((argument = isRegister(arg)) != null)
            return argument;
        else if ((argument = isMemory(arg)) != null)
            return argument;
        else return isNumber(arg);
    }

    private void encodeOperations() {
        operationsEncoded.put((byte) 0, "JE");
        operationsEncoded.put((byte) 1, "JGE");
        operationsEncoded.put((byte) 2, "JL");
        operationsEncoded.put((byte) 3, "JMP");
        operationsEncoded.put((byte) 4, "JNE");
        operationsEncoded.put((byte) 5, "NOT");
        operationsEncoded.put((byte) 6, "PRINT");
        operationsEncoded.put((byte) 7, "SCAN");
        operationsEncoded.put((byte) 8, "ADD");
        operationsEncoded.put((byte) 9, "AND");
        operationsEncoded.put((byte) 10, "MOV");
        operationsEncoded.put((byte) 11, "OR");
        operationsEncoded.put((byte) 12, "SUB");
        operationsEncoded.put((byte) 13, "CMP");
        operationsEncoded.put((byte) 14, "BREAKPOINT");
        operationsEncoded.put((byte) 15, "SWITCH");
    }

    private void translateToMachineCode() {
        long startAddress = 10L;
        processor.getRegisters().put("RIP", startAddress);
        long lineNumber = 0;
        for (Pair<IOperation, Argument[]> pair : operations) {
            if (pair != null) {
                IOperation key = pair.getKey();
                Argument[] args = pair.getValue();
                String operation = key.getClass().getSimpleName().toUpperCase();
                Byte operationByteCode = getOperationByteCode(operation);
                lineNumberToAddress.put(lineNumber, startAddress); // first address instruction
                processor.getAddresses().put(startAddress++, operationByteCode);
                for (Argument arg : args) {
                    processor.getAddresses().put(startAddress++, ",".getBytes()[0]); // delimiter
                    byte[] argByte = new byte[0];
                    if (arg instanceof Register) {
                        String registerName = ((Register) arg).getName();
                        argByte = registerName.getBytes();
                    } else if (arg instanceof Memory) {
                        String memoryAddress = "[0X" + Long.toHexString(((Memory) arg).getAddress()) + "]";
                        argByte = memoryAddress.getBytes();
                    } else if (arg instanceof Constant) {
                        String constantValue = String.valueOf(arg.getValue());
                        argByte = constantValue.getBytes();
                    }
                    for (byte b : argByte) {
                        processor.getAddresses().put(startAddress++, b);
                    }
                }
                processor.getAddresses().put(startAddress++, (byte) 0xFF); // delimiter of instructions
                lineNumber++;
            } else
                lineNumberToAddress.put(lineNumber++, 0L);
        }
    }

    private Byte getOperationByteCode(String operation) {
        for (Map.Entry<Byte, String> entry : operationsEncoded.entrySet()) {
            if (entry.getValue().equals(operation))
                return entry.getKey();
        }
        return 0;
    }

    private void debug() {
        String input;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("1. Pregled vrijednosti svih registara");
            System.out.println("2. Pregled specificirane memorijske adrese");
            System.out.println("3. NEXT");
            System.out.println("4. CONTINUE");
            System.out.print("Unesite opciju: ");
            input = scanner.nextLine();
            if ("1".equals(input)) {
                printRegisterValues();
            } else if ("2".equals(input)) {
                System.out.print("Unesite memorijsku adresu (dekadna vrijednost): ");
                try {
                    Long memoryAddress = Long.parseLong(scanner.nextLine());
                    Byte memoryValue = processor.getAddresses().get(memoryAddress);
                    System.out.printf("Adresa: %d Vrijednost: %d\n", memoryAddress, memoryValue != null ? memoryValue : 0);
                } catch (NumberFormatException e) {
                    System.out.println("Niste unijeli ispravan format adrese.");
                }
            } else if (_continue.equals(input))
                debug = false;
        } while (!_continue.equals(input) && !next.equals(input));
    }

    private void printPair(IOperation operation, Argument arg, Argument arg1) {
        System.out.printf("%s %s, %s\n", operation.getClass().getSimpleName(), arg.getClass().getSimpleName(), arg1);
    }

    private void printRegisterValues() {
        for (Map.Entry<String, Long> entry : processor.getRegisters().entrySet()) {
            System.out.printf("%s=%d\n", entry.getKey(), entry.getValue());
        }
    }

    private void analyseInstructions() throws InvalidCodeException {
        for (int lineNumber = 0; lineNumber < instructions.size(); lineNumber++) {
            analyseInstruction(instructions.get(lineNumber), lineNumber);
        }
    }

    private Memory isMemory(String operand) {
        Memory memory = null;
        if (operand.startsWith("[") && operand.endsWith("]")) {
            operand = operand.replaceAll("\\[", "").replaceAll("\\]", "");
            if (processor.getRegisters().containsKey(operand)) {
                long registerValue = processor.getRegisters().get(operand);
                memory = new Memory(registerValue);
            } else if (operand.matches("0[xX][\\da-fA-F]+")) { // check if hex number
                long address = Long.parseLong(operand.substring(2), 16);
                memory = new Memory(address);
            }
        }
        return memory;
    }

    public Register isRegister(String operand) {
        Register register = null;
        if (!operand.startsWith("[") && processor.getRegisters().containsKey(operand)) {
            register = new Register(operand);
        }
        return register;
    }

    public Constant isNumber(String operand) {
        Constant number = new Constant(Long.MAX_VALUE);
        try {
            if (operand.matches("\\d+")) number.setValue(Long.parseLong(operand));
        } catch (NumberFormatException e) {
            return number;
        }
        return number;
    }

    private Constant isLabel(String operand) {
        Constant lineNumber = new Constant(Long.MAX_VALUE);
        if (labels.containsKey(operand))
            lineNumber = new Constant(labels.get(operand));
        return lineNumber;
    }

    private void analyseInstruction(String instruction, int lineNumber) throws InvalidCodeException {
        String[] components = instruction.split(" ");
        if (components.length < 1 || (!keywords.contains(components[0].toUpperCase())) && !components[0].endsWith(":")) {
            throw new InvalidCodeException();
        }

        String operation = components[0];
        if (components.length == 1) {
            // BREAKPOINT, LABELA, SWITCH
            if (operation.endsWith(":")) { // label
                operations.add(null);
                labels.put(operation.substring(0, operation.length() - 1), lineNumber);
            } else if (breakpoint.equals(operation) || switchToMachineCode.equals(operation)) // breakpoint
                operations.add(null);
        } else if (components.length == 2) {
            String[] operands = components[1].split(",");
            String firstOperand = operands[0].toUpperCase();
            Argument arg1, arg2 = null;
            if (processor.getUnaryOperations().containsKey(operation) && operands.length == 1) { // unary
                IOperation unaryOperation = processor.getUnaryOperations().get(operation);
                if ((arg1 = isMemory(firstOperand)) != null || (arg1 = isRegister(firstOperand)) != null
                        || (("PRINT".equals(components[0]) && (arg1 = isNumber(firstOperand)).getValue() != Long.MAX_VALUE))
                        || ((arg1 = isLabel(firstOperand)).getValue() != Long.MAX_VALUE)) {
                    operations.add(new Pair<>(unaryOperation, new Argument[]{arg1, null}));
                } else throw new InvalidCodeException();
            } else if (processor.getBinaryOperations().containsKey(operation) && operands.length == 2) { // binary
                IOperation binaryOperation = processor.getBinaryOperations().get(operation);
                String secondOperand = operands[1].toUpperCase();
                if ((arg1 = isRegister(firstOperand)) != null && (arg2 = isMemory(secondOperand)) != null // register,[register] || register,[0xnum]
                        || (arg1 = isRegister(firstOperand)) != null && (arg2 = isRegister(secondOperand)) != null // register,register
                        || (arg1 = isRegister(firstOperand)) != null && ((arg2 = isNumber(secondOperand)).getValue() != Long.MAX_VALUE) // register,num
                        || (arg1 = isMemory(firstOperand)) != null && (arg2 = isRegister(secondOperand)) != null) { // [register],register || [0xnum],register
                    operations.add(new Pair<>(binaryOperation, new Argument[]{arg1, arg2}));
                } else throw new InvalidCodeException();
            } else throw new InvalidCodeException();
            // if memory, add to hashmap of addresses
            ifMemoryAddToAddresses(arg1);
            ifMemoryAddToAddresses(arg2);
        } else throw new InvalidCodeException();
    }

    private void ifMemoryAddToAddresses(Argument arg) {
        if (arg instanceof Memory m) {
            processor.getAddresses().put(m.getAddress(), (byte) 0);
        }
    }

    private void printOperations() {
        for (Pair<IOperation, Argument[]> pair : operations) {
            IOperation key = pair.getKey();
            Argument[] value = pair.getValue();
            System.out.printf("%s arg1=%s arg2=%s\n", key.getClass().getSimpleName(), value[0].getClass().getSimpleName(), value[1]);
        }
    }

    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Processor getProcessor() {
        return processor;
    }
}
