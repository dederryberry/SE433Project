package edu.derryberry;

import edu.derryberry.model.*;
import edu.derryberry.service.*;
import edu.derryberry.ui.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ShoppingApplication {

	public static void main(String[] args) {

		// Load Store
		List<Item> list = loadItems("src/edu/derryberry/resources/store.csv");
		
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

	static List<Item> loadItems(String filePath) {
		List<Item> items = new ArrayList<>();
		try(BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
			reader.readLine(); // skip header
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if(line.isEmpty()) {
					continue;
				}
				String[] parts = line.split(",");
				Item item = new Item(parts[0].trim(), Double.parseDouble(parts[1].trim()));
				items.add(item);
			}
		} catch (IOException e) {
			System.err.println("Error reading store file: " + e.getMessage());
		}
		return items;
	}

}
