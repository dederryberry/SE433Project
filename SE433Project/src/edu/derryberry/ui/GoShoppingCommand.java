package edu.derryberry.ui;

import java.util.Scanner;

public class GoShoppingCommand implements IMenuCommand {

	private final Scanner scn;
	
	public GoShoppingCommand(Scanner scn) {
		this.scn = scn;
	}
	
	@Override
	public void execute() {
		System.out.println("Go shopping!");
	}
}
