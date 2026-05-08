package edu.derryberry.ui;

import edu.derryberry.model.Customer;
import edu.derryberry.model.CustomerState;
import edu.derryberry.model.ShoppingCart;

public class CheckoutCommand implements IMenuCommand {
	
	private final CustomerState customerState;
	private final ShoppingCart cart;
	
	public CheckoutCommand(CustomerState customerState, ShoppingCart cart) {
		this.customerState = customerState;
		this.cart = cart;
	}
	
	public void execute() {
		
		// get customer object
		Customer customer = customerState.getActiveCustomer();
		System.out.println("Transaction completed!");
		
		// customer info
		System.out.println("Name: " + customer.getName());
		System.out.println("State of Residence: " + customer.getState());
		System.out.println("Shipping Option: " + customer.getShippingOption().getDisplayName());
		
		// cart info including cost
		System.out.println("Shopping Cart: ");
		cart.printCartItems();
		System.out.println("Final cost: " + cart.getCartFullCost());
		
		System.out.println("Thanks for shopping!");
		
	}
}
