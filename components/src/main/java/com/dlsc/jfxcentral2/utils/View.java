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
        String name = name().toLowerCase();
        if (this != REAL_WORLD) {
            return name.replace("_", "-");
        }
        return name;
    }
}