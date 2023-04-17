package simulation;

public class Memory implements Argument {
    private long address;
    private byte value;


    public Memory(long address) {
        this.address = address;
        value = 0;
    }

    @Override
    public long getValue() {
        return Main.simulator.getProcessor().getAddresses().get(this.getAddress());
    }

    @Override
    public void setValue(long value) {
        Main.simulator.getProcessor().getAddresses().put(address, (byte) value);
    }

    public long getAddress() {
        return address;
    }

    public void setAddress(long address) {
        this.address = address;
    }
}
