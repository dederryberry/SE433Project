package edu.derryberry.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatUtilsTest {

    @Test
    public void testFormatsStandardPrice() {
        new FormatUtils(); // this line added to satisfy line coverage in PIT
        assertEquals("$9.99", FormatUtils.formatMoney(9.99));
    }

    @Test
    public void testFormatsZero() {
        assertEquals("$0.00", FormatUtils.formatMoney(0));
    }

    @Test
    public void testPadsToTwoDecimals() {
        assertEquals("$5.50", FormatUtils.formatMoney(5.5));
    }

    @Test
    public void testRoundsToTwoDecimals() {
        assertEquals("$100.00", FormatUtils.formatMoney(99.999));
    }
}
