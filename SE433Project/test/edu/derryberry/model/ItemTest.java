package edu.derryberry.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    @Test
    void testGetName() {
        Item item = new Item("Soap", 9.99);
        assertEquals("Soap", item.getName());
    }
}
