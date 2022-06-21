package operations.binary;

import simulation.Argument;

public class Mov implements IBinary {

    @Override
    public void execute(Argument arg1, Argument arg2) {
        arg1.setValue(arg2.getValue());
    }
}
