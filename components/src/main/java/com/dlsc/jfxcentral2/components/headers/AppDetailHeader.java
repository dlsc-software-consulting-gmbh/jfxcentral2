package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.RealWorldApp;

public class AppDetailHeader extends SimpleDetailHeader<RealWorldApp>  {

    public AppDetailHeader(RealWorldApp app) {
        super(app);
        getStyleClass().add("app-detail-header");
        setWebsite(getModel().getUrl());
    }
}
