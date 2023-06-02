package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Tool;

import java.util.Comparator;

public class ToolsFilterView extends SimpleSearchFilterView<Tool> {
    public ToolsFilterView() {
        getStyleClass().add("tools-filter-view");
        setSearchPromptText("Search for a tool");
        setComparator(Comparator.comparing(Tool::getName));

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
