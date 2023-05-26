package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral2.model.filter.IconStyle;

public class IkonliIconFilter extends SimpleSearchFilterView {

    public IkonliIconFilter() {
        getStyleClass().add("ikonli-browser-filter-view");
        setSearchPromptText("Search by name");

        getFilterItems().setAll(
                new FilterItem<>("STYLE", IconStyle.class, IconStyle.ALL)
        );

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search icon ------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

        setOnApplyFilters(() -> System.out.println("selectedFiltersProperty: " + getSelectedFilters()));
    }
}
