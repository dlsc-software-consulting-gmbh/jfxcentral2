package com.dlsc.jfxcentral2.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ImageQuickLinkTest {

    /**
     * Test for the ImageQuickLink No Argument Constructor method.
     */
    @Test
    void noArgsConstructor() {
        ImageQuickLink imageQuickLink = new ImageQuickLink();
        assertNull(imageQuickLink.getImageUrl());
    }

    /**
     * Test for the ImageQuickLink Single Argument Constructor method.
     */
    @Test
    void singleArgsConstructor() {
        String imageUrl = "http://example.com/image.jpg";
        ImageQuickLink imageQuickLink = new ImageQuickLink(imageUrl);
        assertEquals(imageUrl, imageQuickLink.getImageUrl());
    }

    /**
     * Test for the ImageQuickLink Two Argument Constructor method.
     */
    @Test
    void twoArgsConstructor() {
        String imageUrl = "http://example.com/image.jpg";
        String linkUrl = "http://example.com/link";
        ImageQuickLink imageQuickLink = new ImageQuickLink(imageUrl, linkUrl);
        assertEquals(imageUrl, imageQuickLink.getImageUrl());
        assertEquals(linkUrl, imageQuickLink.getLinkUrl());
    }

    /**
     * Test for the setImageUrl method in the class ImageQuickLink.
     */
    @Test
    void setImageUrl() {
        ImageQuickLink imageQuickLink = new ImageQuickLink();
        String imageUrl = "http://example.com/newimage.jpg";
        imageQuickLink.setImageUrl(imageUrl);
        assertEquals(imageUrl, imageQuickLink.getImageUrl());
    }
}