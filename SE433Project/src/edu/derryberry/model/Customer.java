package edu.derryberry.model;

public class Customer {

	private final String name;
	private final String state;
	private final ShippingOption shippingOption;

	public Customer(String name, String state, ShippingOption shippingOption) {
		this.name = name;
		this.state = state;
		this.shippingOption = shippingOption;
	}
	
	public String getName() {
		return name;
	}
	
	public String getState() {
		return state;
	}
	
	public ShippingOption getShippingOption() {
		return shippingOption;
	}
	
}
