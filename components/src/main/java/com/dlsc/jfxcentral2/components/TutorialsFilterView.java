package com.dlsc.jfxcentral2.components;

public class TutorialsFilterView extends SimpleSearchFilterView {
    public TutorialsFilterView() {
        getStyleClass().add("tutorials-filter-view");
        setSearchPromptText("Search for a tutorial");

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search tutorials------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

        selectedFiltersProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("------------Tutorials Selected Filters------------");
            System.out.println("Selected Filters: " + newValue);
        });
    }
}
