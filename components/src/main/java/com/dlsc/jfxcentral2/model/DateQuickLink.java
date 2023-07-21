package com.dlsc.jfxcentral2.model;

import org.kordamp.ikonli.Ikon;

import java.time.LocalDate;

public class DateQuickLink extends NormalQuickLink {

    private LocalDate date;

    public DateQuickLink() {
    }

    public DateQuickLink(String title, String description, Ikon ikon, String linkUrl, LocalDate date) {
        super(title, description, ikon, linkUrl);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
