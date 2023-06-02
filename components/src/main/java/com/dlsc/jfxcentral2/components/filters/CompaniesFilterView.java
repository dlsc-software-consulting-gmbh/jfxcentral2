package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Company;

import java.util.Comparator;

public class CompaniesFilterView extends SimpleSearchFilterView<Company> {

    public CompaniesFilterView() {
        getStyleClass().add("companies-filter-view");
        setSearchPromptText("Search for a company");
        setComparator(Comparator.comparing(Company::getName));
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
