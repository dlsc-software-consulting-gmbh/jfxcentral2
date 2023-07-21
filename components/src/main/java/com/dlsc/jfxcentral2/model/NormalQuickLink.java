package com.dlsc.jfxcentral2.model;

import org.kordamp.ikonli.Ikon;

public class NormalQuickLink extends QuickLink {

    private String title;
    private String description;
    private Ikon ikon;

    public NormalQuickLink() {
    }

    public NormalQuickLink(String title, String description, Ikon ikon, String linkUrl) {
        super(linkUrl);
        this.title = title;
        this.description = description;
        this.ikon = ikon;
    }

    public Ikon getIkon() {
        return ikon;
    }

    public void setIkon(Ikon ikon) {
        this.ikon = ikon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
