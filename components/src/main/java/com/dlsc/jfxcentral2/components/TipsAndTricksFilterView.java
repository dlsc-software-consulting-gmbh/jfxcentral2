package com.dlsc.jfxcentral2.components;

public class TipsAndTricksFilterView extends SimpleSearchFilterView {
    public TipsAndTricksFilterView() {
        getStyleClass().add("tips-filter-view");
        setSearchPromptText("Search for tips and tricks ");

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
