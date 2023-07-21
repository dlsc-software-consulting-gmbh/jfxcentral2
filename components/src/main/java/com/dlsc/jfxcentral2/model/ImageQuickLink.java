package com.dlsc.jfxcentral2.model;

public class ImageQuickLink extends QuickLink {

    private String imageUrl;

    public ImageQuickLink() {
    }

    public ImageQuickLink(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ImageQuickLink(String imageUrl, String linkUrl) {
        super(linkUrl);
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
