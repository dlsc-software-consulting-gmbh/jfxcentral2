package com.dlsc.jfxcentral2.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SenaptQuickLinkTest {

    /**
     * A test class for the SenaptQuickLink class.
     * This class tests the method in the SenaptQuickLink class.
     *
     * The SenaptQuickLinkTest constructor creates a new instance of SenaptQuickLink and 
     * tests to see if it is working as expected.
     * 
     * The testSenaptQuickLinkMethod creates a new SenaptQuickLink, and 
     * verifies that the URL set in the superclass is correct.
     */
    @Test
    public void testSenaptQuickLinkMethod() {
        SenaptQuickLink senaptQuickLink = new SenaptQuickLink();
        assertEquals("https://www.senapt.co.uk/", senaptQuickLink.getLinkUrl(), "The URL does not match the expected URL.");
    }
}