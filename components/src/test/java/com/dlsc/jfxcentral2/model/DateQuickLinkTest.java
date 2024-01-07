package com.dlsc.jfxcentral2.model;

import org.junit.jupiter.api.Test;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateQuickLinkTest {

    @Test
    void getDateTest() {
        LocalDate testDate = LocalDate.of(2022, 2, 21);
        DateQuickLink dateQuickLink = new DateQuickLink(
                "Test Title",
                "Test Description",
                FontAwesomeSolid.ANCHOR,
                "https://example.com",
                testDate
        );
        assertEquals(testDate, dateQuickLink.getDate());
    }
  
    @Test
    void setDateTest() {
        LocalDate testDate = LocalDate.of(2022, 2, 22);
        DateQuickLink dateQuickLink = new DateQuickLink();
        dateQuickLink.setDate(testDate);
        assertEquals(testDate, dateQuickLink.getDate());
    }
}