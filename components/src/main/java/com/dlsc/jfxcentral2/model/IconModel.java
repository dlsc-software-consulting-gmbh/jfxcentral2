package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.model.filter.IconStyle;


public class IconModel extends ModelObject {
    private IconStyle iconStyle;
    private String url; // github url
    private IkonData ikonData;

    public IconModel() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public IconStyle getIconStyle() {
        return iconStyle;
    }

    public void setIconStyle(IconStyle iconStyle) {
        this.iconStyle = iconStyle;
    }

    public IkonData getIkonData() {
        return ikonData;
    }

    public void setIkonData(IkonData ikonData) {
        this.ikonData = ikonData;
    }
}
