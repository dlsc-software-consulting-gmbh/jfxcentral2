package com.dlsc.jfxcentral2.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LicenseTest {

    /**
     * Tests the License's constructor and getters.
     */
    @Test
    public void licenseConstructorAndGettersTest() {
        License license = new License("Type1", "Version1", "https://url1.com");

        assertEquals("Type1", license.getType());
        assertEquals("Version1", license.getVersion());
        assertEquals("https://url1.com", license.getUrl());
    }
    
    /**
     * Tests the License's setters and toString method.
     */
    @Test
    public void licenseSettersAndToStringTest() {
        License license = new License();
        license.setType("Type2");
        license.setVersion("Version2");
        license.setUrl("https://url2.com");

        assertEquals("Type2", license.getType());
        assertEquals("Version2", license.getVersion());
        assertEquals("https://url2.com", license.getUrl());

        String expectedString = "Licence{name='Type2', version='Version2', url='https://url2.com'}";
        assertEquals(expectedString, license.toString());
    }
}