package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

import com.dlsc.jfxcentral.data.util.StringUtils;

@Mapped
public class Company extends ModelObject {

    private String homepage;
    private boolean consulting;
    private boolean freelancing;

    public Company() {
    }

    public boolean isConsulting() {
        return consulting;
    }

    public void setConsulting(boolean consulting) {
        this.consulting = consulting;
    }

    public boolean isFreelancing() {
        return freelancing;
    }

    public void setFreelancing(boolean freelancing) {
        this.freelancing = freelancing;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
