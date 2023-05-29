package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.jfxcentral2.components.PaneBase;

public class DetailView<T> extends PaneBase {
    private final T item;

    public DetailView(T item) {
        getStyleClass().add("detail-view");
        this.item = item;
    }

    public T getData() {
        return item;
    }
}
