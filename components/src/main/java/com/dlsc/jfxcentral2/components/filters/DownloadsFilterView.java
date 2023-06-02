package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Download;

import java.util.Comparator;

public class DownloadsFilterView extends SimpleSearchFilterView<Download> {

    public DownloadsFilterView() {
        getStyleClass().add("companies-filter-view");
        setSearchPromptText("Search for a download");
        setComparator(Comparator.comparing(Download::getName));

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
