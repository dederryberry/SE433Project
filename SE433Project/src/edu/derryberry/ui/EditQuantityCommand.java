package edu.derryberry.ui;

import java.util.Scanner;

import edu.derryberry.model.ShoppingCart;

public class EditQuantityCommand implements IMenuCommand {
	
	private final Scanner scn;
	private final ShoppingCart cart;
	
	public EditQuantityCommand(Scanner scn, ShoppingCart cart) {
		this.scn = scn;
		this.cart = cart;
	}
	
	public void execute() {
		
		System.out.println("Here is your cart: ");
		cart.printCartItems();
		
		System.out.println("Which item's quantity would you like to modify?");
		
		// get item name
		System.out.println("Enter item name: ");
		String itemName = scn.nextLine();
		
		// get new quantity
		System.out.println("What do you want the new quantity for that item to be? ");
		int newQuantity = Integer.parseInt(scn.nextLine());
		
		// update quantity
		System.out.println("Updating item quantity...");
		cart.updateCartItemQuantity(itemName, newQuantity);
		
		System.out.println("Here is your updated cart: ");
		cart.printCartItems();
	}
}
