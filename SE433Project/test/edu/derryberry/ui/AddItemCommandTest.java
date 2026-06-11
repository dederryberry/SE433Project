package edu.derryberry.ui;

import edu.derryberry.model.Item;
import edu.derryberry.model.ShoppingCart;
import edu.derryberry.model.Store;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AddItemCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Store store;
    private ShoppingCart cart;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
        store = new Store(List.of(
                new Item("XBOX", 299.99),
                new Item("PlayStation", 399.99)
        ));
        cart = new ShoppingCart();
    }

    @AfterEach
    public void teardown() {
        System.setOut(originalOut);
    }

    private void run(String input) {
        new AddItemCommand(new Scanner(input), store, cart).execute();
    }

    @Test
    public void testAddsItemAndQuantityToCart() {
        run("XBOX\n4\n");
        assertEquals(4, cart.getItemCount());
        assertEquals(1199.96, cart.getCartCost(), 0.0001);
        assertTrue(outContent.toString().contains("Item added to cart! Items in cart: 4"));
    }

    @Test
    public void testUnknownItemPrintsErrorAddsNothing() {
        run("Sega Genesis\n");
        String output = outContent.toString();
        assertTrue(output.contains("Error: Item 'Sega Genesis' not found."));
        assertFalse(output.contains("How many would you like to buy?"));
        assertTrue(cart.isEmpty());
    }

    @Test
    public void testInvalidQuantityPrintsErrorAddsNothing() {
        run("XBOX\nbillion\n");
        String output = outContent.toString();
        assertTrue(output.contains("Error: Quantity must be a whole number."));
        assertFalse(output.contains("Item added to cart!"));
        assertTrue(cart.isEmpty());
    }

    @Test
    public void testDisplaysAllPrompts() {
        run("XBOX\n3\n");
        String output = outContent.toString();
        assertTrue(output.contains("Here are the items available: "));
        assertTrue(output.contains("\tXBOX: $299.99"));
        assertTrue(output.contains("\tPlayStation: $399.99"));
        assertTrue(output.contains("Type the name of the item you would like to purchase: "));
        assertTrue(output.contains("How many would you like to buy?"));
    }
}
