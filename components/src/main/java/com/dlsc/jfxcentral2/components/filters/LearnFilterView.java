package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Learn;
import org.apache.commons.lang3.StringUtils;

public class LearnFilterView extends SimpleModelObjectSearchFilterView<Learn> {

    public LearnFilterView() {
        getStyleClass().addAll("learn-filter-view","tools-filter-view");

        setSearchPromptText("Search for a lesson");

        setOnSearch(text -> tool -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(tool.getName(), text)
        );

    }
}
