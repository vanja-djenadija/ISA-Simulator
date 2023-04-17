package operations.unary;

import simulation.Argument;
import simulation.Main;
import simulation.Processor;
import simulation.Simulator;

public class Jne implements IUnary {

    @Override
    public void execute(Argument arg) {
        Simulator simulator = Main.simulator;
        if (!simulator.getProcessor().isCmpEquals())
            simulator.setLineNumber(arg.getValue());
    }
}
