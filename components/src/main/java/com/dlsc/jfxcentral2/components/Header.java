package com.dlsc.jfxcentral2.components;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

public class Header extends HBox {

    public Header() {
        getStyleClass().addAll("header", "animated-header");

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
        wrapper.mouseTransparentProperty().bind(Bindings.createBooleanBinding(() -> getOnIconClickAction() == null, onIconClickActionProperty()));
        getChildren().setAll(title, new Spacer(), wrapper);
    }

    public void playRemindAnimation() {
        FadeTransition ft = new FadeTransition(Duration.millis(100), this);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setCycleCount(10);
        ft.setAutoReverse(true);
        ft.setDelay(Duration.millis(500));
        ft.play();
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
