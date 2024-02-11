package com.dlsc.jfxcentral2.model;

import org.kordamp.ikonli.Ikon;

public class IconInfo {
    private Ikon ikon;
    private String ikonliPackName;
    private String ikonliPackId;
    private String iconLiteral;
    private String cssCode;
    private String javaCode;
    private String unicode;
    private String mavenInfo;
    private String gradleInfo;
    private String path;

    public IconInfo() {
    }

    public Ikon getIkon() {
        return ikon;
    }

    public void setIkon(Ikon ikon) {
        this.ikon = ikon;
    }

    public String getIkonliPackName() {
        return ikonliPackName;
    }

    public void setIkonliPackName(String ikonliPackName) {
        this.ikonliPackName = ikonliPackName;
    }

    public String getIkonliPackId() {
        return ikonliPackId;
    }

    public void setIkonliPackId(String ikonliPackId) {
        this.ikonliPackId = ikonliPackId;
    }

    public String getIconLiteral() {
        return iconLiteral;
    }

    public void setIconLiteral(String iconLiteral) {
        this.iconLiteral = iconLiteral;
    }

    public String getCssCode() {
        return cssCode;
    }

    public void setCssCode(String cssCode) {
        this.cssCode = cssCode;
    }

    public String getJavaCode() {
        return javaCode;
    }

    public void setJavaCode(String javaCode) {
        this.javaCode = javaCode;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public String getMavenInfo() {
        return mavenInfo;
    }

    public void setMavenInfo(String mavenInfo) {
        this.mavenInfo = mavenInfo;
    }

    public String getGradleInfo() {
        return gradleInfo;
    }

    public void setGradleInfo(String gradleInfo) {
        this.gradleInfo = gradleInfo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
