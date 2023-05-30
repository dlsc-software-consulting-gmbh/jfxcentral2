package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral2.model.filter.ShowcaseDomain;
import com.dlsc.jfxcentral2.model.filter.ShowcaseType;

public class ShowcaseFilterView extends SearchFilterView {

    public ShowcaseFilterView() {
        getStyleClass().add("showcases-filter-view");

        getFilterItems().setAll(
                new FilterItem<>("TYPE", ShowcaseType.class, ShowcaseType.TOOLS),
                new FilterItem<>("DOMAIN", ShowcaseDomain.class, ShowcaseDomain.ALL)
        );

//        setOnApplyFilters(() -> System.out.println("selectedFiltersProperty: " + getSelectedFilters()));
    }
}
