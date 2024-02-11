package com.dlsc.jfxcentral2.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    /**
     * This class is a junit test class for the class StringUtil and particularly the method getDomainName.
     * getDomainName is a utility method that extracts the main domain from a URL string.
     */
    @Test
    void getDomainNameFromValidUrl() {
        String url = "https://www.google.com";
        String expectedDomain = "google";
        String actual = StringUtil.getDomainName(url);
        assertEquals(expectedDomain, actual, "Domain mismatch in url: " + url);
    }

    @Test
    void getDomainNameFromInvalidUrl() {
        String url = "not a valid url";
        assertNull(StringUtil.getDomainName(url));
    }

    @Test
    void getDomainNameFromNullUrl() {
        assertNull(StringUtil.getDomainName(null));
    }
}