package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.IkonliPack;

/**
 * Filters for selecting specific icons
 */
public class IkonliIconsFilter extends SimpleSearchFilterView<IkonliPack> {

    public IkonliIconsFilter() {
        getStyleClass().add("ikonli-icons-filter");
        setSearchPromptText("Search by name");

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search icon ------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

//        setOnApplyFilters(() -> System.out.println("selectedFiltersProperty: " + getSelectedFilters()));
    }
}
