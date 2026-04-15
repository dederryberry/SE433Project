package edu.derryberry.model;

public enum ShippingOption {
	
    STANDARD("Standard Shipping", 10.00),
    NEXT_DAY("Next-day Shipping", 25.00);

    private final String displayName;
    private final double cost;

    ShippingOption(String displayName, double cost) {
        this.displayName = displayName;
        this.cost = cost;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getCost() {
        return cost;
    }
}