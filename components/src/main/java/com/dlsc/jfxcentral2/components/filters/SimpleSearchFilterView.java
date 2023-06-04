package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.ModelObject;

import java.util.Comparator;
import java.util.List;

public class SimpleSearchFilterView<T> extends SearchFilterView<T> {
    public static final SortGroup MODEL_DEFAULT_SORT_GROUP = new SortGroup<>("ORDER",
            List.of(
                    //new SortItem("Default", Comparator.comparing(ModelObject::getId)),
                    new SortItem("From A to Z", Comparator.comparing(ModelObject::getName)),
                    new SortItem("From Z to A", Comparator.comparing(ModelObject::getName).reversed())
            ));
    public SimpleSearchFilterView() {
        getStyleClass().add("simple-filter-view");

        setSortGroup(MODEL_DEFAULT_SORT_GROUP);
    }
}
