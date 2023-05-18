package com.dlsc.jfxcentral2.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

        getChildren().setAll(title, new Spacer(), icon);
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

}
