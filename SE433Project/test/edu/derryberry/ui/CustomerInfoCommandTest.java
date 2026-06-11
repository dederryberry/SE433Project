package edu.derryberry.ui;

import edu.derryberry.model.Customer;
import edu.derryberry.model.CustomerState;
import edu.derryberry.model.ShippingOption;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerInfoCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private CustomerState customerState;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
        customerState = new CustomerState();
    }

    @AfterEach
    public void teardown() {
        System.setOut(originalOut);
    }

    private void run(String input) {
        Scanner scn = new Scanner(input);
        new CustomerInfoCommand(scn, customerState).execute();
    }

    @Test
    public void testDisplaysAllPrompts() {
        run("Dustin Derryberry\ntn\n1\n");
        String output = outContent.toString();
        assertTrue(output.contains("Customer info!"));
        assertTrue(output.contains("Enter first and last name (e.g. Dustin Derryberry): "));
        assertTrue(output.contains("Enter state of residence (e.g. IL, TN, GA): "));
        assertTrue(output.contains("Enter desired shipping option: "));
        assertTrue(output.contains("\t1) Standard"));
        assertTrue(output.contains("\t2) Next day"));
        assertTrue(output.contains("Thanks for the info!"));
    }

    @Test
    public void testValidInputStandardShipping() {
        run("Dustin Derryberry\nTN\n1\n");
        Customer customer = customerState.getActiveCustomer();
        assertNotNull(customer);
        assertEquals("Dustin Derryberry", customer.getName());
        assertEquals("TN", customer.getState());
        assertEquals(ShippingOption.STANDARD, customer.getShippingOption());
        assertTrue(outContent.toString().contains("Thanks for the info!"));
    }

    @Test
    public void testValidInputNextDayShipping() {
        run("Ben Harki\nIL\n2\n");
        Customer customer = customerState.getActiveCustomer();
        assertNotNull(customer);
        assertEquals("Ben Harki", customer.getName());
        assertEquals("IL", customer.getState());
        assertEquals(ShippingOption.NEXT_DAY, customer.getShippingOption());
        assertTrue(outContent.toString().contains("Thanks for the info!"));
    }

    @Test
    public void testInvalidStateAbortsNoCustomer() {
        run("Dustin Derryberry\nBU\n1\n");
        assertTrue(customerState.isEmpty());
        assertTrue(outContent.toString().contains("Error: 'BU' is not a valid state abbreviation."));
    }

    @Test
    public void testInvalidShippingAbortsNoCustomer() {
        run("Dustin Derryberry\nTN\n4\n");
        assertTrue(customerState.isEmpty());
        assertTrue(outContent.toString().contains("You did not select a valid shipping option."));
    }
}
