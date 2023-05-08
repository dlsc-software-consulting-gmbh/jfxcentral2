package com.dlsc.jfxcentral2.model;

public enum VideoDate {
    ALL("All"),
    LESS_THAN_ONE_WEEK("Less than a week ago"),
    LESS_THAN_ONE_MONTH("Less than a month ago"),
    LESS_THAN_THREE_MONTH("Less than 3 month ago"),
    LESS_THAN_SIX_MONTH("Less than 6 month ago"),
    LESS_THAN_THIS_YEAR("Less than a year ago");

    private final String description;

    VideoDate(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
