package com.dlsc.jfxcentral2.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

  
/**
 * The NumberUtilTest class tests the methods of the NumberUtil class.
 */
public class NumberUtilTest {
    
    /**
     * This method tests the trimTrailingZeros(double value) method of the NumberUtils class 
     * for different use cases focusing on one use case per test.
     */
    @Test
    public void testTrimTrailingZeros() {

        String expected = "5";
        String actual = NumberUtil.trimTrailingZeros(5.00);
        assertEquals(expected, actual, "Expected: " + expected + ", but got: " + actual);

        expected = "5.75";
        actual = NumberUtil.trimTrailingZeros(5.7500);
        assertEquals(expected, actual, "Expected: " + expected + ", but got: " + actual);

        expected = "0.3";
        actual = NumberUtil.trimTrailingZeros(0.30000);
        assertEquals(expected, actual, "Expected: " + expected + ", but got: " + actual);
    }

    /**
     * This method tests the trimTrailingZeros(double value, int decimalPlaces) method of the NumberUtils class 
     * for different use cases focusing on one use case per test.
     */
    @Test
    public void testTrimTrailingZerosWithDecimalPlaces() {
        
        String expected = "5";
        String actual = NumberUtil.trimTrailingZeros(5.00, 2);
        assertEquals(expected, actual, "Expected: " + expected + ", but got: " + actual);

        expected = "5.50";
        actual = NumberUtil.trimTrailingZeros(5.500, 2);
        assertEquals(expected, actual, "Expected: " + expected + ", but got: " + actual);

        expected = "0.30";
        actual = NumberUtil.trimTrailingZeros(0.30000, 2);
        assertEquals(expected, actual, "Expected: " + expected + ", but got: " + actual);
    }

}