package com.dlsc.jfxcentral.data.model;

import com.codename1.annotations.Mapped;

import com.dlsc.jfxcentral.data.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Mapped
public class Download extends ModelObject {

    private String homepage;
    private DownloadType downloadType;
    private List<DownloadFile> files = new ArrayList<>();

    public Download() {
    }

    @Override
    public boolean matches(String searchPattern) {
        if (super.matches(searchPattern)) {
            return true;
        }

        for (DownloadFile file : getFiles()) {
            if (StringUtils.containsAnyIgnoreCase(file.getName(), searchPattern)) {
                return true;
            }
        }

        return false;
    }

    public DownloadType getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(DownloadType downloadType) {
        this.downloadType = downloadType;
    }

    public List<DownloadFile> getFiles() {
        return files;
    }

    public void setFiles(List<DownloadFile> files) {
        this.files = files;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
