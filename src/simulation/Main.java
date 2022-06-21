package simulation;

import simulation.Simulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static Simulator simulator;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Missing filepath as an argument.");
            return;
        }
        try {
            simulator = new Simulator(Files.readAllLines(Path.of(args[0])));
        } catch (IOException e) {
            System.out.println("File is not valid.");
            return;
        }
        simulator.run();
    }
}
