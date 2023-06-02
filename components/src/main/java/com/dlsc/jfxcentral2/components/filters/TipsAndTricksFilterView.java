package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Tip;

import java.util.Comparator;

public class TipsAndTricksFilterView extends SimpleSearchFilterView<Tip> {
    public TipsAndTricksFilterView() {
        getStyleClass().add("tips-filter-view");
        setSearchPromptText("Search for tips and tricks");
        setComparator(Comparator.comparing(Tip::getName));

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search Tips Tricks------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

        selectedFiltersProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("------------Tips Tricks Selected Filters------------");
            System.out.println("Selected Filters: " + newValue);
        });
    }
}
