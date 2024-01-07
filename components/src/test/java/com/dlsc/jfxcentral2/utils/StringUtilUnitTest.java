package com.dlsc.jfxcentral2.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * StringUtilUnitTest is a test class for StringUtil. It aims to test 
 * the functionality of the method formatFileSize within StringUtil.
 */
public class StringUtilUnitTest {

    /**
     * The formatFileSize method in StringUtil class formats file sizes from
     * bytes to a human-readable format (GB, MB, KB, Byte) depending on the
     * size.
     */
    @Test
    public void testFormatFileSize_GB() {
        assertEquals("1.0 GB", StringUtil.formatFileSize(1073741824));
    }

    @Test
    public void testFormatFileSize_MB_Over_100() {
        assertEquals("200 MB", StringUtil.formatFileSize(209715200));
    }

    @Test
    public void testFormatFileSize_MB_Less_100() {
        assertEquals("99.0 MB", StringUtil.formatFileSize(103809024));
    }

    @Test
    public void testFormatFileSize_KB_Over_100() {
        assertEquals("200 KB", StringUtil.formatFileSize(204800));
    }

    @Test
    public void testFormatFileSize_KB_Less_100() {
        assertEquals("99.0 KB", StringUtil.formatFileSize(101376));
    }

    @Test
    public void testFormatFileSize_Bytes() {
        assertEquals("512 Byte", StringUtil.formatFileSize(512));
    }
}