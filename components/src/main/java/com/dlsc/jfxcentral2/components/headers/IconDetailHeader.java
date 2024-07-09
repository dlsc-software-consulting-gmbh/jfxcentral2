package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.utils.PagePath;

public class IconDetailHeader extends SimpleDetailHeader<IkonliPack>  {

    public IconDetailHeader(IkonliPack pack) {
        super(pack);

        getStyleClass().add("icon-detail-header");

        setWebsite(getModel().getUrl());
        setShareUrl("icons/" + pack.getId());
        setShareText("Found this icon pack on @JFXCentral: " + pack.getName());
        setShareTitle("Icon pack: " + pack.getName());
        setBackText("ALL ICON PACKS");
        setBackUrl(PagePath.ICONS);
    }
}
