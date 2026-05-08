package edu.derryberry.model;

public class CustomerState {
	
	private Customer activeCustomer;
	
	public void setActiveCustomer(Customer customer) {
		this.activeCustomer = customer;
	}
	
	public Customer getActiveCustomer() {
		return activeCustomer;
	}
	
	public boolean isEmpty() {
		return activeCustomer == null;
	}
	
}
