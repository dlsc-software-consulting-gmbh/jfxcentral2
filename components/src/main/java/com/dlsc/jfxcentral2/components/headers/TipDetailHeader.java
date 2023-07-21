package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tip;

public class TipDetailHeader extends SimpleDetailHeader<Tip>  {

    public TipDetailHeader(Tip tip) {
        super(tip);

        getStyleClass().addAll("tip-detail-header", "dark-header");

        backgroundImageProperty().bind(ImageManager.getInstance().tipBannerImageProperty(tip));

        setShareUrl("tips/" + tip.getId());
        setShareText("Found this tip on @JFXCentral: " + tip.getName());
        setShareTitle("Tip: " + tip.getName());
        setBackText("ALL TIPS & TRICKS");
        setBackUrl("/tips");
    }
}
