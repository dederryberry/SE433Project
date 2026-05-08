package edu.derryberry.ui;

import java.util.Scanner;

import edu.derryberry.model.CartItem;
import edu.derryberry.model.Item;
import edu.derryberry.model.ShoppingCart;
import edu.derryberry.model.Store;

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
		
		// item quantity
		System.out.println("How many would you like to buy?");
		int quantity = Integer.parseInt(scn.nextLine()); // ugly...
		
		// get item from store; add cart item to cart
		Item item = store.getItemByName(name);
		CartItem cartItem = new CartItem(item.getName(), item.getCost(), quantity);
		cart.addItemToCart(cartItem, quantity);
		System.out.println("Item added to cart!");
		
		
	}
}
