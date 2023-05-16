package com.dlsc.jfxcentral2.components.filters;

public class DownloadsFilterView extends SimpleSearchFilterView {

    public DownloadsFilterView() {
        getStyleClass().add("companies-filter-view");
        setSearchPromptText("Search for a download");

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search downloads ------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

        selectedFiltersProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("------------Downloads Selected Filters------------");
            System.out.println("Selected Filters: " + newValue);
        });
    }
}
