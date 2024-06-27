package com.dlsc.jfxcentral2.mobile;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.components.headers.LinksOfTheWeekHeader;
import com.dlsc.jfxcentral2.mobile.componenets.PageView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2RoundAL;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;

public class LotwPageView extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "lotw-page-view";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

    private final SizeSupport sizeSupport = new SizeSupport(this);
    private final LinksContentView[] cachedContentAry = new LinksContentView[3];

    public LotwPageView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // top header
        LinksOfTheWeekHeader header = new LinksOfTheWeekHeader();
        header.sizeProperty().bind(sizeProperty());

        // center content
        List<LinksOfTheWeek> linksOfTheWeek = DataRepository2.getInstance().getLinksOfTheWeek();
        // Reverse the list to show the latest links first
        Collections.reverse(linksOfTheWeek);

        PageView pageView = new PageView();
        pageView.setPageCount(linksOfTheWeek.size());
        pageView.setPageFactory(index -> {
            LinksOfTheWeek weakLinks = linksOfTheWeek.get(index);

            LinksContentView contentView = getLinksContentView(index);
            contentView.setDate(weakLinks.getCreatedOn());
            contentView.setContent(DataRepository2.getInstance().getLinksOfTheWeekReadMe(weakLinks));
            if (index == 0) {
                contentView.setShowAll(true);
            }
            return contentView;
        });

        pageView.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            int newIndex = newValue.intValue();
            int aryIndex = newIndex % cachedContentAry.length;

            for (int i = 0; i < cachedContentAry.length; i++) {
                LinksContentView mdView = cachedContentAry[i];
                if (mdView == null) {
                    continue;
                }
                mdView.setShowAll(i == aryIndex);
            }
        });

        getChildren().addAll(header, pageView);
    }

    private LinksContentView getLinksContentView(int currentPageIndex) {
        int aryIndex = currentPageIndex % cachedContentAry.length;
        LinksContentView mdView = cachedContentAry[aryIndex];
        if (mdView == null) {
            cachedContentAry[aryIndex] = new LinksContentView();
            mdView = cachedContentAry[aryIndex];
        }
        return mdView;
    }

    // size

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    private static class LinksContentView extends VBox {

        public LinksContentView() {
            getStyleClass().add("links-content-view");

            Label dateLabel = new Label();
            dateLabel.setGraphic(new FontIcon(Material2RoundAL.DATE_RANGE));
            StackPane dateLabelContainer = new StackPane(dateLabel);
            dateLabelContainer.getStyleClass().add("date-label-container");

            dateLabel.textProperty().bind(Bindings.createStringBinding(() -> {
                LocalDate date = getDate();
                return date != null ? DATE_FORMATTER.format(date) : "Unknown date";
            }, date));

            CustomMarkdownView markdownView = new CustomMarkdownView();
            markdownView.mdStringProperty().bind(Bindings.createStringBinding(() -> {
                String content = getContent();
                if (content == null) {
                    return "";
                }
                if (isShowAll()) {
                    return content;
                }
                return content.length() > 3000 ? content.substring(0, 3000) : content;
            }, content, showAll));
            // markdownView.setPrefHeight(markdownView.getWidth());

            getChildren().addAll(dateLabelContainer, markdownView);
        }

        private final BooleanProperty showAll = new SimpleBooleanProperty(this, "showAll");

        public final boolean isShowAll() {
            return showAll.get();
        }

        public final BooleanProperty showAllProperty() {
            return showAll;
        }

        public final void setShowAll(boolean showAll) {
            this.showAll.set(showAll);
        }

        // create on date

        private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(this, "date");

        public final LocalDate getDate() {
            return date.get();
        }

        public final ObjectProperty<LocalDate> dateProperty() {
            return date;
        }

        public final void setDate(LocalDate date) {
            this.date.set(date);
        }

        // links of the week content

        private final StringProperty content = new SimpleStringProperty(this, "content");

        public final String getContent() {
            return content.get();
        }

        public final StringProperty contentProperty() {
            return content;
        }

        public final void setContent(String content) {
            this.content.set(content);
        }
    }

}
