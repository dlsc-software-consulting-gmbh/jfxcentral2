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

        expected = "0.30";
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

        expected = "5.75";
        actual = NumberUtil.trimTrailingZeros(5.750, 2);
        assertEquals(expected, actual, "Expected: " + expected + ", but got: " + actual);

        expected = "0.30";
        actual = NumberUtil.trimTrailingZeros(0.30000, 2);
        assertEquals(expected, actual, "Expected: " + expected + ", but got: " + actual);
    }

    /**
     * This method tests the trimTrailingZeros(double value, int decimalPlaces) method of the NumberUtils class
     * for different use cases focusing on one use case per test.
     */
    @Test
    public void testTrimTrailingZerosWithDecimalPlacesAndKeepDecimal() {

        String expected = "5.00";
        String actual = NumberUtil.trimTrailingZeros(5.00, true, 2);
        assertEquals(expected, actual, "Expected: " + expected + ", but got: " + actual);

        expected = "5.50";
        actual = NumberUtil.trimTrailingZeros(5.500, true, 2);
        assertEquals(expected, actual, "Expected: " + expected + ", but got: " + actual);

        expected = "0.30";
        actual = NumberUtil.trimTrailingZeros(0.30000, true, 2);
        assertEquals(expected, actual, "Expected: " + expected + ", but got: " + actual);
    }

    // Tests that the 'clamp' function correctly clamps a value that is less than the minimum
    // value to the minimum value.
    @Test
    public void testClampWithLessThanMinimumValue() {
        double value = -10.0;
        double min = 0.0;
        double max = 10.0;
        double expected = min;
        double actual = NumberUtil.clamp(value, min, max);
        assertEquals(expected, actual);
    }

    // Tests that the 'clamp' function correctly clamps a value that is more than the maximum
    // value to the maximum value.
    @Test
    public void testClampWithMoreThanMaximumValue() {
        double value = 20.0;
        double min = 0.0;
        double max = 10.0;
        double expected = max;
        double actual = NumberUtil.clamp(value, min, max);
        assertEquals(expected, actual);
    }

    // Tests that the 'clamp' function correctly clamps a value that is within the range of
    // minimum and maximum values to itself.
    @Test
    public void testClampWithWithinRangeValue() {
        double value = 5.0;
        double min = 0.0;
        double max = 10.0;
        double expected = value;
        double actual = NumberUtil.clamp(value, min, max);
        assertEquals(expected, actual);
    }
}