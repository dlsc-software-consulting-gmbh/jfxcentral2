package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.model.filter.ShowcaseDomain;
import com.dlsc.jfxcentral2.model.filter.ShowcaseType;

import java.util.Comparator;

public class ShowcaseFilterView extends SearchFilterView<RealWorldApp> {

    public ShowcaseFilterView() {
        getStyleClass().add("showcases-filter-view");
        setComparator(Comparator.comparing(RealWorldApp::getName));

        getFilterItems().setAll(
                new FilterItem<>("TYPE", ShowcaseType.class, ShowcaseType.TOOLS),
                new FilterItem<>("DOMAIN", ShowcaseDomain.class, ShowcaseDomain.ALL)
        );

//        setOnApplyFilters(() -> System.out.println("selectedFiltersProperty: " + getSelectedFilters()));
    }
}
