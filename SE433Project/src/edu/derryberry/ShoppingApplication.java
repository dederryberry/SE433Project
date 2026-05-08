package edu.derryberry;

import edu.derryberry.model.*;
import edu.derryberry.service.*;
import edu.derryberry.ui.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ShoppingApplication {

	public static void main(String[] args) {
		
		// Test Store
		List<Item> list = new ArrayList<>();
		list.add(new Item("Soap", 9.99));
		list.add(new Item("Batteries", 13.49));
		list.add(new Item("Bread", 3.99));
		
		// initialize store, customerState, shopping cart, menu, and scanner
		Store store = new Store(list);
		CustomerState customerState = new CustomerState();
		ShoppingCart cart = new ShoppingCart();
		MenuController menu = new MenuController();
		Scanner scn = new Scanner(System.in);
		
		// add menu commands
		menu.add("1", new CustomerInfoCommand(scn, customerState));
		menu.add("2", new GoShoppingCommand(scn, customerState, store, cart));
		menu.add("3", new ExitCommand());
		
		// Welcome message
		System.out.println("Welcome to Dustin's shop!");
		
		// menu 
		while(true) {

			System.out.println("Please select an option: ");
			System.out.println("\t1) Enter customer info");
			System.out.println("\t2) Go shopping");
			System.out.println("\t3) Exit Shop");
			String input = scn.nextLine();
			
			menu.execute(input);
			if(input.equalsIgnoreCase("3")) {
				break;
			}
			
			System.out.println();
		}
		scn.close();
	}

}
