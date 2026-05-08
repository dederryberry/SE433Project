package edu.derryberry.ui;

public class LaunchCommand implements IMenuCommand {
		public void execute() {
			System.out.println("Welcome to Dustin's Shop!");
			System.out.println("Please select an option: ");
			System.out.println("\t1) Enter customer info");
			System.out.println("\t2) Exit Shop");
		}
}
