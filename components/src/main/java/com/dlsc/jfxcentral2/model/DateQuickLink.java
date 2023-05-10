package com.dlsc.jfxcentral2.model;

import org.kordamp.ikonli.Ikon;

import java.time.ZonedDateTime;

public class DateQuickLink extends NormalQuickLink {

    private ZonedDateTime date;

    public DateQuickLink() {
    }

    public DateQuickLink(String title, String description, Ikon ikon, String linkUrl, ZonedDateTime date) {
        super(title, description, ikon, linkUrl);
        this.date = date;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
}
