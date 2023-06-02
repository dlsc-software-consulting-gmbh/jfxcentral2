package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.model.filter.IconStyle;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

/**
 * Filters for selecting icon packs
 */
public class IkonliPacksFilter extends SimpleSearchFilterView<IkonliPack> {

    public IkonliPacksFilter() {
        getStyleClass().add("ikonli-packs-filter");
        setSearchPromptText("Search by name");
        setComparator(Comparator.comparing(IkonliPack::getName));

        getFilterItems().setAll(
                new FilterItem<>("STYLE", IconStyle.class, IconStyle.ALL)
        );

        setOnSearch((text, filters) -> {
            System.out.println("updating predicate, text = " + text);
            setPredicate(pack -> StringUtils.containsIgnoreCase(pack.getName(), text));
        });
    }
}
