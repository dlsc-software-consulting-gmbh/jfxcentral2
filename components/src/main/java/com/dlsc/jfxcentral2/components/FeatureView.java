package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.FeatureViewSkin;
import com.dlsc.jfxcentral2.model.Feature;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Skin;
import one.jpro.platform.routing.LinkUtil;

public class FeatureView extends ControlBase {

    public FeatureView() {
        this(null);
    }

    public FeatureView(Feature feature) {
        getStyleClass().add("feature-view");
        setFeature(feature);
        LinkUtil.setLink(this, feature.url());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new FeatureViewSkin(this);
    }

    private final ObjectProperty<Feature> feature = new SimpleObjectProperty<>(this, "feature");

    public Feature getFeature() {
        return feature.get();
    }

    public ObjectProperty<Feature> featureProperty() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature.set(feature);
    }
}
