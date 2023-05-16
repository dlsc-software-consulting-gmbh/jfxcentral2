package com.dlsc.jfxcentral2.components.filters;

public class LibrariesFilterView extends SimpleSearchFilterView {

    public LibrariesFilterView() {
        getStyleClass().add("libraries-filter-view");
        setSearchPromptText("Search for libraries");

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search Libraries------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

        selectedFiltersProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("------------Libraries Selected Filters------------");
            System.out.println("Selected Filters: " + newValue);
        });
    }
}
