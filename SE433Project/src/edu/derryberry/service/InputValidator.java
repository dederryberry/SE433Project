package edu.derryberry.service;

import edu.derryberry.model.Item;
import edu.derryberry.model.ShoppingCart;
import edu.derryberry.model.Store;

public class InputValidator {

    public static int parseQuantity(String input) {
        int quantity;
        try {
            quantity = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantity must be a whole number.");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }
        return quantity;
    }

    public static void validatePurchaseAmount(double amount) {
        if (amount < 1.00) {
            throw new IllegalArgumentException("Purchase amount must be at least $1.00.");
        }
        if (amount > 99999.99) {
            throw new IllegalArgumentException("Purchase amount cannot exceed $99,999.99.");
        }
    }

    public static Item validateItem(Store store, String name) {
        Item item = store.getItemByName(name);
        if (item == null) {
            throw new IllegalArgumentException("Item '" + name + "' not found.");
        }
        return item;
    }

    public static void validateCartNotEmpty(ShoppingCart cart) {
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("Your cart is empty.");
        }
    }
}
