package operations.unary;

import simulation.Argument;
import simulation.Main;
import simulation.Simulator;

import java.util.Scanner;

public class Scan implements IUnary {

    @Override
    public void execute(Argument arg) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        long value;
        try {
            value = Long.parseLong(input);
            arg.setValue(value);
        } catch (NumberFormatException e) {
            System.out.println("Invalid format of scanned input.");
        }
    }
}
