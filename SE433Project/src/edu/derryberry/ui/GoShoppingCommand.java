package edu.derryberry.ui;

import java.util.Scanner;

import edu.derryberry.model.CartItem;
import edu.derryberry.model.CustomerState;
import edu.derryberry.model.Item;
import edu.derryberry.model.ShippingOption;
import edu.derryberry.model.ShoppingCart;
import edu.derryberry.model.Store;

public class GoShoppingCommand implements IMenuCommand {

	private final Scanner scn;
	private final CustomerState customerState;
	private final Store store;
	private final ShoppingCart cart;
	private final MenuController shoppingMenu;
	
	public GoShoppingCommand(Scanner scn, CustomerState customerState, Store store, ShoppingCart shoppingCart) {
		this.scn = scn;
		this.customerState = customerState;
		this.store = store;
		this.cart = shoppingCart;
		this.shoppingMenu = new MenuController();
	}
	
	private void prepMenuController() {
		ShippingOption shippingOption = customerState.getActiveCustomer().getShippingOption();
		String state = customerState.getActiveCustomer().getState();
		
		shoppingMenu.add("1", new AddItemCommand(scn, store, cart));
		shoppingMenu.add("2", new GetTotalCommand(cart, shippingOption, state));
		shoppingMenu.add("3", new SeeContentsCommand(cart));
		shoppingMenu.add("4", new EditQuantityCommand(scn, cart));
		shoppingMenu.add("5", new RemoveItemCommand(scn, cart));
		shoppingMenu.add("6", new CheckoutCommand(customerState, cart));
		
	}
	
	@Override
	public void execute() {
		
		// empty cart
		cart.emptyCart();
		
		// check for customer info
		if(customerState.isEmpty()) {
			System.out.println("Before going shopping, you must enter customer information.");
			return;
		}
		
		// prepare shopping menu controller
		prepMenuController();
		
		// intro message
		System.out.println("Let's go shopping!");
		
		// menu
		while(true) {
			
			System.out.println("What would you like to do?");
			System.out.println("\t1) Add item to cart");
			System.out.println("\t2) Get cart total");
			System.out.println("\t3) See cart contents");
			System.out.println("\t4) Edit item quantity");
			System.out.println("\t5) Remove item from cart");
			System.out.println("\t6) Checkout");
			String input = scn.nextLine();
			
			shoppingMenu.execute(input);
			if(input.equalsIgnoreCase("6")) {
				break;
			}
			
			System.out.println();
			
		}

		
		
	}
	
}
