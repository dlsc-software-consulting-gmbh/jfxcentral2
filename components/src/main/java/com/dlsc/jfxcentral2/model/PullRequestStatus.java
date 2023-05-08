package com.dlsc.jfxcentral2.model;

public enum PullRequestStatus {

    ALL("All"),
    OPEN("Open"),
    CLOSED("Closed");

    private final String description;

    PullRequestStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
