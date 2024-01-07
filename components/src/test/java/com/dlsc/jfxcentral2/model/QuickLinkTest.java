package com.dlsc.jfxcentral2.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuickLinkTest {

    /**
     * Testing the QuickLink class and its methods
     * This class is related for linking URL's and is a model of the project
     * The tests included verify the class functionality
     */
    
    @Test
    public void testSetAndThenGetLinkUrl() {
        QuickLink quickLink = new QuickLink();
        String url = "https://www.google.com/";
        quickLink.setLinkUrl(url);
        assertEquals(url, quickLink.getLinkUrl());
    }

    @Test
    public void testNoArgConstructorLinkUrlIsNull() {
        QuickLink quickLink = new QuickLink();
        assertNull(quickLink.getLinkUrl());
    }

    @Test
    public void testArgConstructorSetsLinkUrlCorrectly() {
        String url = "https://www.example.com/";
        QuickLink quickLink = new QuickLink(url);
        assertEquals(url, quickLink.getLinkUrl());
    }
}