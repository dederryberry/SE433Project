package edu.derryberry.service;

import edu.derryberry.model.ShippingOption;

// simple logic class to get the cost of shipping based on shipping option
// takes purchase price and free threshold into account
public class ShippingCalculator {

	public static double calculate(ShippingOption option, double purchasePrice, double threshold) {
		if(option == ShippingOption.STANDARD && purchasePrice > threshold) {
			return 0.00;
		}
		return option.getCost();
	}
	
}
