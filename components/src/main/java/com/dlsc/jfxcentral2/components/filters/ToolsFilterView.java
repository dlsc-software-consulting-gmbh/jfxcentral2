package com.dlsc.jfxcentral2.components.filters;

public class ToolsFilterView extends SimpleSearchFilterView {
    public ToolsFilterView() {
        getStyleClass().add("tools-filter-view");
        setSearchPromptText("Search for a tool");

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search tools------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

        selectedFiltersProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("------------Tools Selected Filters------------");
            System.out.println("Selected Filters: " + newValue);
        });
    }
}
