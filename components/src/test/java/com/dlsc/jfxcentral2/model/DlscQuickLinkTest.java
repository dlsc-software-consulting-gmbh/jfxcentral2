package com.dlsc.jfxcentral2.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DlscQuickLinkTest {

    /**
     * A test class for the DlscQuickLink class.
     * This class tests the method in the DlscQuickLink class.
     *
     * The DlscQuickLinkTest constructor creates a new instance of DlscQuickLink and
     * tests to see if it is working as expected.
     * 
     * The testSenaptQuickLinkMethod creates a new DlscQuickLink, and
     * verifies that the URL set in the superclass is correct.
     */
    @Test
    public void testDlscQuickLinkMethod() {
        DlscQuickLink dlscQuickLink = new DlscQuickLink();
        assertEquals("https://www.dlsc.com/", dlscQuickLink.getLinkUrl(), "The URL does not match the expected URL.");
    }
}