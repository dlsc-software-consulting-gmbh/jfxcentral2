package com.dlsc.jfxcentral2.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditModelTest {

    @Test
    public void testSetName() {
        CreditModel model = new CreditModel();
        model.setName("TestName");
        assertEquals("TestName", model.getName());
    }

    @Test
    public void testSetLicence() {
        CreditModel model = new CreditModel();
        License license = new License();
        model.setLicence(license);
        assertEquals(license, model.getLicence());
    }

    @Test
    public void testSetDescription() {
        CreditModel model = new CreditModel();
        model.setDescription("TestDescription");
        assertEquals("TestDescription", model.getDescription());
    }

    @Test
    public void testSetUrl() {
        CreditModel model = new CreditModel();
        model.setUrl("TestUrl");
        assertEquals("TestUrl", model.getUrl());
    }

    @Test
    public void testSetVersion() {
        CreditModel model = new CreditModel();
        model.setVersion("TestVersion");
        assertEquals("TestVersion", model.getVersion());
    }

    @Test
    public void testSetId() {
        CreditModel model = new CreditModel();
        model.setId("TestId");
        assertEquals("TestId", model.getId());
    }
}