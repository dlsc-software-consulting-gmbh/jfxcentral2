package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.utils.IkonUtil;

public class AppDetailHeader extends SimpleDetailHeader<RealWorldApp>  {

    public AppDetailHeader(RealWorldApp app) {
        this();
        setModel(app);
    }

    public AppDetailHeader() {
        getStyleClass().add("app-detail-header");

        setWebsiteButtonText("WEBSITE");
        setWebsiteButtonIcon(IkonUtil.website);
        setOnWebsite(() -> {
            if (getModel() != null) {
                System.out.println(getModel().getName() + " App website");
            }
        });
        setOnBack(() -> System.out.println(getClass().getSimpleName() + " back"));
        setOnShare(() -> System.out.println(getClass().getSimpleName() + " share"));
    }
}
