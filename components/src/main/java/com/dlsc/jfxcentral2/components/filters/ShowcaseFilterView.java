package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ShowcaseFilterView extends SimpleSearchFilterView<RealWorldApp> {

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

        setOnSearch(text -> app -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(app.getName(), text)
                || StringUtils.containsIgnoreCase(app.getDescription(), text));

    }
}
