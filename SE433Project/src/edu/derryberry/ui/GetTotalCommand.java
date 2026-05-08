package edu.derryberry.ui;

import edu.derryberry.model.ShippingOption;
import edu.derryberry.model.ShoppingCart;

public class GetTotalCommand implements IMenuCommand {

	private ShoppingCart cart;
	private ShippingOption shippingOption;
	private String stateForTax;
	
	public GetTotalCommand(ShoppingCart cart, ShippingOption shippingOption, String state) {
		this.cart = cart;
		this.shippingOption = shippingOption;
		this.stateForTax = state;
	}
	
	public void execute() {
		
		double threshold = 50; // free shipping threshold for standard
		double subtotal = cart.getCartCost();
		double tax = calculateTax(subtotal, stateForTax);
		double shipping = calculateShipping(shippingOption, subtotal, threshold);
		double total = subtotal + tax + shipping;
		
		// set cart full cost...
		cart.setCartFullCost(total);
		
		System.out.println("Displaying totals...");
		System.out.println("Cart subtotal: " + subtotal);
		System.out.println("Tax: " + tax);
		System.out.println("Shipping: " + shipping);
		System.out.println("Total: " + total);
		
	}
	
	// IL, CA, NY have 6% sales tax; other states have none
	public static double calculateTax(double subtotal, String state) {
		double taxRate = 0.0;
		if(state.equals("IL") || state.equals("CA") || state.equals("NY")) {
			taxRate = 0.06;
		}
		return taxRate * subtotal;
	}
	
	// shipping cost logic is in ShippingOption enum
	public static double calculateShipping(ShippingOption option, double purchasePrice, double threshold) {
		if(option == ShippingOption.STANDARD && purchasePrice > threshold) {
			return 0.00;
		}
		return option.getCost();
	}
	

}
