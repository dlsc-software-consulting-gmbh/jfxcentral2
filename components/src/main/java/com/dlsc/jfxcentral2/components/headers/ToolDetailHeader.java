package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Tool;
import org.apache.commons.lang3.StringUtils;

public class ToolDetailHeader extends SimpleDetailHeader<Tool>  {

    public ToolDetailHeader(Tool tool) {
        super(tool);
        getStyleClass().add("tool-detail-header");
        setWebsite(StringUtils.isNotBlank(getModel().getHomepage()) ? getModel().getHomepage() : getModel().getRepository());
    }
}
