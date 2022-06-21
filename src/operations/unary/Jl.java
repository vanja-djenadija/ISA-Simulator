package operations.unary;

import simulation.Argument;
import simulation.Main;
import simulation.Simulator;

public class Jl implements IUnary{

    @Override
    public void execute(Argument arg) {
        Simulator simulator = Main.simulator;
        if (simulator.getProcessor().isCmpLess())
            simulator.setLineNumber(arg.getValue());
    }
}
