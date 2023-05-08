package com.dlsc.jfxcentral2.model;

public enum PullRequestLabel {

    ALL("all"),
    RFR("rfr"),
    INTEGRATED("integrated"),
    READY("ready"),
    CSR("csr"),
    OCA("oca"),
    OCA_VERIFY("oca-verify");

    private final String description;

    PullRequestLabel(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
