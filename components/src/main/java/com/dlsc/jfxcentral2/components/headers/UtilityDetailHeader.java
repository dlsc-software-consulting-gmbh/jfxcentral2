package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Utility;
import com.dlsc.jfxcentral2.utils.PagePath;

public class UtilityDetailHeader extends SimpleDetailHeader<Utility>  {

    public UtilityDetailHeader(Utility utility) {
        super(utility);

        getStyleClass().addAll("utility-detail-header");

        imageProperty().bind(ImageManager.getInstance().utilityImageProperty(utility));

        // setWebsite(StringUtils.isNotBlank(getModel().getHomepage()) ? getModel().getHomepage() : getModel().getRepository());
        // setWebsiteButtonText(StringUtils.isNotBlank(getModel().getHomepage()) ? "WEBSITE" : "REPOSITORY");
        setShareUrl("utilities/" + utility.getId());
        setShareText("Found this utility on @JFXCentral: " + utility.getName());
        setShareTitle("Utility: " + utility.getName());
        setBackText("UTILITIES");
        setBackUrl(PagePath.UTILITIES);
    }
}
