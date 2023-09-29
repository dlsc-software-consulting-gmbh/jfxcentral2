package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Utility;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;

public class UtilitiesFilterView extends SimpleModelObjectSearchFilterView<Utility> {

    public UtilitiesFilterView() {
        getStyleClass().addAll("utilities-filter-view", "tools-filter-view");

        setSearchPromptText("Search utilities");

        setSortGroup(new SortGroup<>("ORDER",
                List.of(
                        new SortItem<>("Status", Comparator.comparing((Utility modelObject) -> modelObject.getStatus().ordinal())),
                        new SortItem<>("From A to Z", Comparator.comparing((Utility modelObject) -> modelObject.getName().toLowerCase())),
                        new SortItem<>("From Z to A", Comparator.comparing((Utility modelObject) -> modelObject.getName().toLowerCase()).reversed())
                )
        ));

        setOnSearch(text -> tool -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(tool.getName(), text)
                || StringUtils.containsIgnoreCase(tool.getDescription(), text)
        );
    }
}
