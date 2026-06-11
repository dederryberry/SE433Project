package edu.derryberry.ui;

import edu.derryberry.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoShoppingCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private CustomerState customerState;
    private Store store;
    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
        customerState = new CustomerState();
        store = new Store(List.of(
                new Item("XBOX", 150.00),
                new Item("PlayStation", 250.00)
        ));
        shoppingCart = new ShoppingCart();
    }

    @AfterEach
    public void teardown() {
        System.setOut(originalOut);
    }

    private void run (String input) {
        new GoShoppingCommand(new Scanner(input), customerState, store, shoppingCart).execute();
    }

    private void setCustomer() {
        customerState.setActiveCustomer(new Customer("Dustin Derryberry", "TN", ShippingOption.STANDARD));
    }

    @Test
    public void testBlocksShoppingWithoutCustomerInfo() {
        run("");
        String output = outContent.toString();
        assertTrue(output.contains("Before going shopping, you must enter customer information."));
        assertFalse(output.contains("Let's go shopping!"));
    }

    @Test
    public void testEmptiesCartOnEntry() {
        setCustomer();
        shoppingCart.addItemToCart(new CartItem("XBOX", 150.00, 3));
        run("6\n");
        assertTrue(outContent.toString().contains("Error: Your cart is empty."));
        assertTrue(shoppingCart.isEmpty());
    }

    @Test
    public void testFullShoppingSession() {
        setCustomer();
        run("1\nXBOX\n3\n2\n6\n");
        String output = outContent.toString();
        assertTrue(output.contains("Let's go shopping!"));
        assertTrue(output.contains("Item added to cart! Items in cart: 3"));
        assertTrue(output.contains("Total: $450.00"));
        assertTrue(output.contains("Transaction completed!"));
        assertTrue(output.contains("Final cost: $450.00"));
        assertTrue(shoppingCart.isEmpty());
    }

    @Test
    public void testUnknownOptionPrintsMessage() {
        setCustomer();
        run("8\n6\n");
        String output = outContent.toString();
        assertTrue(output.contains("Unknown command!"));
        assertTrue(output.contains("Error: Your cart is empty."));
    }

    @Test
    public void testDisplaysShoppingMenu() {
        setCustomer();
        run("6\n");
        String output = outContent.toString();
        assertTrue(output.contains("What would you like to do?"));
        assertTrue(output.contains("\t1) Add item to cart"));
        assertTrue(output.contains("\t2) Get cart total"));
        assertTrue(output.contains("\t3) See cart contents"));
        assertTrue(output.contains("\t4) Edit item quantity"));
        assertTrue(output.contains("\t5) Remove item from cart"));
        assertTrue(output.contains("\t6) Checkout"));
    }

    @Test
    public void testAllShoppingMenuOptionsAreWired() {
        setCustomer();
        run("1\nXBOX\n2\n"
                + "3\n"
                + "4\nXBOX\n1\n"
                + "5\nXBOX\n"
                + "6\n");

        String output = outContent.toString();
        assertTrue(output.contains("Item added to cart! Items in cart: 2"));
        assertTrue(output.contains("Displaying cart contents:"));
        assertTrue(output.contains("Updating item quantity..."));
        assertTrue(output.contains("Removing item from cart..."));
        assertTrue(output.contains("Error: Your cart is empty."));
        assertFalse(output.contains("Unknown command!"));

        // kill line 76 mutant
        String ls = System.lineSeparator();
        assertTrue(output.contains("Item added to cart! Items in cart: 2" + ls + ls + "What would you like to do?"));
    }

}
