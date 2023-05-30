package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral2.model.filter.FilterOrder;

public class SimpleSearchFilterView<T> extends SearchFilterView<T> {

    public SimpleSearchFilterView() {
        getStyleClass().add("simple-filter-view");

        getFilterItems().setAll(
                new FilterItem<>("ORDER", FilterOrder.class, FilterOrder.ASCENDING)
        );
    }
}
