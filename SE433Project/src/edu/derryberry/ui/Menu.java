package edu.derryberry.ui;

import java.util.Scanner;

import edu.derryberry.model.Customer;
import edu.derryberry.model.ShoppingCart;
import edu.derryberry.model.Store;

public class Menu {

	private Scanner scn;
	private Store store;
	private Customer customer;
	private ShoppingCart cart;

	// initialize menu
	public Menu(Scanner scn, Store store, ShoppingCart cart) {
		this.scn = scn;
	}
	
	public void introSequence() {
		System.out.println("Welcome to Dustin's Shop!");
		System.out.println("Please select an option: ");
	}
	
}
