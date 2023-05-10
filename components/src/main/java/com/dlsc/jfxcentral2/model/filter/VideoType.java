package com.dlsc.jfxcentral2.model.filter;

public enum VideoType {

    ALL("All"),
    INTERVIEW("Interview"),
    TUTORIAL("Tutorial"),
    DISCUSSION("Discussion"),
    CONFERENCE("Conference"),
    KEYNOTE("Keynote");

    private final String description;

    VideoType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
