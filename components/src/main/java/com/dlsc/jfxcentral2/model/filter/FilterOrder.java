package com.dlsc.jfxcentral2.model.filter;

public enum FilterOrder {
    DEFAULT("Default"),
    ASCENDING("From A to Z"),
    DESCENDING("From Z to A");

    private final String description;

    FilterOrder(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
