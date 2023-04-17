package simulation;

import operations.binary.IBinary;
import operations.unary.IUnary;
import operations.unary.*;
import operations.binary.*;


import java.util.HashMap;
import java.util.Map;

public class Processor {

    private Map<String, Long> registers;
    private Map<String, IUnary> unaryOperations;
    private Map<String, IBinary> binaryOperations;
    private HashMap<Long, Byte> addresses = new HashMap<>();

    private boolean cmpEquals;
    private boolean cmpLess;

    public Processor() {
        createRegisters();
        createOperations();
    }

    private void createRegisters() {
        Register rax = new Register("RAX");
        Register rbx = new Register("RBX");
        Register rcx = new Register("RCX");
        Register rdx = new Register("RDX");
        Register rip = new Register("RIP");
        registers = new HashMap<>(
                Map.of(rax.getName(), (long) 0,
                        rbx.getName(), (long) 0,
                        rcx.getName(), (long) 0,
                        rdx.getName(), (long) 0,
                        rip.getName(), (long) 0));
    }

    private void createOperations() {
        unaryOperations = new HashMap<>(
                Map.of("JE", new Je(),
                        "JGE", new Jge(),
                        "JL", new Jl(),
                        "JMP", new Jmp(),
                        "JNE", new Jne(),
                        "NOT", new Not(),
                        "PRINT", new Print(),
                        "SCAN", new Scan()));

        binaryOperations = new HashMap<>(
                Map.of("ADD", new Add(),
                        "AND", new And(),
                        "MOV", new Mov(),
                        "OR", new Or(),
                        "SUB", new Sub(),
                        "CMP", new Cmp()));
    }

    public Map<String, IUnary> getUnaryOperations() {
        return unaryOperations;
    }

    public Map<String, IBinary> getBinaryOperations() {
        return binaryOperations;
    }

    public Map<String, Long> getRegisters() {
        return registers;
    }

    public HashMap<Long, Byte> getAddresses() {
        return addresses;
    }

    public boolean isCmpEquals() {
        return cmpEquals;
    }

    public boolean isCmpLess() {
        return cmpLess;
    }

    public void setCmpEquals(boolean cmpEquals) {
        this.cmpEquals = cmpEquals;
    }

    public void setCmpLess(boolean cmpLess) {
        this.cmpLess = cmpLess;
    }


}
