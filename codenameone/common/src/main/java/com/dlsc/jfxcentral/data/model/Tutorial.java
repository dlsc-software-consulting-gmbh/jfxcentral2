package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

@Mapped
public class Tutorial extends ModelObject {

    public enum Format {
        VIDEO,
        WEB
    }

    private String url;
    private boolean commercial;
    private String companyId;
    private Format format;

    public Tutorial() {
    }

    public Format getType() {
        return format;
    }

    public void setType(Format format) {
        this.format = format;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public boolean isCommercial() {
        return commercial;
    }

    public void setCommercial(boolean commercial) {
        this.commercial = commercial;
    }
}
