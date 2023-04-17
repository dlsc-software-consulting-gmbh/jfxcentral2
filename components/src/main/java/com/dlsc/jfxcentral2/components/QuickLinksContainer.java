package com.dlsc.jfxcentral2.components;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class QuickLinksContainer extends PaneBase {

    private final GridPane gridPane;

    public QuickLinksContainer() {
        getStyleClass().add("quick-links-container");

        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
        getChildren().add(gridPane);

        quickLinksProperty().addListener((observable, oldValue, newValue) -> layoutBySize());
        layoutBySize();
    }

    @Override
    protected void layoutBySize() {
        gridPane.getChildren().clear();
        ObservableList<QuickLinkView.QuickLink> links = getQuickLinks();

        int col = getSize().isSmall() ? 2 : 3;
        for (int i = 0; i < links.size(); i++) {
            QuickLinkView.QuickLink quickLink = links.get(i);
            if (quickLink != null) {
                QuickLinkView quickLinkView = new QuickLinkView(quickLink);
                quickLinkView.sizeProperty().bind(sizeProperty());
                gridPane.add(quickLinkView, i % col, i / col);
            }
        }
    }

    private final ListProperty<QuickLinkView.QuickLink> quickLinks = new SimpleListProperty<>(this, "quickLinks", FXCollections.observableArrayList());

    public ObservableList<QuickLinkView.QuickLink> getQuickLinks() {
        return quickLinks.get();
    }

    public ListProperty<QuickLinkView.QuickLink> quickLinksProperty() {
        return quickLinks;
    }

    public void setQuickLinks(ObservableList<QuickLinkView.QuickLink> quickLinks) {
        this.quickLinks.set(quickLinks);
    }
}
