package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.RealWorldApp;

import java.util.List;

public class ShowcaseFilterView extends SearchFilterView<RealWorldApp> {

    public ShowcaseFilterView() {
        getStyleClass().add("showcases-filter-view");

        getFilterGroups().setAll(
                new FilterGroup<>("TYPE", List.of(
                        new FilterItem<>("ALL", item -> true, true),
                        new FilterItem<>("Tools", item -> true),
                        new FilterItem<>("Business", item -> true),
                        new FilterItem<>("Other", item -> true)
                )),
                new FilterGroup<>("DOMAIN", List.of(
                        new FilterItem<>("ALL", item -> true, true),
                        new FilterItem<>("Finance", item -> true),
                        new FilterItem<>("Healthcare", item -> true),
                        new FilterItem<>("Education", item -> true)
                ))
        );
    }
}
