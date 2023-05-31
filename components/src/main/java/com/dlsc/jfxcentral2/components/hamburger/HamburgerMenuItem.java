package com.dlsc.jfxcentral2.components.hamburger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.kordamp.ikonli.Ikon;

public class HamburgerMenuItem {

    public HamburgerMenuItem() {
        this("");
    }

    public HamburgerMenuItem(String text) {
        this(text, null, null);
    }

    public HamburgerMenuItem(String text, Ikon ikon, String url) {
        setText(text);
        setIkon(ikon);
        setUrl(url);
    }

    private final StringProperty url = new SimpleStringProperty();

    public String getUrl() {
        return url.get();
    }

    public StringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    private final StringProperty text = new SimpleStringProperty();

    public StringProperty textProperty() {
        return text;
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    private final ObjectProperty<Ikon> ikon = new SimpleObjectProperty<>();

    public ObjectProperty<Ikon> ikonProperty() {
        return ikon;
    }

    public Ikon getIkon() {
        return ikon.get();
    }

    public void setIkon(Ikon ikon) {
        this.ikon.set(ikon);
    }

    private final ObjectProperty<EventHandler<ActionEvent>> onAction = new SimpleObjectProperty<>();

    public ObjectProperty<EventHandler<ActionEvent>> onActionProperty() {
        return onAction;
    }

    public EventHandler<ActionEvent> getOnAction() {
        return onAction.get();
    }

    public void setOnAction(EventHandler<ActionEvent> onAction) {
        this.onAction.set(onAction);
    }
}