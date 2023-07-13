package com.dlsc.jfxcentral2.components.filters;

import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;

import java.util.Comparator;
import java.util.List;

/**
 * Filters for selecting specific icons
 */
public class IkonliIconsFilter extends SimpleSearchFilterView<Ikon> {

    public IkonliIconsFilter() {
        getStyleClass().add("ikonli-icons-filter");
        setSearchPromptText("Search by name");

        setSortGroup(new SortGroup<>("ORDER", List.of(
                new SortItem<>("From A to Z", Comparator.comparing((Ikon ikon) -> ikon.getDescription().toLowerCase())),
                new SortItem<>("From Z to A", Comparator.comparing((Ikon ikon) -> ikon.getDescription().toLowerCase()).reversed()))
        ));

        setOnSearch(text -> icon -> StringUtils.isBlank(text) || StringUtils.containsIgnoreCase(icon.getDescription(), text));
    }
}
