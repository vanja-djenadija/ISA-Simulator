package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import simulation.Main;
import simulation.Simulator;

public class JumpTest {

    @Test
    public void test() {
        Simulator simulator = null;
        try {
            simulator = new Simulator(Files.readAllLines(Paths.get("ISA-Simulator/src/files/Jump")));
            Main.simulator = simulator;
            simulator.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert simulator != null;
        assertEquals(10, (long) simulator.getProcessor().getRegisters().get("RBX"));
    }

}

