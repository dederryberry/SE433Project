package edu.derryberry.ui;

import edu.derryberry.model.CartItem;
import edu.derryberry.model.ShoppingCart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class EditQuantityCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ShoppingCart cart;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        cart = new ShoppingCart();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    private void run(String input) {
        new EditQuantityCommand(new Scanner(input), cart).execute();
    }

    @Test
    public void testEmptyCartPrintsError() {
        run("");
        String output = outContent.toString();
        assertTrue(output.contains("Error: Your cart is empty."));
        assertFalse(output.contains("Here is your cart: "));
    }

    @Test
    public void testInvalidQuantityPrintsError() {
        cart.addItemToCart(new CartItem("XBOX", 299.99, 1));
        run("XBOX\nzero\n");
        String output = outContent.toString();
        assertTrue(output.contains("Error: Quantity must be a whole number."));
        assertFalse(output.contains("Updating item quantity..."));
        assertEquals(1, cart.getItemCount());
        assertEquals(299.99, cart.getCartCost(), 0.0001);
    }

    @Test
    public void testDisplaysAllPrompts() {
        cart.addItemToCart(new CartItem("XBOX", 299.99, 2));
        run("XBOX\n15\n");

        String output = outContent.toString();

        // prompts
        assertTrue(output.contains("Here is your cart: "));
        assertTrue(output.contains("Which item's quantity would you like to modify?"));
        assertTrue(output.contains("Enter item name: "));
        assertTrue(output.contains("How many would you like to buy?"));
        assertTrue(output.contains("Updating item quantity..."));
        assertTrue(output.contains("Here is your updated cart: "));

        // before listing shows the old quantity, after listing shows the new one
        assertTrue(output.contains("\tName: XBOX; Quantity: 2"));
        assertTrue(output.contains("\tName: XBOX; Quantity: 15"));
    }
}
