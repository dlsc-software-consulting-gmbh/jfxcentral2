package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

public class LinksOfTheWeekView extends PaneBase {

    private final PaginationControl2 pagination;
    private final VBox linksBox;

    public LinksOfTheWeekView() {
        getStyleClass().add("links-of-the-week-view");
        linksBox = new VBox();
        linksBox.getStyleClass().add("links-box");

        pagination = new PaginationControl2();
        pagination.getStyleClass().add("pagination");

        VBox contentBox = new VBox(linksBox, pagination);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);

        createFactory();
        pagination.setPrefHeight(Region.USE_PREF_SIZE);
        linksOfTheWeeksProperty().addListener((ob, ov, nv) -> createFactory());
        weekCountProperty().addListener((ob, ov, nv) -> createFactory());
        sizeProperty().addListener((ob, ov, nv) -> createFactory());

    }

    private void createFactory() {
        pagination.setMaxPageIndicatorCount(isSmall() ? 2 : 3);
        pagination.setPageCount((int) Math.ceil((double) getLinksOfTheWeeks().size() / getWeekCount()));
        pagination.setPageFactory((pageIndex) -> {
            linksBox.getChildren().clear();
            int fromIndex = pageIndex * getWeekCount();
            int toIndex = Math.min(fromIndex + getWeekCount(), getLinksOfTheWeeks().size());
            List<LinksOfTheWeek> links = getLinksOfTheWeeks().subList(fromIndex, toIndex);
            links.forEach(week -> {
                Label title = new Label(week.getName());
                title.getStyleClass().add("title");
                title.setMaxWidth(Double.MAX_VALUE);

                MarkdownView markdownView = new MarkdownView();
                markdownView.setMdString(week.getDescription());
                markdownView.heightProperty().addListener((ob, ov, nv) -> {
                    System.out.println("MarkdownView height: " + nv);
                });
                markdownView.setPrefHeight(markdownView.getWidth());

                VBox weekBox = new VBox(title, markdownView);
                weekBox.getStyleClass().add("week-box");
                linksBox.getChildren().add(weekBox);
            });
            return null;
        });
        pagination.setCurrentPageIndex(0);

    }

    private final IntegerProperty weekCount = new SimpleIntegerProperty(this, "weekCount", 1);

    public int getWeekCount() {
        return weekCount.get();
    }

    public IntegerProperty weekCountProperty() {
        return weekCount;
    }

    public void setWeekCount(int weekCount) {
        this.weekCount.set(weekCount);
    }

    private final ListProperty<LinksOfTheWeek> linksOfTheWeeks = new SimpleListProperty<>(this, "linksOfTheWeeks", FXCollections.observableArrayList());

    public ObservableList<LinksOfTheWeek> getLinksOfTheWeeks() {
        return linksOfTheWeeks.get();
    }

    public ListProperty<LinksOfTheWeek> linksOfTheWeeksProperty() {
        return linksOfTheWeeks;
    }

    public void setLinksOfTheWeeks(ObservableList<LinksOfTheWeek> linksOfTheWeeks) {
        this.linksOfTheWeeks.set(linksOfTheWeeks);
    }

}