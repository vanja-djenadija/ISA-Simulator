package operations.unary;

import simulation.Argument;
import simulation.Constant;
import simulation.Main;
import simulation.Simulator;

public class Jmp implements IUnary {

    @Override
    public void execute(Argument arg) {
        Main.simulator.setLineNumber(arg.getValue()); // passing Constant as Argument (line number)
    }
}
