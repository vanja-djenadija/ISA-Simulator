package operations.unary;

import operations.IOperation;
import simulation.Argument;

public interface IUnary extends IOperation {
    void execute(Argument arg);
}
