package com.dlsc.jfxcentral2.model;

public class License {
    private String type;
    private String version;
    private String url;

    public License() {
    }

    public License(String type, String version) {
        this.type = type;
        this.version = version;
    }

    public License(String type, String version, String url) {
        this(type, version);
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Licence{" +
                "name='" + type + '\'' +
                ", version='" + version + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
