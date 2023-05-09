package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.FilterOrder;

public class SimpleSearchFilterView extends SearchFilterView {
    public SimpleSearchFilterView() {
        getStyleClass().add("simple-filter-view");

        getFilterItems().setAll(
                new FilterItem<>("ORDER", FilterOrder.class, FilterOrder.ASCENDING)
        );
    }
}