package com.dlsc.jfxcentral2.components;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Orientation;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class OrientationComboBox extends ComboBox<Orientation>{

    public OrientationComboBox() {
        this(Orientation.HORIZONTAL);
    }

    public OrientationComboBox(Orientation orientation) {
        getItems().addAll(Orientation.values());
        getSelectionModel().select(orientation);
        setConverter(new StringConverter<>() {
            @Override
            public String toString(Orientation object) {
                return object.toString();
            }

            @Override
            public Orientation fromString(String string) {
                return null;
            }
        });
        this.orientation.bind(getSelectionModel().selectedItemProperty());
    }

    private final ReadOnlyObjectWrapper<Orientation> orientation = new ReadOnlyObjectWrapper<>(this, "orientation");

    public final ReadOnlyObjectProperty<Orientation> orientationProperty() {
        return orientation.getReadOnlyProperty();
    }

    public final Orientation getOrientation() {
        return orientationProperty().get();
    }
}
