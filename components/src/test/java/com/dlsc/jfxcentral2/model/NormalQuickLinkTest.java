package com.dlsc.jfxcentral2.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kordamp.ikonli.Ikon;

/**
 * This class contains tests for the NormalQuickLink class in the com.dlsc.jfxcentral2.model package.
 * The NormalQuickLink class has methods for setting and retrieving data related to a QuickLink, including
 * a title, a description, an ikon, and the URL of the link itself.
 */
class NormalQuickLinkTest {

    @Test
    void testTitle() {
        NormalQuickLink normalQuickLink = new NormalQuickLink();
        String title = "Test Title";
        normalQuickLink.setTitle(title);
        Assertions.assertEquals(title, normalQuickLink.getTitle());
    }

    @Test
    void testDescription() {
        NormalQuickLink normalQuickLink = new NormalQuickLink();
        String description = "Test Description";
        normalQuickLink.setDescription(description);
        Assertions.assertEquals(description, normalQuickLink.getDescription());
    }

    @Test
    void testIkon() {
        NormalQuickLink normalQuickLink = new NormalQuickLink();
        Ikon ikon = new MockIkon();
        normalQuickLink.setIkon(ikon);
        Assertions.assertEquals(ikon, normalQuickLink.getIkon());
    }
}

/**
 * This class is just for testing purposes. It should mock the behavior of the real Ikon class
 */
class MockIkon implements Ikon {

    @Override
    public String getDescription() {
        return "MockIkon";
    }

    @Override
    public int getCode() {
        return 0;
    }
}