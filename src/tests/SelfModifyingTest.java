package tests;

import org.junit.Test;
import simulation.Main;
import simulation.Simulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class SelfModifyingTest {
    @Test
    public void test() {
        Simulator simulator = null;
        try {
            simulator = new Simulator(Files.readAllLines(Paths.get("ISA-Simulator/src/files/SelfModifying")));
            Main.simulator = simulator;
            simulator.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert simulator != null;
        assertEquals(10, (long) simulator.getProcessor().getRegisters().get("RAX"));
    }
}
