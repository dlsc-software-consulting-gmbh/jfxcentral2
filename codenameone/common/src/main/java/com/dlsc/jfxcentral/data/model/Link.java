package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

@Mapped
public class Link {

    private String text;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
