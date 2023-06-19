package com.dlsc.jfxcentral2.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

public class Header extends HBox {

    public Header() {
        getStyleClass().add("header");

        Label title = new Label();
        title.getStyleClass().add("header-title");
        title.textProperty().bind(titleProperty());

        FontIcon icon = new FontIcon();
        icon.getStyleClass().add("header-icon");
        icon.iconCodeProperty().bind(iconProperty());

        StackPane wrapper = new StackPane(icon);
        wrapper.getStyleClass().add("header-icon-wrapper");
        wrapper.cursorProperty().bind(Bindings.createObjectBinding(()-> getOnIconClickAction() == null ? Cursor.DEFAULT : Cursor.HAND, onIconClickActionProperty()));
        wrapper.setOnMousePressed(evt -> {
            Runnable action = getOnIconClickAction();
            if (action != null) {
                action.run();
            }
            evt.consume();
        });


        getChildren().setAll(title, new Spacer(), wrapper);
    }

    private final StringProperty title = new SimpleStringProperty(this, "title", "Overview");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    private final ObjectProperty<Ikon> icon = new SimpleObjectProperty<>(this, "icon", MaterialDesignP.PLAYLIST_EDIT);

    public Ikon getIcon() {
        return icon.get();
    }

    public ObjectProperty<Ikon> iconProperty() {
        return icon;
    }

    public void setIcon(Ikon icon) {
        this.icon.set(icon);
    }

    private final ObjectProperty<Runnable> onIconClickAction = new SimpleObjectProperty<>(this, "onIconClickAction");

    public Runnable getOnIconClickAction() {
        return onIconClickAction.get();
    }

    public ObjectProperty<Runnable> onIconClickActionProperty() {
        return onIconClickAction;
    }

    public void setOnIconClickAction(Runnable onIconClickAction) {
        this.onIconClickAction.set(onIconClickAction);
    }
}
