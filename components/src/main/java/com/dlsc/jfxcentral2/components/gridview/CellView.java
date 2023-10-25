package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.jfxcentral2.components.PaneBase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.input.MouseButton;

import java.util.Objects;

public class CellView<T> extends PaneBase {

    private final T item;
    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

    public CellView(T item) {
        getStyleClass().add("cell-view");

        this.item = Objects.requireNonNull(item, "item can not be null");

        activeSelectedPseudoClass();

        selectedProperty().addListener(it -> activeSelectedPseudoClass());

        setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.isStillSincePress()) {
                setSelected(!isSelected());
            }
        });
    }

    private void activeSelectedPseudoClass() {
        pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected());
    }

    public T getData() {
        return item;
    }

    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}
