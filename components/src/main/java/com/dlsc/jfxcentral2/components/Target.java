package com.dlsc.jfxcentral2.components;

public enum Target {
    DESKTOP,
    BROWSER,
    TABLET,
    MOBILE;

    public boolean isComputer() {
        return this == Target.DESKTOP || this == BROWSER;
    }

    public boolean isEmbedded() {
        return this == Target.TABLET || this == MOBILE;
    }
}
