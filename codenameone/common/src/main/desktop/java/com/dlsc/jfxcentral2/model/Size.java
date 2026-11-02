package com.dlsc.jfxcentral2.model;

public enum Size {

    /**
     * PseudoClassName: sm
     */
    SMALL,

    /**
     * PseudoClassName: md
     */
    MEDIUM,

    /**
     * PseudoClassName: lg
     */
    LARGE;

    Size() {
        // constructor
    }

    public boolean isSmall() {
        return this == SMALL;
    }

    public boolean isMedium() {
        return this == MEDIUM;
    }

    public boolean isLarge() {
        return this == LARGE;
    }

}