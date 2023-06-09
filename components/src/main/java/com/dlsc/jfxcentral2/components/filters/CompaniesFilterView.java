package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Company;
import org.apache.commons.lang3.StringUtils;

public class CompaniesFilterView extends SimpleSearchFilterView<Company> {

    public CompaniesFilterView() {
        getStyleClass().add("companies-filter-view");
        setSearchPromptText("Search for a company");

        setOnSearch(text -> company -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(company.getName(), text)
                || StringUtils.containsIgnoreCase(company.getDescription(), text));
    }
}
