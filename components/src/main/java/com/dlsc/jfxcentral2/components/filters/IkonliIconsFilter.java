package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral2.model.filter.IconPack;

/**
 * Filters for selecting specific icons
 */
public class IkonliIconsFilter extends SimpleSearchFilterView {

    public IkonliIconsFilter() {
        getStyleClass().add("ikonli-icons-filter");
        setSearchPromptText("Search by name");

        getFilterItems().setAll(
                new FilterItem<>("PACK", IconPack.class, null, true)
        );

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search icon ------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

        setOnApplyFilters(() -> System.out.println("selectedFiltersProperty: " + getSelectedFilters()));
    }
}
