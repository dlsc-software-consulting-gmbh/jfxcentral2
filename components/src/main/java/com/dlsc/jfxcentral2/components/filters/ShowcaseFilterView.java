package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ShowcaseFilterView extends SimpleSearchFilterView<RealWorldApp> {
    private static List<FilterItem<RealWorldApp>> domainFilterItems;

    public ShowcaseFilterView() {
        getStyleClass().add("showcases-filter-view");
        setSearchPromptText("Search for a JFX APP");

        if (domainFilterItems == null) {
            domainFilterItems = getDomainFilterItems();
        }

        getFilterGroups().setAll(
                //new FilterGroup<>("TYPE", List.of(
                //        new FilterItem<>("ALL", item -> true, true),
                //        new FilterItem<>("Tools", item -> true),
                //        new FilterItem<>("Business", item -> true),
                //        new FilterItem<>("Other", item -> true)
                //)),
                new FilterGroup<>("DOMAIN", domainFilterItems)
        );

        setOnSearch(text -> app -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(app.getName(), text)
                || StringUtils.containsIgnoreCase(app.getDescription(), text));

    }

    private List<FilterItem<RealWorldApp>> getDomainFilterItems() {
        DataRepository.getInstance().loadData();
        ObservableList<RealWorldApp> appList = DataRepository.getInstance().getRealWorldApps();

        ArrayList<FilterItem<RealWorldApp>> filterItems = new ArrayList<>(appList.stream()
                .flatMap(app -> Optional.ofNullable(app.getDomain()).stream()
                        .flatMap(domain -> Arrays.stream(domain.split(","))))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .sorted()
                .distinct()
                .map(item -> new FilterItem<RealWorldApp>(item, it -> StringUtils.containsIgnoreCase(it.getDomain(), item)))
                .toList());
        filterItems.add(0, new FilterItem<>("ALL", item -> true, true));

        return filterItems;
    }
}
