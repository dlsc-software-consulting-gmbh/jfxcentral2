package com.dlsc.jfxcentral2.components;

public enum Target {
    DESKTOP,
    BROWSER,
    MOBILE;

    public boolean isMobile() {
        return this == Target.MOBILE;
    }

    public boolean isBrowser() {
        return this == Target.BROWSER;
    }

    public boolean isDesktop() {
        return this == Target.DESKTOP;
    }
}
