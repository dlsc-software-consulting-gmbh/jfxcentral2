package com.dlsc.jfxcentral2.components.filters;

import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;

/**
 * Filters for selecting specific icons
 */
public class IkonliIconsFilter extends SimpleSearchFilterView<Ikon> {

    public IkonliIconsFilter() {
        getStyleClass().add("ikonli-icons-filter");
        setSearchPromptText("Search by name");

        setOnSearch((searchText, filterEnums) -> {
            setPredicate(icon -> StringUtils.isBlank(searchText) || StringUtils.containsIgnoreCase(icon.getDescription(), searchText));
        });
    }
}
