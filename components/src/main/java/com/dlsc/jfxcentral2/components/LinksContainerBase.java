package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.QuickLink;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class LinksContainerBase extends PaneBase {

    protected final GridPane gridPane;

    public LinksContainerBase() {
        getStyleClass().add("links-container");

        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
        gridPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        getChildren().add(gridPane);

        quickLinks.addListener((Observable it) -> layoutBySize());
        layoutBySize();
    }

    private final ListProperty<QuickLink> quickLinks = new SimpleListProperty<>(this, "quickLinks", FXCollections.observableArrayList());

    public ObservableList<QuickLink> getQuickLinks() {
        return quickLinks.get();
    }

    public ListProperty<QuickLink> quickLinksProperty() {
        return quickLinks;
    }

    public void setQuickLinks(ObservableList<QuickLink> quickLinks) {
        this.quickLinks.set(quickLinks);
    }
}
