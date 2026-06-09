package edu.derryberry.ui;

import edu.derryberry.model.ShippingOption;
import edu.derryberry.model.ShoppingCart;
import edu.derryberry.service.FormatUtils;
import edu.derryberry.service.InputValidator;

public class GetTotalCommand implements IMenuCommand {

	private final ShoppingCart cart;
	private final ShippingOption shippingOption;
	private final String stateForTax;
	
	public GetTotalCommand(ShoppingCart cart, ShippingOption shippingOption, String state) {
		this.cart = cart;
		this.shippingOption = shippingOption;
		this.stateForTax = state;
	}
	
	public void execute() {

		try {
			InputValidator.validateCartNotEmpty(cart);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}

		double threshold = 50; // free shipping threshold for standard
		double subtotal = cart.getCartCost();
		double tax = calculateTax(subtotal, stateForTax);
		double shipping = calculateShipping(shippingOption, subtotal, threshold);
		double total = subtotal + tax + shipping;

		// validate purchase amount
		try {
			InputValidator.validatePurchaseAmount(total);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}
		
		System.out.println("Displaying totals...");
		System.out.println("Cart subtotal: " + FormatUtils.formatMoney(subtotal));
		System.out.println("Tax: " + FormatUtils.formatMoney(tax));
		System.out.println("Shipping: " + FormatUtils.formatMoney(shipping));
		System.out.println("Total: " + FormatUtils.formatMoney(total));
		
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
