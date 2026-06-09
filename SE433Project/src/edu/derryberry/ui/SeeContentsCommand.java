package edu.derryberry.ui;

import edu.derryberry.model.ShoppingCart;
import edu.derryberry.service.InputValidator;

public class SeeContentsCommand implements IMenuCommand {

	private final ShoppingCart cart;
	
	public SeeContentsCommand(ShoppingCart cart) {
		this.cart = cart;
	}
	
	public void execute() {

		// validate if the cart is empty
		try {
			InputValidator.validateCartNotEmpty(cart);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}

		System.out.println("Displaying cart contents:");
		cart.printCartItems();
	}
}
