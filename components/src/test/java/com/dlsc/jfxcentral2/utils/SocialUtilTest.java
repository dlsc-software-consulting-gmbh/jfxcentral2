package com.dlsc.jfxcentral2.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SocialUtilTest {
    
    // This class contains tests for the isSocialFeaturesEnabled method of the SocialUtil class.
    // The method checks for social feature availability based on VM argument or application environment (browser or not).

    @Test
    public void testIsSocialFeaturesEnabled_WhenVMArgumentIsSocial_AndValueIsTrue() {
        // Setup:
        // Setting VM argument "social" to true
        System.setProperty("social", "true");

        // Execution:
        // Calling isSocialFeaturesEnabled method
        var result = SocialUtil.isSocialFeaturesEnabled();

        // Assertion:
        // The result should be true as the VM argument "social" is set to true
        assertTrue(result);
    }

    @Test
    public void testIsSocialFeaturesEnabled_WhenVMArgumentIsSocial_AndValueIsFalse() {
        // Setup:
        // Setting VM argument "social" to false
        System.setProperty("social", "false");

        // Execution:
        // Calling isSocialFeaturesEnabled method
        var result = SocialUtil.isSocialFeaturesEnabled();

        // Assertion:
        // The result should be false as the VM argument "social" is set to false
        assertFalse(result);
    }
}