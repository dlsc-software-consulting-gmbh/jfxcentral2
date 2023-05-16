package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral2.model.filter.PullRequestDate;
import com.dlsc.jfxcentral2.model.filter.PullRequestLabel;
import com.dlsc.jfxcentral2.model.filter.PullRequestStatus;

public class PullRequestsFilterView extends SearchFilterView {

    public PullRequestsFilterView() {
        getStyleClass().add("pull-requests-filter-view");

        getFilterItems().setAll(
                new FilterItem<>("DATE", PullRequestDate.class, PullRequestDate.ALL),
                new FilterItem<>("LABEL", PullRequestLabel.class, PullRequestLabel.ALL),
                new FilterItem<>("STATUS", PullRequestStatus.class, PullRequestStatus.ALL)
        );

        setOnApplyFilters(() -> System.out.println("selectedFiltersProperty: " + getSelectedFilters()));
    }

}
