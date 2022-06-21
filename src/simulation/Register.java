package simulation;

public class Register implements Argument {
    private final String name;
    private long value;

    public Register(String name) {
        this.name = name;
        value = 0;
    }

    @Override
    public long getValue() {
        return Main.simulator.getProcessor().getRegisters().get(name);
    }

    @Override
    public void setValue(long value) {
        Main.simulator.getProcessor().getRegisters().put(name, value);
    }

    public String getName() {
        return name;
    }
}
