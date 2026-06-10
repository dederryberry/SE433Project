package edu.derryberry.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart {

	private final Map<String, CartItem> cart;
	private double cartCost;
	
	public ShoppingCart() {
		this.cart = new LinkedHashMap<>();
		this.cartCost = 0.00;
	}
	
	// add new item to cart
	public void addItemToCart(CartItem cartItem) {
		if (cart.containsKey(cartItem.getName())) {
			CartItem existing = cart.get(cartItem.getName());
			existing.updateQuantity(existing.getQuantity() + cartItem.getQuantity());
		} else {
			cart.put(cartItem.getName(), cartItem);
		}
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
		if (cart.containsKey(itemName)) {
			cart.get(itemName).updateQuantity(newQuantity);
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
	
	public void printCartItems() {
		for(CartItem c : cart.values()) {
			System.out.println("\tName: " + c.getName() + "; Quantity: " + c.getQuantity());
		}
	}

	public void emptyCart() {
		cart.clear();
		cartCost = 0.0;
	}

	public int getItemCount() {
		int count = 0;
		for (CartItem c : cart.values()) {
			count += c.getQuantity();
		}
		return count;
	}

	public boolean isEmpty() {
		return cart.isEmpty();
	}
	
}
