package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.filter.AppDOMAIN;
import com.dlsc.jfxcentral2.model.filter.AppType;

public class AppsFilterView extends SearchFilterView {

    public AppsFilterView() {
        getStyleClass().add("apps-filter-view");

        getFilterItems().setAll(
                new FilterItem<>("TYPE", AppType.class, AppType.TOOLS),
                new FilterItem<>("DOMAIN", AppDOMAIN.class, AppDOMAIN.ALL)
        );

        setOnApplyFilters(() -> System.out.println("selectedFiltersProperty: " + getSelectedFilters()));
    }
}
