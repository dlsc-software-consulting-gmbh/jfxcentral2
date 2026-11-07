package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

@Mapped
public class Utility extends ModelObject {

    private DevelopmentStatus status;
    private boolean nativeSupported;
    private boolean onlineSupported;

    public Utility() {
    }

    public DevelopmentStatus getStatus() {
        return status;
    }

    public void setStatus(DevelopmentStatus status) {
        this.status = status;
    }

    public boolean isNativeSupported() {
        return nativeSupported;
    }

    public void setNativeSupported(boolean nativeSupported) {
        this.nativeSupported = nativeSupported;
    }

    public boolean isOnlineSupported() {
        return onlineSupported;
    }

    public void setOnlineSupported(boolean onlineSupported) {
        this.onlineSupported = onlineSupported;
    }
}
