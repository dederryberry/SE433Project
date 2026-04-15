package edu.derryberry.ui;

import java.util.Scanner;

import edu.derryberry.model.Customer;
import edu.derryberry.model.ShoppingCart;
import edu.derryberry.model.Store;

public class Menu {

	private Scanner scanner;
	private Store store;
	private ShoppingCart cart;
	private Customer customer;
	
	public Menu() {
		this.scanner = new Scanner(System.in);
		
	}
}
