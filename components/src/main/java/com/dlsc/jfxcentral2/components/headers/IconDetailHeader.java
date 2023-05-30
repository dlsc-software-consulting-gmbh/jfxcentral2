package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.IkonliPack;

public class IconDetailHeader extends SimpleDetailHeader<IkonliPack>  {

    public IconDetailHeader(IkonliPack pack) {
        super(pack);
        getStyleClass().add("icon-detail-header");
        setWebsite(getModel().getUrl());
    }
}
