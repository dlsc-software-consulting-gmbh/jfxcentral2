package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.RealWorldApp;

public class AppDetailHeader extends SimpleDetailHeader<RealWorldApp>  {

    public AppDetailHeader(RealWorldApp app) {
        super(app);
        getStyleClass().addAll("app-detail-header", "dark-header");
        setWebsite(getModel().getUrl());
        setDescription(app.getSummary());
        backgroundImageProperty().bind(ImageManager.getInstance().realWorldAppBannerImageProperty(app));
        setShareUrl("showcases/" + app.getId());
    }
}
