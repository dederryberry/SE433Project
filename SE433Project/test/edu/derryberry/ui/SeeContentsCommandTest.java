package edu.derryberry.ui;

import edu.derryberry.model.CartItem;
import edu.derryberry.model.ShoppingCart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeeContentsCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ShoppingCart cart;
    private SeeContentsCommand command;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
        cart = new ShoppingCart();
        command = new SeeContentsCommand(cart);
    }

    @AfterEach
    public void teardown() {
        System.setOut(originalOut);
    }

    @Test
    public void testEmptyCartPrintsErrorAndStops() {
        command.execute();
        String output = outContent.toString();
        assertTrue(output.contains("Error: Your cart is empty."));
        assertFalse(output.contains("Displaying cart contents"));
    }

    @Test
    public void testFullCartPrints() {
        cart.addItemToCart(new CartItem("XBOX", 299.00, 1));
        cart.addItemToCart(new CartItem("PlayStation", 399.00, 2));
        command.execute();
        String output = outContent.toString();
        assertTrue(output.contains("Displaying cart contents:"));
        assertTrue(output.contains("\tName: XBOX; Quantity: 1"));
        assertTrue(output.contains("\tName: PlayStation; Quantity: 2"));
    }
}
