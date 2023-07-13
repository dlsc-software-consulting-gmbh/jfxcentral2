package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.ModelObject;

import java.util.Comparator;
import java.util.List;

public class SimpleModelObjectSearchFilterView<T extends ModelObject> extends SimpleSearchFilterView<T> {

    public final SortGroup<T> MODEL_DEFAULT_SORT_GROUP = new SortGroup<>("ORDER",
            List.of(
                    new SortItem<>("From A to Z", Comparator.comparing((T modelObject) -> modelObject.getName().toLowerCase())),
                    new SortItem<>("From Z to A", Comparator.comparing((T modelObject) -> modelObject.getName().toLowerCase()).reversed())
            ));

    public SimpleModelObjectSearchFilterView() {
        getStyleClass().add("simple-model-object-filter-view");

        setSortGroup(MODEL_DEFAULT_SORT_GROUP);
    }
}
