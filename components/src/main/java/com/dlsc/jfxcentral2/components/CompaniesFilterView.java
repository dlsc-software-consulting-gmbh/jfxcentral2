package com.dlsc.jfxcentral2.components;

public class CompaniesFilterView extends SimpleSearchFilterView {
    public CompaniesFilterView() {
        getStyleClass().add("companies-filter-view");
        setSearchPromptText("Search for a company");

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search companies------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

        selectedFiltersProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("------------Companies Selected Filters------------");
            System.out.println("Selected Filters: " + newValue);
        });
    }
}
