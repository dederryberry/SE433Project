package edu.derryberry.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

    @Test
    public void testConstructorSetsFieldsStandard() {
        Customer customer = new Customer("Dustin Derryberry", "TN", ShippingOption.STANDARD);
        assertEquals("Dustin Derryberry", customer.getName());
        assertEquals("TN", customer.getState());
        assertEquals(ShippingOption.STANDARD, customer.getShippingOption());
    }

    @Test
    public void testConstructorSetsFieldsNextDay() {
        Customer customer = new Customer("Ben Harki", "IL", ShippingOption.NEXT_DAY);
        assertEquals("Ben Harki", customer.getName());
        assertEquals("IL", customer.getState());
        assertEquals(ShippingOption.NEXT_DAY, customer.getShippingOption());
    }
}
