package operations.unary;

import simulation.Argument;
import simulation.Main;
import simulation.Processor;
import simulation.Simulator;

public class Jge implements IUnary {

    @Override
    public void execute(Argument arg) {
        Simulator simulator = Main.simulator;
        if (!simulator.getProcessor().isCmpLess())
            simulator.setLineNumber(arg.getValue());
    }
}
