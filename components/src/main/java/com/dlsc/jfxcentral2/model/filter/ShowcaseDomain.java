package com.dlsc.jfxcentral2.model.filter;

public enum ShowcaseDomain {
    ALL("All"),
    FINANCE("Finance"),
    HEALTHCARE("Healthcare"),
    EDUCATION("Education");

    private final String description;

    ShowcaseDomain(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
