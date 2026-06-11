package edu.derryberry.ui;

import edu.derryberry.model.CartItem;
import edu.derryberry.model.ShippingOption;
import edu.derryberry.model.ShoppingCart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class GetTotalCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void teardown() {
        System.setOut(originalOut);
    }

    // helper method: calculateTax
    @Test
    public void testTaxIllinois() {
        assertEquals(6.00, GetTotalCommand.calculateTax(100.00, "IL"), 0.0001);
    }

    @Test
    public void testTaxCalifornia() {
        assertEquals(6.00, GetTotalCommand.calculateTax(100.00, "CA"), 0.0001);
    }

    @Test
    public void testTaxNewYork() {
        assertEquals(6.00, GetTotalCommand.calculateTax(100.00, "NY"), 0.0001);
    }

    @Test
    public void testNoTaxState() {
        assertEquals(0.00, GetTotalCommand.calculateTax(100.00, "NM"), 0.0001);
        assertEquals(0.00, GetTotalCommand.calculateTax(100.00, "TN"), 0.0001);
        assertEquals(0.00, GetTotalCommand.calculateTax(100.00, "TX"), 0.0001);
    }

    // helper method: calculateShipping
    @Test
    public void testStandardShippingChargedBelowThreshold() {
        assertEquals(10.00, GetTotalCommand.calculateShipping(ShippingOption.STANDARD,
                49.99, 50), 0.0001);
    }

    @Test
    public void testStandardShippingChargedAtThreshold() {
        assertEquals(10.00, GetTotalCommand.calculateShipping(ShippingOption.STANDARD,
                50.00, 50), 0.0001);
    }

    @Test
    public void testStandardShippingFreeAboveThreshold() {
        assertEquals(0.00, GetTotalCommand.calculateShipping(ShippingOption.STANDARD,
                50.01, 50), 0.0001);
    }

    @Test
    public void testNextDayShippingChargedAboveThreshold() {
        assertEquals(25.00, GetTotalCommand.calculateShipping(ShippingOption.NEXT_DAY,
                100.00, 50), 0.0001);
    }

    // execute method
    @Test
    public void testEmptyCartPrintsErrors() {
        ShoppingCart cart = new ShoppingCart();
        new GetTotalCommand(cart, ShippingOption.STANDARD, "TN").execute();
        String output = outContent.toString();
        assertTrue(output.contains("Error: Your cart is empty."));
        assertFalse(output.contains("Displaying totals..."));
    }

    @Test
    public void testPrintsAllTotalsForTaxStateFreeShipping() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItemToCart(new CartItem("XBOX", 299.99, 1));
        new GetTotalCommand(cart, ShippingOption.STANDARD, "IL").execute();
        String output = outContent.toString();
        assertTrue(output.contains("Displaying totals..."));
        assertTrue(output.contains("Cart subtotal: $299.99"));
        assertTrue(output.contains("Tax: $18.00"));
        assertTrue(output.contains("Shipping: $0.00"));
        assertTrue(output.contains("Total: $317.99"));
    }

    @Test
    public void testPrintsAllTotalsForUntaxedStatePaidShipping() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItemToCart(new CartItem("XBOX", 299.99, 2));
        new GetTotalCommand(cart, ShippingOption.NEXT_DAY, "TN").execute();
        String output = outContent.toString();
        assertTrue(output.contains("Displaying totals..."));
        assertTrue(output.contains("Cart subtotal: $599.98"));
        assertTrue(output.contains("Tax: $0.00"));
        assertTrue(output.contains("Shipping: $25.00"));
        assertTrue(output.contains("Total: $624.98"));
    }

    @Test
    public void testInvalidPurchaseAmountPrintsError() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItemToCart(new CartItem("Better XBOX", 1000.00, 1000));
        new GetTotalCommand(cart, ShippingOption.STANDARD, "TN").execute();
        String output = outContent.toString();
        assertTrue(output.contains("Error: Purchase amount cannot exceed $99,999.99."));
        assertFalse(output.contains("Displaying totals..."));
    }

}
