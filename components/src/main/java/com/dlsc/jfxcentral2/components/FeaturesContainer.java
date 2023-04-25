package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.Feature;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.HBox;

import java.util.List;

public class FeaturesContainer extends PaneBase {

    public FeaturesContainer(List<Feature> items) {
        this();
        setFeatures(items);
    }

    public FeaturesContainer() {
        getStyleClass().add("features-container");
        featuresProperty().addListener((ob, ov, nv) -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        getChildren().clear();
        if (getFeatures() == null || getFeatures().isEmpty()) {
            return;
        }
        int size = getFeatures().size();
        if (isSmall()) {
            PaginationControl pagination = new PaginationControl();
            pagination.setPageCount(size);
            pagination.setPageFactory(index -> {
                FeatureView featureView = new FeatureView(getFeatures().get(index));
                featureView.sizeProperty().bind(sizeProperty());
                return featureView;
            });
            getChildren().setAll(pagination);
        } else {
            HBox contentBox = new HBox();
            for (int i = 0; i < size; i++) {
                Feature item = getFeatures().get(i);
                FeatureView featureView = new FeatureView(item);
                featureView.getStyleClass().addAll("feature-view-" + i, i % 2 == 0 ? "even" : "odd");
                featureView.sizeProperty().bind(sizeProperty());
                contentBox.getChildren().add(featureView);
            }
            getChildren().setAll(contentBox);
        }
    }

    private final ObjectProperty<List<Feature>> features = new SimpleObjectProperty<>(this, "features");

    public List<Feature> getFeatures() {
        return features.get();
    }

    public ObjectProperty<List<Feature>> featuresProperty() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features.set(features);
    }

}
