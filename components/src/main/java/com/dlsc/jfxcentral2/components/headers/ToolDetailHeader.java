package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.utils.PagePath;
import org.apache.commons.lang3.StringUtils;

public class ToolDetailHeader extends SimpleDetailHeader<Tool>  {

    public ToolDetailHeader(Tool tool) {
        super(tool);

        getStyleClass().add("tool-detail-header");

        imageProperty().bind(ImageManager.getInstance().toolImageProperty(tool));

        setWebsite(StringUtils.isNotBlank(getModel().getHomepage()) ? getModel().getHomepage() : getModel().getRepository());
        setWebsiteButtonText(StringUtils.isNotBlank(getModel().getHomepage()) ? "WEBSITE" : "REPOSITORY");
        setShareUrl("tools/" + tool.getId());
        setShareText("Found this tool on @JFXCentral: " + tool.getName());
        setShareTitle("Tool: " + tool.getName());
        setBackText("ALL TOOLS");
        setBackUrl(PagePath.TOOLS);
    }
}
