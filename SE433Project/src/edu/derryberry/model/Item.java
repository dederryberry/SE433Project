package edu.derryberry.model;

public class Item {

		private final String name;
		private final double cost;
		
		public Item(String name, double cost) {
			this.name = name;
			this.cost = cost;
		}
		
		public String getName() {
			return name;
		}
		
		public double getCost() {
			return cost;
		}
		
}
