package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.OnlineTool;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;

public class OnlineToolsFilterView extends SimpleModelObjectSearchFilterView<OnlineTool> {

    public OnlineToolsFilterView() {
        getStyleClass().addAll("online-tools-filter-view", "tools-filter-view");

        setSearchPromptText("Search online tools");

        setSortGroup(new SortGroup<>("ORDER",
                List.of(
                        new SortItem<>("Status", Comparator.comparing((OnlineTool modelObject) -> modelObject.getStatus().ordinal())),
                        new SortItem<>("From A to Z", Comparator.comparing((OnlineTool modelObject) -> modelObject.getName().toLowerCase())),
                        new SortItem<>("From Z to A", Comparator.comparing((OnlineTool modelObject) -> modelObject.getName().toLowerCase()).reversed())
                )
        ));

        setOnSearch(text -> tool -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(tool.getName(), text)
                || StringUtils.containsIgnoreCase(tool.getDescription(), text)
        );
    }
}
