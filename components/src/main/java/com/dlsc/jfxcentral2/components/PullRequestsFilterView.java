package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.PullRequestDate;
import com.dlsc.jfxcentral2.model.PullRequestLabel;
import com.dlsc.jfxcentral2.model.PullRequestStatus;

public class PullRequestsFilterView extends SearchFilterView {

    public PullRequestsFilterView() {
        getStyleClass().add("pull-requests-filter-view");

        getFilterItems().setAll(
                new FilterItem<>("DATE", PullRequestDate.class, PullRequestDate.ALL),
                new FilterItem<>("LABEL", PullRequestLabel.class, PullRequestLabel.ALL),
                new FilterItem<>("Status", PullRequestStatus.class, PullRequestStatus.ALL)
        );

        setOnApplyFilters(() -> System.out.println("selectedFiltersProperty: " + getSelectedFilters()));
    }

}
