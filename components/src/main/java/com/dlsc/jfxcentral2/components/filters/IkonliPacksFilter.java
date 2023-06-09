package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.IconStyle;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Filters for selecting icon packs
 */
public class IkonliPacksFilter extends SimpleSearchFilterView<IkonliPack> {

    public IkonliPacksFilter() {
        getStyleClass().add("ikonli-packs-filter");
        setSearchPromptText("Search by name");

        getFilterGroups().setAll(
                new FilterGroup<>("STYLE", List.of(
                        new FilterItem<>("ALL", item -> true, true),
                        new FilterItem<>("FILLED", item -> item.getIconStyle() == IconStyle.FILLED),
                        new FilterItem<>("OUTLINED", item -> item.getIconStyle() == IconStyle.OUTLINED),
                        new FilterItem<>("MIXING", item -> item.getIconStyle() == IconStyle.MIXING)
                )));

        setOnSearch(text -> pack -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(pack.getName(), text)
                || StringUtils.containsIgnoreCase(pack.getDescription(), text));
    }
}
