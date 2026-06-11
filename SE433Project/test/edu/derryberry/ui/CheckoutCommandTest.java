package edu.derryberry.ui;

import edu.derryberry.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private CustomerState customerState;
    private ShoppingCart shoppingCart;
    private CheckoutCommand checkoutCommand;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
        customerState = new CustomerState();
        shoppingCart = new ShoppingCart();
        checkoutCommand = new CheckoutCommand(customerState, shoppingCart);
    }

    @AfterEach
    public void teardown() {
        System.setOut(originalOut);
    }

    @Test
    public void testEmptyCartPrintsError() {
        customerState.setActiveCustomer(new Customer("Dustin Derryberry", "TN", ShippingOption.STANDARD));
        checkoutCommand.execute();
        String output = outContent.toString();
        assertTrue(output.contains("Error: Your cart is empty."));
        assertFalse(output.contains("Transaction completed!"));
    }

    @Test
    public void testSuccessfulCheckoutPrintsReceiptEmptiesCart() {
        customerState.setActiveCustomer(new Customer("Dustin Derryberry", "IL", ShippingOption.STANDARD));
        shoppingCart.addItemToCart(new CartItem("PlayStation", 399.99, 1));
        checkoutCommand.execute();
        String output = outContent.toString();
        assertTrue(output.contains("Transaction completed!"));
        assertTrue(output.contains("Name: Dustin Derryberry"));
        assertTrue(output.contains("State of Residence: IL"));
        assertTrue(output.contains("Shipping Option: Standard Shipping"));
        assertTrue(output.contains("Shopping Cart: "));
        assertTrue(output.contains("\tName: PlayStation; Quantity: 1"));
        assertTrue(output.contains("Final cost: $423.99"));
        assertTrue(output.contains("Thanks for shopping!"));
        assertTrue(shoppingCart.isEmpty());
    }

    @Test
    public void testCheckoutWithPaidShipping() {
        customerState.setActiveCustomer(new Customer("Dustin Derryberry", "TN", ShippingOption.NEXT_DAY));
        shoppingCart.addItemToCart(new CartItem("Switch", 150.00, 2));
        checkoutCommand.execute();
        String output = outContent.toString();
        assertTrue(output.contains("Shipping Option: Next-day Shipping"));
        assertTrue(output.contains("Final cost: $325.00"));
        assertTrue(shoppingCart.isEmpty());
    }

    @Test
    public void testTotalExceededMaximumErrorsAndKeepsCart() {
        customerState.setActiveCustomer(new Customer("Dustin", "TN", ShippingOption.STANDARD));
        shoppingCart.addItemToCart(new CartItem("Super Mega Playstation", 1000.00, 1000));
        checkoutCommand.execute();
        String output = outContent.toString();
        assertTrue(output.contains("Error: Purchase amount cannot exceed $99,999.99."));
        assertFalse(output.contains("Transaction completed!"));
        assertEquals(1000, shoppingCart.getItemCount()); // cart untouched — customer can fix it and retry
    }


}
