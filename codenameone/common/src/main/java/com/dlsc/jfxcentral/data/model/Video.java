package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

import com.dlsc.jfxcentral.data.util.StringUtils;

import java.time.LocalDate;

@Mapped
public class Video extends ModelObject {

    private String type;
    private String domain;
    private String event;
    private String platform;
    private LocalDate publishedOn;
    // A string in videos.json ("12"); Gson coerced it to int, the mapper does not.
    private String minutes;

    public Video() {
    }

    @Override
    public boolean matches(String searchPattern) {
        if (super.matches(searchPattern)) {
            return true;
        }

        if (StringUtils.containsAnyIgnoreCase(type, searchPattern)) {
            return true;
        }

        if (StringUtils.containsAnyIgnoreCase(domain, searchPattern)) {
            return true;
        }

        if (StringUtils.containsAnyIgnoreCase(platform, searchPattern)) {
            return true;
        }

        if (StringUtils.containsAnyIgnoreCase(event, searchPattern)) {
            return true;
        }

        return false;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getType() {
        return type;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }
}
