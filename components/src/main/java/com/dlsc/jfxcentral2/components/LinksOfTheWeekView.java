package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.function.Consumer;

public class LinksOfTheWeekView extends PaneBase {

    private final PaginationControl2 pagination;
    private final LinksOfTheWeek lotw;

    public LinksOfTheWeekView(LinksOfTheWeek lotw) {
        getStyleClass().add("links-of-the-week-view");
        this.lotw = lotw;

        VBox linksBox = new VBox(createPaginationTitleBox());
        linksBox.getStyleClass().add("links-box");

        CustomMarkdownView markdownView = new CustomMarkdownView();
        markdownView.setMdString(DataRepository.getInstance().getLinksOfTheWeekReadMe(lotw));
        markdownView.setPrefHeight(markdownView.getWidth());
        linksBox.getChildren().add(markdownView);

        pagination = new PaginationControl2();
        pagination.getStyleClass().add("pagination");

        VBox contentBox = new VBox(linksBox, pagination);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);

        createFactory();
        pagination.setPrefHeight(Region.USE_PREF_SIZE);
        selectedIndex.bind(pagination.currentPageIndexProperty());
        linksOfTheWeeksProperty().addListener((ob, ov, nv) -> createFactory());
        sizeProperty().addListener((ob, ov, nv) -> createFactory());
    }

    private HBox createPaginationTitleBox() {
        Button previousButton = new Button();
        previousButton.getStyleClass().add("prev-button");
        previousButton.setGraphic(new FontIcon(BootstrapIcons.ARROW_LEFT_CIRCLE_FILL));
        previousButton.setMouseTransparent(false);
        previousButton.setOnAction(e -> goToPreviousPage());
        previousButton.disableProperty().bind(selectedIndexProperty().isEqualTo(0));
        previousButton.managedProperty().bind(previousButton.visibleProperty());
        previousButton.visibleProperty().bind(sizeProperty().map(s -> s != Size.LARGE));

        Label dateLabel = new Label();
        dateLabel.getStyleClass().add("date-label");
        dateLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(dateLabel, Priority.ALWAYS);

        dateLabel.setText(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(lotw.getCreatedOn()));

        Button nextButton = new Button();
        nextButton.getStyleClass().add("next-button");
        nextButton.setMouseTransparent(false);
        nextButton.setGraphic(new FontIcon(BootstrapIcons.ARROW_RIGHT_CIRCLE_FILL));
        nextButton.setOnAction(e -> goToNextPage());
        nextButton.disableProperty().bind(Bindings.createBooleanBinding(() -> getSelectedIndex() == getLinksOfTheWeeks().size() - 1, selectedIndexProperty(), linksOfTheWeeks));

        nextButton.managedProperty().bind(nextButton.visibleProperty());
        nextButton.visibleProperty().bind(sizeProperty().map(s -> s != Size.LARGE));

        HBox paginationBox = new HBox(previousButton, dateLabel, nextButton);
        paginationBox.getStyleClass().add("pagination-title-box");
        paginationBox.managedProperty().bind(paginationBox.visibleProperty());

        return paginationBox;
    }

    private void createFactory() {
        pagination.setMaxPageIndicatorCount(isSmall() ? 2 : 3);
        List<LinksOfTheWeek> linksOfTheWeeks = getLinksOfTheWeeks();
        pagination.setPageCount(linksOfTheWeeks.size());

        LinksOfTheWeek currentLotw = linksOfTheWeeks.stream().filter(l -> StringUtils.equals(l.getId(), lotw.getId())).findFirst().orElse(null);
        int currentPageIndex = currentLotw == null ? -1 : linksOfTheWeeks.indexOf(currentLotw);
        pagination.setCurrentPageIndex(currentPageIndex);

        pagination.setPageFactory(pageIndex -> {
            if (pageIndex != currentPageIndex) {
                goToPage(pageIndex);
            }
            return null;
        });
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

    public void goToPage(int pageIndex) {
        if (pageIndex >= 0 && pageIndex < getLinksOfTheWeeks().size()) {
            doGoToPageAction(pageIndex);
        }
    }

    public void goToNextPage() {
        int nextPageIndex = Math.min(pagination.getCurrentPageIndex() + 1, pagination.getPageCount() - 1);
        if (nextPageIndex != pagination.getCurrentPageIndex()) {
            doGoToPageAction(nextPageIndex);
        }
    }

    public void goToPreviousPage() {
        int previousPageIndex = Math.max(pagination.getCurrentPageIndex() - 1, 0);
        if (previousPageIndex != pagination.getCurrentPageIndex()) {
            doGoToPageAction(previousPageIndex);
        }
    }

    private void doGoToPageAction(int pageIndex) {
        getOnGoToLinkOfTheWeek().accept(getLinksOfTheWeeks().get(pageIndex));
    }

    private final ObjectProperty<Consumer<LinksOfTheWeek>> onGoToLinkOfTheWeek = new SimpleObjectProperty<>(this, "onGoToLinkOfTheWeek");

    public Consumer<LinksOfTheWeek> getOnGoToLinkOfTheWeek() {
        return onGoToLinkOfTheWeek.get();
    }

    public void setOnGoToLinkOfTheWeek(Consumer<LinksOfTheWeek> onGoToLinkOfTheWeek) {
        this.onGoToLinkOfTheWeek.set(onGoToLinkOfTheWeek);
    }

    public ObjectProperty<Consumer<LinksOfTheWeek>> onGoToLinkOfTheWeekProperty() {
        return onGoToLinkOfTheWeek;
    }

    private final ReadOnlyIntegerWrapper selectedIndex = new ReadOnlyIntegerWrapper(this, "selectedIndex", 0);

    public int getSelectedIndex() {
        return selectedIndex.get();
    }

    public ReadOnlyIntegerProperty selectedIndexProperty() {
        return selectedIndex.getReadOnlyProperty();
    }
}