package edu.derryberry.ui;

import java.util.HashMap;
import java.util.Map;

public class MenuController {

	private final Map<String, IMenuCommand> commands;
	
	public MenuController() {
		this.commands = new HashMap<>();
	}
	
	public void add(String key, IMenuCommand command) {
		commands.put(key, command);
	}
	
	public void execute(String key) {
		IMenuCommand command = commands.get(key);
		if(command != null) {
			command.execute();
		} else {
			System.out.println("Unknown command!");
		}
	}
}
