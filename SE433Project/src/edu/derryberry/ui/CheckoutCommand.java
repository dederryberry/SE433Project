package edu.derryberry.ui;

import edu.derryberry.model.Customer;
import edu.derryberry.model.CustomerState;
import edu.derryberry.model.ShoppingCart;
import edu.derryberry.service.FormatUtils;
import edu.derryberry.service.InputValidator;

public class CheckoutCommand implements IMenuCommand {
	
	private final CustomerState customerState;
	private final ShoppingCart cart;
	
	public CheckoutCommand(CustomerState customerState, ShoppingCart cart) {
		this.customerState = customerState;
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

		// get customer object
		Customer customer = customerState.getActiveCustomer();
		double total = calculateCartTotal(customer);

		// validate cart total
		try {
			InputValidator.validatePurchaseAmount(total);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}

		// customer info
		System.out.println("Transaction completed!");
		System.out.println("Name: " + customer.getName());
		System.out.println("State of Residence: " + customer.getState());
		System.out.println("Shipping Option: " + customer.getShippingOption().getDisplayName());
		
		// cart info including cost
		System.out.println("Shopping Cart: ");
		cart.printCartItems();
		System.out.println("Final cost: " + FormatUtils.formatMoney(calculateCartTotal(customer)));
		
		System.out.println("Thanks for shopping!");
		cart.emptyCart();
	}

	private double calculateCartTotal(Customer customer) {
		double threshold = 50;
		double subtotal = cart.getCartCost();
		double tax = GetTotalCommand.calculateTax(subtotal, customer.getState());
		double shipping = GetTotalCommand.calculateShipping(customer.getShippingOption(), subtotal, threshold);
		return subtotal + tax + shipping;
	}
}
