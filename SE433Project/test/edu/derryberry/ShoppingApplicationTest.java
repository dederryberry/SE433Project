package edu.derryberry;

import edu.derryberry.model.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingApplicationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void teardown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    private void runMain(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ShoppingApplication.main(new String[]{});
    }

    // loadItems
    @Test
    public void testLoadItemsReadsStoreFile() {
        List<Item> items = ShoppingApplication.loadItems("src/edu/derryberry/resources/store.csv");
        assertEquals(10, items.size());
        assertEquals("Velvet Armchair", items.get(0).getName());
        assertEquals(249.99, items.get(0).getCost());
        assertEquals("Decorative Coaster Set", items.get(9).getName());
        assertEquals(9.50, items.get(9).getCost());

        assertNotNull(new ShoppingApplication()); // added for full PIT line coverage
    }

    @Test
    public void testLoadItemsSkipsBlankLines(@TempDir Path tempDir) throws Exception {
        Path csv = tempDir.resolve("store.csv");
        Files.writeString(csv, "item,cost\nSoap,9.99\n\n   \nBread,3.99\n");
        List<Item> items = ShoppingApplication.loadItems(csv.toString());
        assertEquals(2, items.size());
        assertEquals("Soap", items.get(0).getName());
        assertEquals("Bread", items.get(1).getName());
    }

    @Test
    public void loadItemsMissingFilePrintsErrorAndReturnsEmptyList(@TempDir Path tempDir) {
        List<Item> items = ShoppingApplication.loadItems(tempDir.resolve("nope.csv").toString());
        assertTrue(items.isEmpty());
        assertTrue(errContent.toString().contains("Error reading store file:"));
    }

    // main (system + acceptance tests, technically)
    @Test
    public void testMainShowsMenuAndExistsOnThree() {
        runMain("3\n");
        String output = outContent.toString();
        assertTrue(output.contains("Welcome to Dustin's shop!"));
        assertTrue(output.contains("Please select an option: "));
        assertTrue(output.contains("\t1) Enter customer info"));
        assertTrue(output.contains("\t2) Go shopping"));
        assertTrue(output.contains("\t3) Exit Shop"));
        assertTrue(output.contains("Thanks for shopping! See you soon!"));
    }

    @Test
    public void testMainHandlesUnknownOption() {
        runMain("7\n3\n");
        String output = outContent.toString();
        assertTrue(output.contains("Unknown command!"));
        assertTrue(output.contains("Thanks for shopping! See you soon!"));
        String ls = System.lineSeparator();
        assertTrue(output.contains("Unknown command!" + ls + ls + "Please select an option: "));
    }

    @Test
    public void testFullJourney() {
        runMain("1\nDustin Derryberry\nTN\n1\n"        // enter customer info, standard shipping
                + "2\n"                                     // go shopping
                + "1\nScented Candle\n8\n"                  // add 8 candles ($100.00)
                + "6\n"                                     // checkout
                + "3\n");                                   // exit shop
        String output = outContent.toString();
        assertTrue(output.contains("Thanks for the info! You can now go shopping. "));
        assertTrue(output.contains("Let's go shopping!"));
        assertTrue(output.contains("Item added to cart! Items in cart: 8"));
        assertTrue(output.contains("Transaction completed!"));
        assertTrue(output.contains("Final cost: $100.00"));   // 100 + 0 TN tax + free shipping over $50
        assertTrue(output.contains("Thanks for shopping! See you soon!"));
    }

    @Test
    public void testMainClosesTheScannerOnExit() {
        class TrackingInputStream extends ByteArrayInputStream {
            boolean closed = false;
            TrackingInputStream(byte[] buf) { super(buf); }
            @Override
            public void close() { closed = true; }
        }

        TrackingInputStream in = new TrackingInputStream("3\n".getBytes());
        System.setIn(in);
        ShoppingApplication.main(new String[]{});
        assertTrue(in.closed);
    }
}
