package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.DataRepository2;
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

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
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
        List<LinksOfTheWeek> sortedList = getSortedList();
        pagination.setPageCount((int) Math.ceil((double) sortedList.size() / getWeekCount()));
        pagination.setPageFactory((pageIndex) -> {
            linksBox.getChildren().clear();
            int fromIndex = pageIndex * getWeekCount();
            int toIndex = Math.min(fromIndex + getWeekCount(), getLinksOfTheWeeks().size());
            List<LinksOfTheWeek> links = sortedList.subList(fromIndex, toIndex);
            links.forEach(week -> {
                Label title = new Label(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(week.getCreatedOn()));
                title.getStyleClass().add("title");
                title.setMaxWidth(Double.MAX_VALUE);

                MarkdownView markdownView = new MarkdownView();
                markdownView.setMdString(DataRepository2.getInstance().getLinksOfTheWeekReadMe(week));
                markdownView.setPrefHeight(markdownView.getWidth());

                VBox weekBox = new VBox(title, markdownView);
                weekBox.getStyleClass().add("week-box");
                linksBox.getChildren().add(weekBox);
            });
            return null;
        });
        pagination.setCurrentPageIndex(0);
    }

    protected List<LinksOfTheWeek> getSortedList() {
        return DataRepository2.getInstance().getLinksOfTheWeek()
                .stream()
                .sorted(Comparator.comparing(LinksOfTheWeek::getCreatedOn).reversed())
                .toList();
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