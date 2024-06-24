package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.utils.PagePath;

public class ShowcaseDetailHeader extends SimpleDetailHeader<RealWorldApp>  {

    public ShowcaseDetailHeader(RealWorldApp app) {
        super(app);

        getStyleClass().addAll("showcase-detail-header", "dark-header");
        setWebsite(getModel().getUrl());
        backgroundImageProperty().bind(ImageManager.getInstance().realWorldAppBannerImageProperty(app));

        setShareUrl("showcases/" + app.getId());
        setShareText("Found this application on @JFXCentral: " + app.getName());
        setShareTitle("Application: " + app.getName());
        setBackText("ALL SHOWCASES");
        setBackUrl(PagePath.SHOWCASES);
    }
}
