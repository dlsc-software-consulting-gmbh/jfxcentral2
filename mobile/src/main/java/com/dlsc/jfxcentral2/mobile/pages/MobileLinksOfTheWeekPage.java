package com.dlsc.jfxcentral2.mobile.pages;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.mobile.components.MobileCategoryHeader;
import com.dlsc.jfxcentral2.mobile.components.PageView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2RoundAL;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;

public class MobileLinksOfTheWeekPage extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "lotw-page-view";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

    private final SizeSupport sizeSupport = new SizeSupport(this);
    private final LinksContentView[] cachedContentAry = new LinksContentView[3];

    public MobileLinksOfTheWeekPage(ObjectProperty<Size> size) {
        this();
        sizeProperty().bind(size);
    }

    public MobileLinksOfTheWeekPage() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // top header
        MobileCategoryHeader header = new MobileCategoryHeader();
        header.setTitle("Links of the Week");
        header.setIcon(IkonUtil.getModelIkon(LinksOfTheWeek.class));

        // sort by date
        ArrayList<LinksOfTheWeek> sortedLinks = new ArrayList<>(DataRepository2.getInstance().getLinksOfTheWeek());
        sortedLinks.sort(Comparator.comparing(LinksOfTheWeek::getCreatedOn).reversed());

        PageView pageView = new PageView();
        pageView.setPageCount(sortedLinks.size());
        pageView.setPageFactory(index -> {
            LinksOfTheWeek weakLinks = sortedLinks.get(index);

            LinksContentView contentView = getLinksContentView(index);
            contentView.setDate(weakLinks.getCreatedOn());
            contentView.setContent(DataRepository2.getInstance().getLinksOfTheWeekReadMe(weakLinks));
            return contentView;
        });

        PrettyScrollPane scrollPane = new PrettyScrollPane(pageView);
        scrollPane.getStyleClass().add("mobile");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        getChildren().addAll(header, scrollPane);
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
            markdownView.mdStringProperty().bind(contentProperty());
            // markdownView.setPrefHeight(markdownView.getWidth());

            getChildren().addAll(dateLabelContainer, markdownView);
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
