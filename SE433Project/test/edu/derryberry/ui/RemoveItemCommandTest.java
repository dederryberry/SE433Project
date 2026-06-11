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

public class RemoveItemCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ShoppingCart cart;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
        cart = new ShoppingCart();
    }

    @AfterEach
    public void teardown() {
        System.setOut(originalOut);
    }

    private void run(String input) {
        new RemoveItemCommand(new Scanner(input), cart).execute();
    }

    @Test
    public void testEmptyCartPrintsErrorAndStops() {
        run("");
        String output = outContent.toString();
        assertTrue(output.contains("Error: Your cart is empty."));
        assertFalse(output.contains("Here is your cart: "));
    }

    @Test
    public void testRemovesItemFromCart() {
        cart.addItemToCart(new CartItem("XBOX", 299.99, 1));
        cart.addItemToCart(new CartItem("Gum", 1.00, 1));
        run("XBOX");
        assertEquals(1, cart.getItemCount());
        assertEquals(1.00, cart.getCartCost(), 0.0001);
    }

    @Test
    public void testDisplaysAllPrompts() {
        cart.addItemToCart(new CartItem("XBOX", 299.99, 2));
        cart.addItemToCart(new CartItem("Gum", 1.00, 1));

        run("XBOX\n");

        String output = outContent.toString();

        // prompts
        assertTrue(output.contains("Here is your cart: "));
        assertTrue(output.contains("Which item would you like to remove?"));
        assertTrue(output.contains("Enter item name: "));
        assertTrue(output.contains("Removing item from cart..."));
        assertTrue(output.contains("Here is your updated cart: "));

        // Verify gum listed twice (once before, once after)
        String gumLine = "\tName: Gum; Quantity: 1";
        int first = output.indexOf(gumLine);
        int second = output.indexOf(gumLine, first + gumLine.length());
        assertTrue(first >= 0 && second > first);

        // XBOX appears only before removal
        String afterListing = output.substring(output.indexOf("Here is your updated cart: "));
        assertFalse(afterListing.contains("XBOX"));
    }

}
