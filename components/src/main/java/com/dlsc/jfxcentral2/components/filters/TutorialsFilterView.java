package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Tutorial;
import org.apache.commons.lang3.StringUtils;

public class TutorialsFilterView extends SimpleModelObjectSearchFilterView<Tutorial> {

    public TutorialsFilterView() {
        getStyleClass().add("tutorials-filter-view");

        setSearchPromptText("Search for a tutorial");

        setOnSearch(text -> tutorial -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(tutorial.getName(), text)
                || StringUtils.containsIgnoreCase(tutorial.getDescription(), text)
        );
    }
}
