package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral2.model.ikon.IkonPackModel;

public class IconDetailHeader extends SimpleDetailHeader<IkonPackModel>  {

    public IconDetailHeader(IkonPackModel app) {
        super(app);
        getStyleClass().add("icon-detail-header");
        setWebsite(getModel().getUrl());
    }
}
