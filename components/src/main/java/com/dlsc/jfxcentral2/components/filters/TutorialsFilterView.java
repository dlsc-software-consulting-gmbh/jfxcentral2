package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Tutorial;

import java.util.Comparator;

public class TutorialsFilterView extends SimpleSearchFilterView<Tutorial> {
    public TutorialsFilterView() {
        getStyleClass().add("tutorials-filter-view");
        setSearchPromptText("Search for a tutorial");
        setComparator(Comparator.comparing(Tutorial::getName));

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
