package edu.derryberry.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerStateTest {

    @Test
    public void testStartsEmptyWithNoCustomer() {
        CustomerState customerState = new CustomerState();
        assertTrue(customerState.isEmpty());
        assertNull(customerState.getActiveCustomer());
    }

    @Test
    public void setActiveCustomerStoresTheCustomer() {
        CustomerState customerState = new CustomerState();
        Customer customer = new Customer("Dustin Derryberry", "TN", ShippingOption.STANDARD);
        customerState.setActiveCustomer(customer);
        assertSame(customer, customerState.getActiveCustomer());
        assertFalse(customerState.isEmpty());
    }

    @Test
    public void settingNewCustomerReplacesOldOne() {
        CustomerState customerState = new CustomerState();
        Customer customer = new Customer("Dustin Derryberry", "TN", ShippingOption.STANDARD);
        customerState.setActiveCustomer(customer);
        Customer replacement = new Customer("Ben Harki", "IL", ShippingOption.NEXT_DAY);
        customerState.setActiveCustomer(replacement);
        assertSame(replacement, customerState.getActiveCustomer());
    }
}
