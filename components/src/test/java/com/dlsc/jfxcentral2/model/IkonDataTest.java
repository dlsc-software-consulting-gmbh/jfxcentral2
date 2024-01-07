package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral.data.model.IkonliPack;
import org.junit.jupiter.api.Test;
import org.kordamp.ikonli.IkonProvider;
import org.kordamp.ikonli.fontawesome.FontAwesomeIkonProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IkonDataTest {

    @Test
    public void testCompareTo() {
        IkonData ikonData1 = new IkonData("test", null);
        IkonData ikonData2 = new IkonData("test", null);
        assertEquals(0, ikonData1.compareTo(ikonData2));
    }

    @Test
    public void testGetIkonliPack() {
        IkonData ikonData = new IkonData();
        IkonliPack ikonliPack = new IkonliPack();
        ikonData.setIkonliPack(ikonliPack);
        assertEquals(ikonliPack, ikonData.getIkonliPack());
    }

    @Test
    public void testSetIkonliPack() {
        IkonData ikonData = new IkonData();
        IkonliPack ikonliPack = new IkonliPack();
        ikonData.setIkonliPack(ikonliPack);
        assertEquals(ikonliPack, ikonData.getIkonliPack());
    }

    @Test
    public void testGetIkonProvider() {
        IkonData ikonData = new IkonData();
        IkonProvider ikonProvider = new FontAwesomeIkonProvider();
        ikonData.setIkonProvider(ikonProvider);
        assertEquals(ikonProvider, ikonData.getIkonProvider());
    }

    @Test
    public void testSetIkonProvider() {
        IkonData ikonData = new IkonData();
        IkonProvider ikonProvider = new FontAwesomeIkonProvider();
        ikonData.setIkonProvider(ikonProvider);
        assertEquals(ikonProvider, ikonData.getIkonProvider());
    }

    @Test
    public void testToString() {
        IkonData ikonData = new IkonData("test", null);
        assertEquals("test", ikonData.toString());
    }

    @Test
    public void testName() {
        IkonData ikonData = new IkonData("test", null);
        assertEquals("test", ikonData.getName());
    }

    @Test
    public void testSetName() {
        IkonData ikonData = new IkonData();
        ikonData.setName("test");
        assertEquals("test", ikonData.getName());
    }

    @Test
    public void testOf() {
        IkonProvider ikonProvider = new FontAwesomeIkonProvider();
        IkonData ikonData = IkonData.of(ikonProvider);
        assertEquals(ikonProvider.getIkon().getSimpleName(), ikonData.getName());
        assertEquals(ikonProvider, ikonData.getIkonProvider());
    }
}