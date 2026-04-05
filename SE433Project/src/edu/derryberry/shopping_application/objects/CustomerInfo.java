package edu.derryberry.shopping_application.objects;

public class CustomerInfo {

		private String name;
		private String stateAbbrev;
		private String itemName;
		private int itemQuantity;
		private String shippingOption;
		
		public CustomerInfo(String name, String stateAbbrev, String itemName, int itemQuantity, String shippingOption) {
			this.name = name;
			this.stateAbbrev = stateAbbrev;
			this.itemName = itemName;
			this.itemQuantity = itemQuantity;
			this.shippingOption = shippingOption;
		}
		
		
}
