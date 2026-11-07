package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

@Mapped
public class Maven {
    private Dependency dependency;

    public Maven() {

    }

    public Dependency getDependency() {
        return dependency;
    }

    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    @Override
    public String toString() {
        if (dependency == null) {
            return "Null dependency";
        }
        return "<dependency>\n" +
                "    <groupId>" + dependency.getGroupId() + "</groupId>\n" +
                "    <artifactId>" + dependency.getArtifactId() + "</artifactId>\n" +
                "    <version>" + dependency.getVersion() + "</version>\n" +
                "</dependency>";
    }
}

