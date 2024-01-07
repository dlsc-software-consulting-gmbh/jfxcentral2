package com.dlsc.jfxcentral2.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This is a test class for the NumberUtil class.
// It tests the functionality of the 'clamp' method provided in the NumberUtil class.
public class NumberUtilUnitTest {

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