package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

@Mapped
public class Installing {
    private String gradle;

    private Maven maven;

    public Installing() {
    }

    public String getGradle() {
        return gradle;
    }

    public void setGradle(String gradle) {
        this.gradle = gradle;
    }

    public Maven getMaven() {
        return maven;
    }

    public void setMaven(Maven maven) {
        this.maven = maven;
    }
}

