package operations.unary;

import simulation.Argument;

public class Print implements IUnary {

    public void execute(Argument arg) {
        System.out.println(arg.getValue());
    }
}
