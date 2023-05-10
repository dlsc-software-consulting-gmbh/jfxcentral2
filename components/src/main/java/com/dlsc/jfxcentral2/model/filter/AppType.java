package com.dlsc.jfxcentral2.model.filter;

public enum AppType {
    ALL("All"),
    TOOLS("Tools"),
    BUSINESS("Business"),
    OTHER("Other");

    private final String description;

    AppType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
