package com.dlsc.jfxcentral2.utils;

public enum View {
    HOME,
    OPENJFX,
    PEOPLE,
    TUTORIALS,
    REAL_WORLD,
    COMPANIES,
    TOOLS,
    LIBRARIES,
    BLOGS,
    BOOKS,
    VIDEOS,
    DOWNLOADS,
    TIPS,
    ICONS,
    UTILITIES,
    LEARN_JAVAFX,
    LEARN_MOBILE,
    LEARN_RASPBERRYPI;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}