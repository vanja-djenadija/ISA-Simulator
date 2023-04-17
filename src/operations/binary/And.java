package operations.binary;

import simulation.Argument;

public class And implements IBinary {

    @Override
    public void execute(Argument arg1, Argument arg2) {
        arg1.setValue(arg1.getValue() & arg2.getValue());
    }
}
