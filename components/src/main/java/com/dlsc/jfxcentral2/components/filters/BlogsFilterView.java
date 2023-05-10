package com.dlsc.jfxcentral2.components.filters;

public class BlogsFilterView extends SimpleSearchFilterView {
    public BlogsFilterView() {
        getStyleClass().add("blogs-filter-view");
        setSearchPromptText("Search for a blog");

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search blogs ------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

        selectedFiltersProperty().addListener((ob, ov, nv) -> {
            System.out.println("------------Blogs Selected filters ------------");
            System.out.println("Selected filters: " + nv);
        });
    }
}
