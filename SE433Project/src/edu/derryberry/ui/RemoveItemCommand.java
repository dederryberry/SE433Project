package edu.derryberry.ui;

import java.util.Scanner;

import edu.derryberry.model.ShoppingCart;
import edu.derryberry.service.InputValidator;

public class RemoveItemCommand implements IMenuCommand {

	private final Scanner scn;
	private final ShoppingCart cart;
	
	public RemoveItemCommand(Scanner scn, ShoppingCart cart) {
		this.scn = scn;
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

		System.out.println("Here is your cart: ");
		cart.printCartItems();
		
		System.out.println("Which item would you like to remove?");
		
		// get item name
		System.out.println("Enter item name: ");
		String itemName = scn.nextLine();
		
		System.out.println("Removing item from cart...");
		cart.removeItemFromCart(itemName);
		
		System.out.println("Here is your updated cart: ");
		cart.printCartItems();
		
	}
}
