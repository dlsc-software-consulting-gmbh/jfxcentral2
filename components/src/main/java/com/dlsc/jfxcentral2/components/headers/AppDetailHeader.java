package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import javafx.beans.binding.Bindings;
import javafx.scene.image.Image;

public class AppDetailHeader extends SimpleDetailHeader<RealWorldApp>  {

    public AppDetailHeader(RealWorldApp app) {
        super(app);
        getStyleClass().addAll("app-detail-header", "dark-header");
        setWebsite(getModel().getUrl());
        backgroundImageProperty().bind(Bindings.createObjectBinding(() -> {
            Image banner = ImageManager.getInstance().realWorldAppBannerImageProperty(app).get();
            if (banner == null) {
                banner = ImageManager.getInstance().realWorldAppLargeImageProperty(app).get();
            }
            return banner;
        }, ImageManager.getInstance().realWorldAppBannerImageProperty(app), ImageManager.getInstance().realWorldAppLargeImageProperty(app)));
    }
}
