package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.QuickLink;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class WebsiteChangesView extends LinksContainerBase {

    public WebsiteChangesView() {
        getStyleClass().add("website-changes-view");

        setTitle("Website\nChanges");
        setDescription("Libraries, news, books, people, etc... that have recently been added or updated");
    }

    @Override
    protected void layoutBySize() {
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();
        List<QuickLink> quickLinks = getQuickLinks();
        if (titleProperty() == null || quickLinks == null || quickLinks.isEmpty()) {
            return;
        }
        System.out.println(quickLinks.size());
        TitleAndDescriptionBox header = new TitleAndDescriptionBox();
        header.getStyleClass().add("header");
        header.sizeProperty().bind(sizeProperty());
        header.titleProperty().bind(titleProperty());
        header.descriptionProperty().bind(descriptionProperty());
        System.out.println();
        if (isSmall()) {
            gridPane.add(header, 0, 0);
            for (int i = 0; i < 3; i++) {
                QuickLink quickLink = quickLinks.get(i);
                QuickLinkView linkView = new QuickLinkView(quickLink);
                linkView.sizeProperty().bind(sizeProperty());
                gridPane.add(linkView, 0, i + 1);
            }
        } else {
            gridPane.add(header, 0, 0, 1, 2);
            int index = 0;
            for (int i = 0; i < 9; i++) {
                if (i == 0 || i == 3) {
                    continue;
                }
                QuickLink quickLink = quickLinks.get(index);
                QuickLinkView linkView = new QuickLinkView(quickLink);
                linkView.sizeProperty().bind(sizeProperty());
                gridPane.add(linkView, i % 3, i / 3);
                index++;
            }
        }
    }

    private final StringProperty title = new SimpleStringProperty(this, "title");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    private final StringProperty description = new SimpleStringProperty(this, "description");

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
