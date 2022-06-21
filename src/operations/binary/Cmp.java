package operations.binary;

import simulation.Argument;
import simulation.Main;
import simulation.Processor;
import simulation.Simulator;

public class Cmp implements IBinary {

    @Override
    public void execute(Argument arg1, Argument arg2) {
        Processor processor = Main.simulator.getProcessor();
        processor.setCmpEquals(arg1.getValue() == arg2.getValue());
        processor.setCmpLess(arg1.getValue() < arg2.getValue());
    }
}
