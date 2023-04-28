package com.dlsc.jfxcentral2.model;

public class SimpleHeaderInfo {
    private String name;
    private String website;
    private boolean saved;
    private boolean liked;
    private String description;


    public SimpleHeaderInfo() {
    }

    public SimpleHeaderInfo(String name, String description, String website, boolean saved, boolean liked) {
        this.name = name;
        this.description = description;
        this.website = website;
        this.saved = saved;
        this.liked = liked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}