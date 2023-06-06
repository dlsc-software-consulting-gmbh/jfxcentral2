package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Tool;
import org.apache.commons.lang3.StringUtils;

public class ToolsFilterView extends SimpleSearchFilterView<Tool> {
    public ToolsFilterView() {
        getStyleClass().add("tools-filter-view");
        setSearchPromptText("Search for a tool");

        setOnSearch(text -> tool -> StringUtils.isBlank(text) || StringUtils.containsIgnoreCase(tool.getName(), text));

    }
}
