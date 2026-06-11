package edu.derryberry.service;

import edu.derryberry.model.CartItem;
import edu.derryberry.model.Item;
import edu.derryberry.model.ShoppingCart;
import edu.derryberry.model.Store;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorTest {

    // parseQuantity
    @Test
    public void testParseQuantityReturnsValidNumber() {
        new InputValidator(); // added to pass line coverage in PIT
        assertEquals(5, InputValidator.parseQuantity("5"));
    }

    @Test
    public void testParseQuantityAcceptsBoundaryLow() {
        assertEquals(1, InputValidator.parseQuantity("1"));
    }

    @Test
    public void testParseQuantityRejectsZero() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.parseQuantity("0"));
        assertEquals("Quantity must be at least 1.", e.getMessage());
    }

    @Test
    public void testParseQuantityRejectsNonNumeric() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.parseQuantity("abc"));
        assertEquals("Quantity must be a whole number.", e.getMessage());
    }

    @Test
    public void testParseQuantityRejectsNonWholeNumber() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.parseQuantity("1.5"));
        assertEquals("Quantity must be a whole number.", e.getMessage());
    }

    // validatePurchaseAmount

    @Test
    public void testValidatePurchaseAmountAcceptsBoundaries() {
        assertDoesNotThrow(() -> InputValidator.validatePurchaseAmount(1.00));
        assertDoesNotThrow(() -> InputValidator.validatePurchaseAmount(99999.99));
    }

    @Test
    public void testValidatePurchaseAmountRejectsBelowMinimum() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validatePurchaseAmount(0.99));
        assertEquals("Purchase amount must be at least $1.00.", e.getMessage());
    }

    @Test
    public void testValidatePurchaseAmountRejectsAboveMaximum() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validatePurchaseAmount(100000.00));
        assertEquals("Purchase amount cannot exceed $99,999.99.", e.getMessage());
    }

    // validateItem
    @Test
    public void testValidateItemReturnsItem() {
        Store store = new Store(List.of(new Item("XBOX", 299.00)));
        Item item = InputValidator.validateItem(store, "XBOX");
        assertEquals("XBOX", item.getName());
    }

    @Test
    public void testValidateItemRejectsItemWhenNotFound() {
        Store store = new Store(List.of(new Item("XBOX", 299.00)));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validateItem(store, "PlayStation"));
        assertEquals("Item 'PlayStation' not found.", e.getMessage());
    }

    // validateCartNotEmpty
    @Test
    public void testCartNotEmptyPassesForCartWithItems() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItemToCart(new CartItem("XBOX", 299.00, 1));
        assertDoesNotThrow(() -> InputValidator.validateCartNotEmpty(cart));
    }

    @Test
    public void testCartNotEmptyFailsForEmptyCart() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validateCartNotEmpty(new ShoppingCart()));
        assertEquals("Your cart is empty.", e.getMessage());
    }


    // validateState
    @Test
    public void testValidateStateNormalizes() {
        assertEquals("MI", InputValidator.validateState(" mi  "));
    }

    @Test
    public void testValidateStateAcceptsClean() {
        assertEquals("TN", InputValidator.validateState("TN"));
    }

    @Test
    public void testValidateStateRejectsInvalid() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validateState("PL"));
        assertEquals("'PL' is not a valid state abbreviation.", e.getMessage());
    }
}
