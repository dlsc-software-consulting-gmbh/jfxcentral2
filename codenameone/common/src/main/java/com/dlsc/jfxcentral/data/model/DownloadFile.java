package com.dlsc.jfxcentral.data.model;

// Top level rather than nested in Download: the Codename One mapper generator
// names a nested type by its binary name (Outer$Inner), which does not compile.
public class DownloadFile {
    private String name;
    private String url;
    private String fileName;
    private FileType fileType;

    public DownloadFile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
