package com.dlsc.jfxcentral2.model;

public class CreditModel {
    private String name;
    private String id;
    private String version;
    private License license;
    private String description;
    private String url;

    public CreditModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public License getLicence() {
        return license;
    }

    public void setLicence(License license) {
        this.license = license;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CreditModel{" +
                "name='" + name + '\'' +
                ", licence=" + license +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
