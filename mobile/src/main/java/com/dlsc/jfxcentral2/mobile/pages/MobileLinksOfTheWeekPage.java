package com.dlsc.jfxcentral2.mobile.pages;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.MobilePageBase;
import com.dlsc.jfxcentral2.mobile.components.MobilePageHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;

public class MobileLinksOfTheWeekPage extends MobilePageBase {

    private static final String DEFAULT_STYLE_CLASS = "lotw-page-view";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);

    public MobileLinksOfTheWeekPage(ObjectProperty<Size> size) {
        this();
        sizeProperty().bind(size);
    }

    public MobileLinksOfTheWeekPage() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // top header
        MobilePageHeader header = new MobilePageHeader();
        header.setTitle("Links of the Week");
        header.setIcon(IkonUtil.getModelIkon(LinksOfTheWeek.class));

        // sort by date
        ArrayList<LinksOfTheWeek> sortedLinks = new ArrayList<>(DataRepository2.getInstance().getLinksOfTheWeek());
        sortedLinks.sort(Comparator.comparing(LinksOfTheWeek::getCreatedOn).reversed());

        ListView<LinksOfTheWeek> listView = new ListView<>();
        listView.getStyleClass().add("mobile");
        listView.getItems().setAll(sortedLinks);
        listView.setCellFactory(param -> new LinksOfTheWeekCell());

        StackPane contentWrapper = new StackPane(listView);
        contentWrapper.getStyleClass().add("content-wrapper");
        VBox.setVgrow(contentWrapper, Priority.ALWAYS);

        getChildren().addAll(header, contentWrapper);
    }

    private static class LinksOfTheWeekCell extends ListCell<LinksOfTheWeek> {

        private final VBox graphic = new VBox();
        private final CustomMarkdownView markdownView;
        private final Label dateLabel;

        public LinksOfTheWeekCell() {
            getStyleClass().add("links-cell");
            setPrefWidth(0);

            dateLabel = new Label();

            Region separator = new Region();
            separator.getStyleClass().add("separator");
            HBox.setHgrow(separator, Priority.ALWAYS);

            HBox dateLabelContainer = new HBox(dateLabel, separator);
            dateLabelContainer.getStyleClass().add("date-label-container");

            markdownView = new CustomMarkdownView();
            markdownView.setFillWidth(true);

            graphic.getStyleClass().add("cell-content");
            graphic.getChildren().addAll(dateLabelContainer, markdownView);
        }

        @Override
        protected void updateItem(LinksOfTheWeek item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                dateLabel.setText("");
                markdownView.setMdString("");
                setGraphic(null);
            } else {
                dateLabel.setText(DATE_FORMATTER.format(item.getCreatedOn()));
                markdownView.setMdString(DataRepository2.getInstance().getLinksOfTheWeekReadMe(item));
                setGraphic(graphic);
            }
        }
    }

}
