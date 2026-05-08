package edu.derryberry;

import edu.derryberry.model.*;
import edu.derryberry.service.*;
import edu.derryberry.ui.*;

import java.util.Scanner;


public class ShoppingApplication {

	public static void main(String[] args) {
		
		// initialize Store, Shopping Cart, Scanner
//		ArrayList<Item> storeList = new ArrayList<>();
//		Item itemOne = new Item("Soap", 9.99);
//		Item itemTwo = new Item("Batteries", 4.99);
//		Item itemThree = new Item("Bread", 2.99);
//		storeList.add(itemOne);
//		storeList.add(itemTwo);
//		storeList.add(itemThree);
//		
//		Store store = new Store(storeList);
//		ShoppingCart cart = new ShoppingCart();
	
		CustomerState customerState = new CustomerState();
		MenuController menu = new MenuController();
		Scanner scn = new Scanner(System.in);
		
		// add commands and execute launch command
		menu.add("1", new CustomerInfoCommand(scn, customerState));
		menu.add("2", new GoShoppingCommand(scn));
		
		// Welcome message
		System.out.println("Welcome to Dustin's shop!");
		
		while(true) {
			System.out.println("Please select an option: ");
			System.out.println("\t1) Enter customer info");
			System.out.println("\t2) Go shopping");
			System.out.println("\t3) Exit Shop");
			String input = scn.nextLine();
			
			if(input.equalsIgnoreCase("3")) break;
			
			menu.execute(input);
			System.out.println();
		}
		scn.close();
	}

}
