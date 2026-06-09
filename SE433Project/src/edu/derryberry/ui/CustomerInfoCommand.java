package edu.derryberry.ui;

import java.util.Scanner;

import edu.derryberry.model.Customer;
import edu.derryberry.model.CustomerState;
import edu.derryberry.model.ShippingOption;

public class CustomerInfoCommand implements IMenuCommand{

	private final Scanner scn;
	private final CustomerState customerState;
	
	
	public CustomerInfoCommand(Scanner scn, CustomerState customerState) {
		this.scn = scn;
		this.customerState = customerState;
	}
	
	@Override
	public void execute() {
		System.out.println("Customer info!");
		
		System.out.print("Enter first and last name (e.g. Dustin Derryberry): ");
		String name = scn.nextLine();
		
		System.out.print("Enter state of residence (e.g. IL, TN, GA): ");
		String state = scn.nextLine().toUpperCase();
		
		System.out.println("Enter desired shipping option: ");
		System.out.println("\t1) Standard");
		System.out.println("\t2) Next day");
		
		String shippingOptionText = scn.nextLine();
		ShippingOption shippingOption;
		if(shippingOptionText.equals("1")) {
			shippingOption = ShippingOption.STANDARD;
		} else if(shippingOptionText.equals("2")) {
			shippingOption = ShippingOption.NEXT_DAY;
		} else {
			System.out.println("You did not select a valid shipping option. Please try again.");
			return;
		}
		
		customerState.setActiveCustomer(new Customer(name, state, shippingOption));
		System.out.println("Thanks for the info! You can now go shopping. ");
	}
}
