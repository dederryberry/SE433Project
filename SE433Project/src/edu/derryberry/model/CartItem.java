package edu.derryberry.model;

public class CartItem extends Item {
	
	private int quantity;
	
	public CartItem(String name, double cost, int quantity) {
		super(name, cost);
		this.quantity = quantity;
	}
	
	public void updateQuantity(int newQuantity) {
		quantity = newQuantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getSubtotal() {
		return this.getCost() * this.getQuantity();
	}
	
}
