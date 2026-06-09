package edu.derryberry.ui;

import java.util.Scanner;

import edu.derryberry.model.CartItem;
import edu.derryberry.model.Item;
import edu.derryberry.model.ShoppingCart;
import edu.derryberry.model.Store;
import edu.derryberry.service.InputValidator;

public class AddItemCommand implements IMenuCommand {

	private final Scanner scn;
	private final Store store;
	private final ShoppingCart cart;
	
	public AddItemCommand(Scanner scn, Store store, ShoppingCart cart) {
		this.scn = scn;
		this.store = store;
		this.cart = cart;
	}
	
	public void execute() {
		
		System.out.println("Here are the items available: ");
		store.showAllItems();
		
		// item name
		System.out.println("Type the name of the item you would like to purchase: ");
		String name = scn.nextLine();

		// validate item name
		Item item;
		try {
			item = InputValidator.validateItem(store, name);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}
		
		// item quantity
		System.out.println("How many would you like to buy?");
		int quantity;
		try {
			quantity = InputValidator.parseQuantity(scn.nextLine());
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}

		// add cart item to cart
		CartItem cartItem = new CartItem(item.getName(), item.getCost(), quantity);
		cart.addItemToCart(cartItem);
		System.out.println("Item added to cart! Items in cart: " + cart.getItemCount());

	}
}
