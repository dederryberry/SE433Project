package edu.derryberry;

import edu.derryberry.model.*;
import edu.derryberry.service.*;
import edu.derryberry.ui.*;

import java.util.ArrayList;
import java.util.Scanner;



public class ShoppingApplication {

	public static void main(String[] args) {
	
		// initialize Store, Shopping Cart, Scanner
		ArrayList<Item> storeList = new ArrayList<>();
		Item itemOne = new Item("Soap", 9.99);
		Item itemTwo = new Item("Batteries", 4.99);
		Item itemThree = new Item("Bread", 2.99);
		storeList.add(itemOne);
		storeList.add(itemTwo);
		storeList.add(itemThree);
		
		Store store = new Store(storeList);
		ShoppingCart cart = new ShoppingCart();
		Scanner scn = new Scanner(System.in);
	
		// launch menu
		Menu menu = new Menu(scn, store, cart);
		menu.introSequence();
		
	}

}
