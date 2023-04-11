package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.Size;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class SizeComboBox extends ComboBox<Size> {
    public SizeComboBox() {
        getItems().addAll(Size.values());
        getSelectionModel().select(Size.LARGE);
        setConverter(new StringConverter<>() {
            @Override
            public String toString(Size object) {
                return object.toString();
            }

            @Override
            public Size fromString(String string) {
                return null;
            }
        });
        size.bind(getSelectionModel().selectedItemProperty());
    }

    private final ReadOnlyObjectWrapper<Size> size = new ReadOnlyObjectWrapper<>(this, "size");

    public final ReadOnlyObjectProperty<Size> sizeProperty() {
        return size.getReadOnlyProperty();
    }

    public final Size getSize() {
        return sizeProperty().get();
    }

}
