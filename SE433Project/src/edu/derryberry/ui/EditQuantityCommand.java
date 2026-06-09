package edu.derryberry.ui;

import java.util.Scanner;

import edu.derryberry.model.ShoppingCart;
import edu.derryberry.service.InputValidator;

public class EditQuantityCommand implements IMenuCommand {
	
	private final Scanner scn;
	private final ShoppingCart cart;
	
	public EditQuantityCommand(Scanner scn, ShoppingCart cart) {
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
		
		System.out.println("Which item's quantity would you like to modify?");
		
		// get item name
		System.out.println("Enter item name: ");
		String itemName = scn.nextLine();
		
		// get new quantity
		System.out.println("How many would you like to buy?");
		int newQuantity;
		try {
			newQuantity = InputValidator.parseQuantity(scn.nextLine());
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}
		
		// update quantity
		System.out.println("Updating item quantity...");
		cart.updateCartItemQuantity(itemName, newQuantity);
		
		System.out.println("Here is your updated cart: ");
		cart.printCartItems();
	}
}
