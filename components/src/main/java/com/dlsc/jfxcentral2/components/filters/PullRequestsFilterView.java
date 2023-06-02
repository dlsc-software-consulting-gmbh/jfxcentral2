package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.pull.PullRequest;
import com.dlsc.jfxcentral2.model.filter.PullRequestDate;
import com.dlsc.jfxcentral2.model.filter.PullRequestLabel;
import com.dlsc.jfxcentral2.model.filter.PullRequestStatus;

import java.util.Comparator;

public class PullRequestsFilterView extends SearchFilterView<PullRequest> {

    public PullRequestsFilterView() {
        getStyleClass().add("pull-requests-filter-view");
        setComparator(Comparator.comparing(PullRequest::getUpdatedAt));

        getFilterItems().setAll(
                new FilterItem<>("DATE", PullRequestDate.class, PullRequestDate.ALL),
                new FilterItem<>("LABEL", PullRequestLabel.class, PullRequestLabel.ALL),
                new FilterItem<>("STATUS", PullRequestStatus.class, PullRequestStatus.ALL)
        );

//        setOnApplyFilters(() -> System.out.println("selectedFiltersProperty: " + getSelectedFilters()));
    }

}
