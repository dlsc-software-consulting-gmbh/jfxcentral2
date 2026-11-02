package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

@Mapped
public class Blog extends ModelObject {

    private String url;
    private String companyId;
    private String feed;

    public Blog() {
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }
}
