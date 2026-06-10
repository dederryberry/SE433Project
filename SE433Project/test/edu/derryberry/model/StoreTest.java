package edu.derryberry.model;

import edu.derryberry.service.FormatUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Store store;

    // set up store
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        store = new Store(List.of(
                new Item("Scented Candle", 12.50),
                new Item("Ceramic Table Lamp", 45.00)
        ));
    }

    // tear down store
    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testGetItemByNameReturnsMatchingItem() {
        Item item = store.getItemByName("Scented Candle");
        assertNotNull(item);
        assertEquals("Scented Candle", item.getName());
        assertEquals(12.50, item.getCost());
    }

    @Test
    public void getItemByNameReturnsNullForUnknownItem() {
        assertNull(store.getItemByName("XBOX 360"));
    }

    @Test
    public void showAllItemsPrintsAllItemsWithFormattedCost() {
        store.showAllItems();
        String output = outContent.toString();
        assertTrue(output.contains("\tScented Candle: " + FormatUtils.formatMoney(12.50)));
        assertTrue(output.contains("\tCeramic Table Lamp: " + FormatUtils.formatMoney(45.00)));
    }
}
