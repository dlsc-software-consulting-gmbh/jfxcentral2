package com.dlsc.jfxcentral2.components;

public enum Target {
    DESKTOP,
    BROWSER,
    TABLET,
    MOBILE;

    public boolean isComputer() {
        return isDesktop() || isBrowser();
    }

    public boolean isEmbedded() {
        return isTablet() || isMobile();
    }

    public boolean isMobile() {
        return this == Target.MOBILE;
    }

    public boolean isTablet() {
        return this == Target.TABLET;
    }

    public boolean isBrowser() {
        return this == Target.BROWSER;
    }

    public boolean isDesktop() {
        return this == Target.DESKTOP;
    }
}
