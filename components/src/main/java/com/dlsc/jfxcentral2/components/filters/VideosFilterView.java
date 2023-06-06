package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Video;

import java.time.LocalDate;
import java.util.List;

public class VideosFilterView extends SearchFilterView<Video> {

    public VideosFilterView() {
        getStyleClass().add("videos-filter-view");
        setOnSearch(null);

        getFilterGroups().setAll(
                new FilterGroup<>("LENGTH", List.of(
                        new FilterItem<>("ALL", item -> true, true),
                        new FilterItem<>("Less than 5 mins", item -> item.getMinutes() < 5),
                        new FilterItem<>("5-15 min", item -> item.getMinutes() >= 5 && item.getMinutes() < 15),
                        new FilterItem<>("15-60 min", item -> item.getMinutes() >= 15 && item.getMinutes() < 60),
                        new FilterItem<>("More than 60 mins", item -> item.getMinutes() >= 60))),
                new FilterGroup<>("DATE", List.of(
                        new FilterItem<>("ALL", item -> true, true),
                        new FilterItem<>("Less than a week ago", item -> item.getPublishedOn().isAfter(LocalDate.now().minusDays(7))),
                        new FilterItem<>("Less than a month ago", item -> item.getPublishedOn().isAfter(LocalDate.now().minusMonths(1))),
                        new FilterItem<>("Less than 3 month ago", item -> item.getPublishedOn().isAfter(LocalDate.now().minusMonths(3))),
                        new FilterItem<>("Less than 6 month ago", item -> item.getPublishedOn().isAfter(LocalDate.now().minusMonths(3))),
                        new FilterItem<>("Less than a year ago", item -> item.getPublishedOn().isAfter(LocalDate.now().minusYears(1))))),
                new FilterGroup<>("TYPE", List.of(
                        new FilterItem<>("ALL", item -> true, true),
                        new FilterItem<>("Interview", item -> true),
                        new FilterItem<>("Tutorial", item -> true),
                        new FilterItem<>("Discussion", item -> true),
                        new FilterItem<>("Conference", item -> true),
                        new FilterItem<>("Keynote", item -> true)))
        );


    }

}
