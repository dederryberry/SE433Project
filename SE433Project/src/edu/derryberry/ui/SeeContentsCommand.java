package edu.derryberry.ui;

import edu.derryberry.model.ShoppingCart;

public class SeeContentsCommand implements IMenuCommand {

	private ShoppingCart cart;
	
	public SeeContentsCommand(ShoppingCart cart) {
		this.cart = cart;
	}
	
	public void execute() {
		System.out.println("Displaying cart contents:");
		cart.printCartItems();
	}
}
