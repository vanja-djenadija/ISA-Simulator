package operations.binary;

import operations.IOperation;
import simulation.Argument;

public interface IBinary extends IOperation {
    void execute(Argument arg1, Argument arg2);
}
