package edu.derryberry.model;

import edu.derryberry.service.FormatUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Store {

	private final Map<String, Item> items;
	
	public Store(List<Item> list) {
		this.items = new LinkedHashMap<>();
		for(Item item : list) {
			this.items.put(item.getName(), item);
		}
	}
	
	public Item getItemByName(String name) {
		return items.get(name);
	}
	
	public void showAllItems() {
		for(Item i : items.values()) {
			System.out.println("\t" + i.getName() + ": " + FormatUtils.formatMoney(i.getCost()));
		}
	}
}
