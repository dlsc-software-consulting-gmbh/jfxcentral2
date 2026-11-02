package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

@Mapped
public class Image {

    private String path;
    private String title;
    private String description;

    public Image() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
