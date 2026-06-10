package edu.derryberry.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        shoppingCart = new ShoppingCart();
    }

    @Test
    public void testAddItem() {
        System.setOut(originalOut);
    }

    @Test
    public void testNewEmptyCart() {
        assertTrue(shoppingCart.isEmpty());
        assertEquals(0, shoppingCart.getItemCount());
        assertEquals(0.0, shoppingCart.getCartCost(), 0.0001);
    }

    @Test
    public void testAddItemToCartUpdatesItemCountAndCartCost() {
        shoppingCart.addItemToCart(new CartItem("XBOX", 299.99, 4));
        assertFalse(shoppingCart.isEmpty());
        assertEquals(4, shoppingCart.getItemCount());
        assertEquals(1199.96, shoppingCart.getCartCost(), 0.0001);
    }

    @Test
    public void testAddDuplicateItemSetsCorrectQuantity() {
        shoppingCart.addItemToCart(new CartItem("XBOX", 299.99, 1));
        shoppingCart.addItemToCart(new CartItem("XBOX", 299.99, 6));
        assertEquals(7, shoppingCart.getItemCount());
        assertEquals(2099.93, shoppingCart.getCartCost(), 0.0001);
    }

    @Test
    public void testAddDifferentItemsSetsCost() {
        shoppingCart.addItemToCart(new CartItem("XBOX", 299.99, 1));
        shoppingCart.addItemToCart(new CartItem("Flower Pot", 4.99, 2));
        assertEquals(3, shoppingCart.getItemCount());
        assertEquals(309.97, shoppingCart.getCartCost(), 0.0001);
    }

    @Test
    public void testRemoveItemFromCartUpdatesItemCountAndCartCost() {
        shoppingCart.addItemToCart(new CartItem("XBOX", 299.99, 1));
        shoppingCart.addItemToCart(new CartItem("Flower Pot", 4.99, 2));
        shoppingCart.removeItemFromCart("XBOX");
        assertEquals(2, shoppingCart.getItemCount());
        assertEquals(9.98, shoppingCart.getCartCost(), 0.0001);
    }

    @Test
    public void testRemoveNonExistingItemDoesNothing() {
        shoppingCart.addItemToCart(new CartItem("Flower Pot", 4.99, 2));
        shoppingCart.removeItemFromCart("XBOX");
        assertEquals(2, shoppingCart.getItemCount());
        assertEquals(9.98, shoppingCart.getCartCost(), 0.0001);
    }

    @Test
    public void testUpdateCartItemQuantityExistingItemRecalculatesCost() {
        shoppingCart.addItemToCart(new CartItem("Flower Pot", 4.99, 2));
        shoppingCart.updateCartItemQuantity("Flower Pot", 7);
        assertEquals(7, shoppingCart.getItemCount());
        assertEquals(34.93, shoppingCart.getCartCost(), 0.0001);
    }

    @Test
    public void testUpdateQuantityMissingItemPrintsMessageNoChanges() {
        shoppingCart.addItemToCart(new CartItem("Flower Pot", 4.99, 2));
        shoppingCart.updateCartItemQuantity("XBOX", 2);
        assertTrue(outContent.toString().contains("Item not found in cart!"));
        assertEquals(9.98, shoppingCart.getCartCost(), 0.0001);
    }

    @Test
    public void testPrintCartItemsShowsRequiredFields() {
        shoppingCart.addItemToCart(new CartItem("XBOX", 299.99, 1));
        shoppingCart.addItemToCart(new CartItem("Flower Pot", 4.99, 2));
        shoppingCart.printCartItems();
        String output = outContent.toString();
        assertTrue(output.contains("\tName: XBOX; Quantity: 1"));
        assertTrue(output.contains("\tName: Flower Pot; Quantity: 2"));
    }

    @Test
    public void testEmptyCartEmptiesCart() {
        shoppingCart.addItemToCart(new CartItem("XBOX", 299.99, 1));
        shoppingCart.emptyCart();
        assertTrue(shoppingCart.isEmpty());
        assertEquals(0, shoppingCart.getItemCount());
        assertEquals(0.0, shoppingCart.getCartCost(), 0.0001);
    }
}
