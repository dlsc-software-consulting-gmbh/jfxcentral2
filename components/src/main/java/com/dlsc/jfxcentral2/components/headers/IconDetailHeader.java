package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral2.model.IconModel;

public class IconDetailHeader extends SimpleDetailHeader<IconModel>  {

    public IconDetailHeader(IconModel app) {
        super(app);
        getStyleClass().add("icon-detail-header");
        setWebsite(getModel().getUrl());
    }
}
