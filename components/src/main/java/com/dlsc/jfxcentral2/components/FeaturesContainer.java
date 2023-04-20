package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.Feature;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

import java.util.List;

public class FeaturesContainer extends PaneBase {

    public FeaturesContainer(List<Feature> items) {
        this();
        getItems().setAll(items);
    }

    public FeaturesContainer() {
        getStyleClass().add("features-container");
        itemsProperty().addListener((ob, ov, nv) -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        int size = getItems().size();
        if (isSmall()) {
            PaginationControl pagination = new PaginationControl();
            pagination.setPageCount(size);
            pagination.setPageFactory(index -> {
                FeatureView featureView = new FeatureView(getItems().get(index));
                featureView.sizeProperty().bind(sizeProperty());
                return featureView;
            });
            getChildren().setAll(pagination);
        } else {
            HBox contentBox = new HBox();
            for (int i = 0; i < size; i++) {
                Feature item = getItems().get(i);
                FeatureView featureView = new FeatureView(item);
                featureView.getStyleClass().addAll("feature-view-" + i, i % 2 == 0 ? "even" : "odd");
                featureView.sizeProperty().bind(sizeProperty());
                contentBox.getChildren().add(featureView);
            }
            getChildren().setAll(contentBox);
        }
    }

    private final ListProperty<Feature> items = new SimpleListProperty<>(this, "items", FXCollections.observableArrayList());

    public ObservableList<Feature> getItems() {
        return items.get();
    }

    public ListProperty<Feature> itemsProperty() {
        return items;
    }

    public void setItems(ObservableList<Feature> items) {
        this.items.set(items);
    }
}
