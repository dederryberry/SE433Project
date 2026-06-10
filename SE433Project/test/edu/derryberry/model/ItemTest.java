package edu.derryberry.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    @Test
    public void testGetName() {
        Item item = new Item("Soap", 9.99);
        assertEquals("Soap", item.getName());
    }

    @Test
    public void testGetCost() {
        Item item = new Item("Soap", 9.99);
        assertEquals(9.99, item.getCost());
    }

}
