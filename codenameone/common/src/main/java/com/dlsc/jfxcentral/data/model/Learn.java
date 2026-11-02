package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

import com.dlsc.jfxcentral.data.util.StringUtils;

@Mapped
public class Learn extends ModelObject {

    private String sourceCodeUrl;

    public Learn() {
    }

    @Override
    public boolean matches(String searchPattern) {
        if (super.matches(searchPattern)) {
            return true;
        }

        return StringUtils.containsAnyIgnoreCase(sourceCodeUrl, searchPattern);
    }

    public String getSourceCodeUrl() {
        return sourceCodeUrl;
    }

    public void setSourceCodeUrl(String sourceCodeUrl) {
        this.sourceCodeUrl = sourceCodeUrl;
    }

}
