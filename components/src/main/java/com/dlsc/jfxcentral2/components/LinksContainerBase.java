package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.QuickLink;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.util.List;

public abstract class LinksContainerBase extends PaneBase {
    protected GridPane gridPane = new GridPane();

    public LinksContainerBase() {
        getStyleClass().add("links-container");

        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        gridPane.getStyleClass().add("grid-pane");
        gridPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        getChildren().setAll(gridPane);

        NodeUtil.initSizeAndTargetPseudoClass(this, null, this::layoutBySize);
    }

    protected abstract void layoutBySize();

    private final ObjectProperty<List<QuickLink>> quickLinks = new SimpleObjectProperty<>(this, "quickLinks");

    public List<QuickLink> getQuickLinks() {
        return quickLinks.get();
    }

    public ObjectProperty<List<QuickLink>> quickLinksProperty() {
        return quickLinks;
    }

    public void setQuickLinks(List<QuickLink> quickLinks) {
        this.quickLinks.set(quickLinks);
    }
}
