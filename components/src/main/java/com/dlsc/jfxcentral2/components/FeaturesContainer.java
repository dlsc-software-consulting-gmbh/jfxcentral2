package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.Feature;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.EnumConverter;
import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeaturesContainer extends PaneBase {
    private static final PseudoClass VERTICAL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("vertical");

    private static final PseudoClass HORIZONTAL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("horizontal");

    private static final Orientation DEFAULT_ORIENTATION = Orientation.HORIZONTAL;

    public FeaturesContainer() {
        this(DEFAULT_ORIENTATION);
    }

    public FeaturesContainer(Orientation orientation) {
        getStyleClass().add("features-container");
        setOrientation(orientation);
        activateOrientationPseudoClass();
        orientationProperty().addListener((ob, ov, nv) -> {
            activateOrientationPseudoClass();
            layoutBySize();
        });
        featuresProperty().addListener((ob, ov, nv) -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        getChildren().clear();
        if (getFeatures() == null || getFeatures().isEmpty()) {
            return;
        }
        int size = getFeatures().size();
        boolean isVer = getOrientation() == Orientation.VERTICAL;
        if (isVer || !isSmall()) {
            Pane contentBox = isVer ? new VBox() : new HBox();
            for (int i = 0; i < size; i++) {
                Feature item = getFeatures().get(i);
                FeatureView featureView = new FeatureView(item);
                featureView.getStyleClass().addAll("feature-view-" + i, i % 2 == 0 ? "even" : "odd");
                featureView.sizeProperty().bind(sizeProperty());
                contentBox.getChildren().add(featureView);
            }
            getChildren().setAll(contentBox);
        } else {
            PaginationControl pagination = new PaginationControl();
            pagination.setPageCount(size);
            pagination.setPageFactory(index -> {
                FeatureView featureView = new FeatureView(getFeatures().get(index));
                featureView.sizeProperty().bind(sizeProperty());
                return featureView;
            });
            getChildren().setAll(pagination);
        }
    }

    private void activateOrientationPseudoClass() {
        Orientation tempOrientation = getOrientation();
        pseudoClassStateChanged(HORIZONTAL_PSEUDOCLASS_STATE, tempOrientation == Orientation.HORIZONTAL);
        pseudoClassStateChanged(VERTICAL_PSEUDOCLASS_STATE, tempOrientation == Orientation.VERTICAL);
    }

    private final ListProperty<Feature> features = new SimpleListProperty<>(this, "features", FXCollections.observableArrayList());

    public ObservableList<Feature> getFeatures() {
        return features.get();
    }

    public ListProperty<Feature> featuresProperty() {
        return features;
    }

    public void setFeatures(ObservableList<Feature> features) {
        this.features.set(features);
    }

    private final ObjectProperty<Orientation> orientation = new StyleableObjectProperty<>(DEFAULT_ORIENTATION) {

        @Override
        public Object getBean() {
            return FeaturesContainer.this;
        }

        @Override
        public String getName() {
            return "orientation";
        }

        @Override
        public CssMetaData<FeaturesContainer, Orientation> getCssMetaData() {
            return FeaturesContainer.StyleableProperties.ORIENTATION;
        }
    };

    public final void setOrientation(Orientation value) {
        orientation.set(value);
    }

    public final Orientation getOrientation() {
        return orientation.get();
    }

    public final ObjectProperty<Orientation> orientationProperty() {
        return orientation;
    }

    private static class StyleableProperties {

        private static final CssMetaData<FeaturesContainer, Orientation> ORIENTATION = new CssMetaData<>("-fx-orientation", new EnumConverter<>(Orientation.class), DEFAULT_ORIENTATION) {

            @Override
            public boolean isSettable(FeaturesContainer n) {
                return !n.orientation.isBound();
            }

            @Override
            public StyleableProperty<Orientation> getStyleableProperty(FeaturesContainer n) {
                return (StyleableProperty<Orientation>) n.orientationProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(PaneBase.getClassCssMetaData());
            styleables.add(ORIENTATION);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return FeaturesContainer.StyleableProperties.STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }
}
