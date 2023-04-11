package com.dlsc.jfxcentral2.components;

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
