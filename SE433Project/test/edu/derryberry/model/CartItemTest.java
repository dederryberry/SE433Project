package edu.derryberry.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartItemTest {

    @Test
    public void testConstructorSetsFields() {
        CartItem cartItem = new CartItem("Flower Pot", 19.99, 2);
        assertEquals("Flower Pot", cartItem.getName());
        assertEquals(19.99, cartItem.getCost());
        assertEquals(2, cartItem.getQuantity());
    }

    @Test
    public void testUpdateQuantity() {
        CartItem cartItem = new CartItem("Flower Pot", 19.99, 2);
        cartItem.updateQuantity(5);
        assertEquals(5, cartItem.getQuantity());
    }

    @Test
    public void testGetSubtotal() {
        CartItem cartItem = new CartItem("Candy Bar", 1.00, 4);
        assertEquals(4.00, cartItem.getSubtotal(), 0.0001);
    }

    @Test
    public void testGetSubtotalAfterUpdateQuantity() {
        CartItem cartItem = new CartItem("Candy Bar", 1.00, 4);
        cartItem.updateQuantity(5);
        assertEquals(5.00, cartItem.getSubtotal(), 0.0001);
    }
}
