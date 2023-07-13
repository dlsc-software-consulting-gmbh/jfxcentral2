package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Tip;
import org.apache.commons.lang3.StringUtils;

public class TipsAndTricksFilterView extends SimpleModelObjectSearchFilterView<Tip> {

    public TipsAndTricksFilterView() {
        getStyleClass().add("tips-filter-view");

        setSearchPromptText("Search for tips and tricks");

        setOnSearch(text -> tip -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(tip.getName(), text)
                || StringUtils.containsIgnoreCase(tip.getDescription(), text)
        );
    }
}
