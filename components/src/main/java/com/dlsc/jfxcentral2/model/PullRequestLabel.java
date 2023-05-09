package com.dlsc.jfxcentral2.model;

public enum PullRequestLabel {

    ALL("All"),
    RFR("Request for Review"),
    INTEGRATED("Integrated"),
    READY("Ready"),
    CSR("Change Specification Request"),
    OCA("Oracle Contributor Agreement"),
    OCA_VERIFY("OCA Verify");

    private final String description;

    PullRequestLabel(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
