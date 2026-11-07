package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

import com.dlsc.jfxcentral.data.util.StringUtils;

@Mapped
public class RealWorldApp extends ModelObject {

    private String company;
    private String domain;
    private String url;
    private String location;

    public RealWorldApp() {
    }

    @Override
    public boolean matches(String searchPattern) {
        if (super.matches(searchPattern)) {
            return true;
        }

        if (StringUtils.containsAnyIgnoreCase(company, searchPattern)) {
            return true;
        }

        if (StringUtils.containsAnyIgnoreCase(domain, searchPattern)) {
            return true;
        }

        return false;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
