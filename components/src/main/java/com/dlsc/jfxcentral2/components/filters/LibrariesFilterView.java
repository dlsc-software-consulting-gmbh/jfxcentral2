package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Library;

import java.util.Comparator;

public class LibrariesFilterView extends SimpleSearchFilterView<Library> {

    public LibrariesFilterView() {
        getStyleClass().add("libraries-filter-view");
        setSearchPromptText("Search for libraries");
        setComparator(Comparator.comparing(Library::getName));

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
