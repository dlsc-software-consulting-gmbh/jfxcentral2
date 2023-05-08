package com.dlsc.jfxcentral2.model;

public enum AppDOMAIN {
    ALL("All"),
    FINANCE("Finance"),
    HEALTHCARE("Healthcare"),
    EDUCATION("Education");

    private final String description;

    AppDOMAIN(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
