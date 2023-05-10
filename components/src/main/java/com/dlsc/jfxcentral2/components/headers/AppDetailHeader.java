package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral2.model.SimpleHeaderInfo;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class AppDetailHeader extends SimpleDetailHeader {

    public AppDetailHeader(SimpleHeaderInfo app) {
        this();
        setSimpleHeaderInfo(app);
    }

    public AppDetailHeader() {
        getStyleClass().add("app-detail-header");

        setWebsiteButtonText("WEBSITE");
        setWebsiteButtonIcon(MaterialDesign.MDI_WEB);
        setOnBack(() -> System.out.println(getClass().getSimpleName() + " back"));
        setOnShare(() -> System.out.println(getClass().getSimpleName() + " share"));
    }


}
