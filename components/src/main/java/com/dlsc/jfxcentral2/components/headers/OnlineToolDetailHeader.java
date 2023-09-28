package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.OnlineTool;

public class OnlineToolDetailHeader extends SimpleDetailHeader<OnlineTool>  {

    public OnlineToolDetailHeader(OnlineTool onlineTool) {
        super(onlineTool);

        getStyleClass().addAll("online-tool-detail-header");

        imageProperty().bind(ImageManager.getInstance().onlineToolImageProperty(onlineTool));

        //setWebsite(StringUtils.isNotBlank(getModel().getHomepage()) ? getModel().getHomepage() : getModel().getRepository());
        //setWebsiteButtonText(StringUtils.isNotBlank(getModel().getHomepage()) ? "WEBSITE" : "REPOSITORY");
        setShareUrl("onlinetools/" + onlineTool.getId());
        setShareText("Found this Online tool on @JFXCentral: " + onlineTool.getName());
        setShareTitle("Online Tool: " + onlineTool.getName());
        setBackText("ONLINE TOOLS");
        setBackUrl("/onlinetools");
    }
}
