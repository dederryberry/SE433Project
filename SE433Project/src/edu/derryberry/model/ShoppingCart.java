package edu.derryberry.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart {

	private Map<String, CartItem> cart;
	private double cartCost;
	
	public ShoppingCart() {
		this.cart = new LinkedHashMap<>();
		this.cartCost = 0.00;
	}
	
	// add new item to cart
	public void addItemToCart(CartItem cartItem, int quantity) {
		cart.put(cartItem.getName(), cartItem);
		updateCartCost();
		
	}
	
	// remove item from cart
	public void removeItemFromCart(String itemName) {
		cart.remove(itemName);
		updateCartCost();
	}
	
	// get cart cost
	public double getCartCost() {
		return cartCost;
	}
	
	// update item quantity
	public void updateCartItemQuantity(String itemName, int newQuantity) {
		// if update quantity is greater than 0, update it
		// if less than 0, remove the item
		if(cart.containsKey(itemName)) {
			if(newQuantity > 0) {
				cart.get(itemName).updateQuantity(newQuantity);
			} else {
				removeItemFromCart(itemName);
			}
			updateCartCost();
		} else {
			System.out.println("Item not found in cart!");
		}
	}
	
	// private helper to update cart cost
	private void updateCartCost() {
		double newCost = 0.0;
		for(CartItem cartItem : cart.values()) {
			newCost += cartItem.getSubtotal();
		}
		cartCost = newCost;
	}
	
	
}
