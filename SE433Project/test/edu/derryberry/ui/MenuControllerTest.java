package edu.derryberry.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuControllerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private MenuController menu;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
        menu = new MenuController();
    }

    @AfterEach
    public void teardown() {
        System.setOut(originalOut);
    }

    @Test
    public void testExecuteRunsCorrectCommand() {
        AtomicInteger counter = new AtomicInteger(0);
        menu.add("1", counter::incrementAndGet);
        menu.execute("1");
        assertEquals(1, counter.get());
    }

    @Test
    public void testExecuteRunsOnlyCorrectCommand() {
        AtomicInteger counterOne = new AtomicInteger(0);
        AtomicInteger counterTwo = new AtomicInteger(0);
        menu.add("1", counterOne::incrementAndGet);
        menu.add("2", counterTwo::incrementAndGet);
        menu.execute("2");
        assertEquals(0, counterOne.get());
        assertEquals(1, counterTwo.get());
    }

    @Test
    public void testExecuteUnknownPrintsMessage() {
        AtomicInteger counter = new AtomicInteger(0);
        menu.add("1", counter::incrementAndGet);
        menu.execute("3");
        assertEquals(0, counter.get());
        assertTrue(outContent.toString().contains("Unknown command!"));
    }
}
