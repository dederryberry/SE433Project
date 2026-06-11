package edu.derryberry.ui;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExitCommandTest {

    @Test
    public void testExitCommand() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        try {
            new ExitCommand().execute();
            assertTrue(outContent.toString().contains("Thanks for shopping! See you soon!"));
        } finally {
            System.setOut(originalOut);
        }
    }
}
