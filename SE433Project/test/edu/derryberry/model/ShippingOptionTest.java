package edu.derryberry.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShippingOptionTest {

    @Test
    public void testStandardShippingIsCorrect() {
        assertEquals("Standard Shipping", ShippingOption.STANDARD.getDisplayName());
        assertEquals(10.00, ShippingOption.STANDARD.getCost());
    }

    @Test
    public void testNextDayShippingIsCorrect() {
        assertEquals("Next-day Shipping", ShippingOption.NEXT_DAY.getDisplayName());
        assertEquals(25.00, ShippingOption.NEXT_DAY.getCost());
    }

    @Test
    public void testValuesAndValuesOf() {
        assertEquals(ShippingOption.STANDARD, ShippingOption.valueOf("STANDARD"));
        assertEquals(ShippingOption.NEXT_DAY, ShippingOption.valueOf("NEXT_DAY"));
        assertEquals(2, ShippingOption.values().length);
    }
}
