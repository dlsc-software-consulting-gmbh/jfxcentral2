package com.dlsc.jfxcentral2.model.filter;

public enum ShowcaseType {
    ALL("All"),
    TOOLS("Tools"),
    BUSINESS("Business"),
    OTHER("Other");

    private final String description;

    ShowcaseType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
