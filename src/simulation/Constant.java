package simulation;

public class Constant implements Argument {
    private long value;

    public Constant(long value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void setValue(long value) {
        this.value = value;
    }
}
