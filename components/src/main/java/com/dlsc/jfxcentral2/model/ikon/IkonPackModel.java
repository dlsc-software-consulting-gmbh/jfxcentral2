package com.dlsc.jfxcentral2.model.ikon;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.model.filter.IconStyle;
import com.google.gson.annotations.SerializedName;

public class IkonPackModel extends ModelObject {
    private String module;

    private String title;

    @SerializedName("icon_style")
    private IconStyle iconStyle;

    private Installing installing;

    @SerializedName("source_url")
    private String url;

    @SerializedName("font_version")
    private String fontVersion;

    public IkonPackModel() {
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public IconStyle getIconStyle() {
        return iconStyle;
    }

    public void setIconStyle(IconStyle iconStyle) {
        this.iconStyle = iconStyle;
    }

    public Installing getInstalling() {
        return installing;
    }

    public void setInstalling(Installing installing) {
        this.installing = installing;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFontVersion() {
        return fontVersion;
    }

    public void setFontVersion(String fontVersion) {
        this.fontVersion = fontVersion;
    }
}

