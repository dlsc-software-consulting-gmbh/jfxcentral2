package com.dlsc.jfxcentral2.model;

public enum VideoLength {
    ALL("All"),
    FIVE_TO_FIFTEEN("5-15 mins"),
    FIFTEEN_TO_SIXTY("15-60 mins"),
    LESS_THAN_FIVE("Less than 5 mins"),
    SIXTY_TO_MORE("More than 60 mins");

    private final String description;

    VideoLength(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
