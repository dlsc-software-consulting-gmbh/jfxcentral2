package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Tool;

public class ToolOverviewBox extends SimpleOverviewBox<Tool> {

    public ToolOverviewBox(Tool tool) {
        super(tool);
        getStyleClass().add("tool-overview-box");
        setBaseURL(DataRepository2.getInstance().getRepositoryDirectoryURL() + "tools/" + getModel().getId());
        setMarkdown(DataRepository2.getInstance().getToolReadMe(tool));
    }
}
