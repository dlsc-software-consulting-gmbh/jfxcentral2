package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral2.model.filter.IconPack;

public class IkonliPackFilter extends SimpleSearchFilterView {

    public IkonliPackFilter() {
        getStyleClass().add("ikonli-browser-filter-view");
        setSearchPromptText("Search by name");

        getFilterItems().setAll(
                new FilterItem<>("PACK", IconPack.class, IconPack.ALL)
        );

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search icon ------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

        setOnApplyFilters(() -> System.out.println("selectedFiltersProperty: " + getSelectedFilters()));
    }
}
