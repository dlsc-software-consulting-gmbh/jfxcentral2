package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.pull.PullRequest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class PullRequestsFilterView extends SearchFilterView<PullRequest> {

    public PullRequestsFilterView() {
        getStyleClass().add("pull-requests-filter-view");

        getFilterGroups().setAll(
                new FilterGroup<>("DATE", List.of(
                        new FilterItem<>("ALL", item -> true),
                        new FilterItem<>("Less than a week ago", item -> dateIsAfter(item, LocalDate.now().minusWeeks(1))),
                        new FilterItem<>("Less than a month ago", item -> dateIsAfter(item, LocalDate.now().minusMonths(1))),
                        new FilterItem<>("Less than 3 months ago", item -> dateIsAfter(item, LocalDate.now().minusMonths(3))),
                        new FilterItem<>("Less than 6 months ago", item -> dateIsAfter(item, LocalDate.now().minusMonths(6))),
                        new FilterItem<>("Less than a year ago", item -> dateIsAfter(item, LocalDate.now().minusYears(1)))
                )),
                new FilterGroup<>("LABEL", List.of(
                        new FilterItem<>("ALL", item -> true),
                        new FilterItem<>("Request for Review", item -> item.getLabels().stream().anyMatch(label -> "rfr".equalsIgnoreCase(label.getName()))),
                        new FilterItem<>("Integrated", item -> item.getLabels().stream().anyMatch(label -> "integrated".equalsIgnoreCase(label.getName())),true),
                        new FilterItem<>("Ready", item -> item.getLabels().stream().anyMatch(label -> "ready".equalsIgnoreCase(label.getName()))),
                        new FilterItem<>("Change Specification Request", item -> item.getLabels().stream().anyMatch(label -> "csr".equalsIgnoreCase(label.getName()))),
                        new FilterItem<>("Oracle Contributor Agreement", item -> item.getLabels().stream().anyMatch(label -> "oca".equalsIgnoreCase(label.getName()))),
                        new FilterItem<>("OCA Verify", item -> item.getLabels().stream().anyMatch(label -> "oca-verify".equalsIgnoreCase(label.getName())))
                        )),
                new FilterGroup<>("STATUS", List.of(
                        new FilterItem<>("ALL", item -> true),
                        new FilterItem<>("Open", item -> "open".equalsIgnoreCase(item.getState())),
                        new FilterItem<>("Closed", item -> "closed".equalsIgnoreCase(item.getState())
                        ))
                ));

    }

    private boolean dateIsAfter(PullRequest item, LocalDate date) {
        return Instant.parse(item.getUpdatedAt()).atZone(ZoneId.systemDefault()).toLocalDate().isAfter(date);
    }

}
