package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.RealWorldApp;

public class AppDetailHeader extends SimpleDetailHeader<RealWorldApp>  {

    public AppDetailHeader(RealWorldApp app) {
        this();
        setModel(app);
    }

    public AppDetailHeader() {
        getStyleClass().add("app-detail-header");
        modelProperty().addListener(it -> setWebsite(getModel().getUrl()));
    }
}
