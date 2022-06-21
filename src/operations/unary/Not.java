package operations.unary;

import simulation.Argument;

public class Not implements IUnary {

    public void execute(Argument arg) {
        arg.setValue(~arg.getValue());
    }
}

