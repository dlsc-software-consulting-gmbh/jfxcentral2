package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.LinksOfTheWeekView;
import com.dlsc.jfxcentral2.components.MenuView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.headers.LinksOfTheWeekHeader;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;

public class LinksOfTheWeekPage extends CategoryPageBase<LinksOfTheWeek> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    public LinksOfTheWeekPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Links of the Week";
    }

    @Override
    public String description() {
        return "Miscellaneous stuff found on the web that is related to JavaFX.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Links of the Week";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(LinksOfTheWeek.class);
    }

    @Override
    public Node content() {

        // header
        LinksOfTheWeekHeader header = new LinksOfTheWeekHeader();
        header.sizeProperty().bind(sizeProperty());

        // links of the week view
        LinksOfTheWeekView linksOfTheWeekView = new LinksOfTheWeekView();
        linksOfTheWeekView.getLinksOfTheWeeks().setAll(DataRepository2.getInstance().getLinksOfTheWeek());
        linksOfTheWeekView.sizeProperty().bind(sizeProperty());

        // this is a category page, but we still need to use the details content pane for layout purposes
        DetailsContentPane detailsContentPane = new DetailsContentPane();
        detailsContentPane.sizeProperty().bind(sizeProperty());
        detailsContentPane.getMenuView().getItems().setAll(createMenuItems());
        detailsContentPane.getCenterNodes().add(linksOfTheWeekView);

        return wrapContent(header, detailsContentPane);
    }

    @Override
    protected ObservableList<LinksOfTheWeek> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getLinksOfTheWeek());
    }

    @Override
    protected Callback<LinksOfTheWeek, TileViewBase<LinksOfTheWeek>> getTileViewProvider() {
        return null;
    }

    @Override
    protected SearchFilterView createSearchFilterView() {
        return null;
    }

    protected List<MenuView.Item> createMenuItems() {
        return DataRepository2.getInstance().getLinksOfTheWeek()
                .stream()
                .limit(5)
                .sorted(Comparator.comparing(LinksOfTheWeek::getCreatedOn).reversed())
                .map(links -> new MenuView.Item(DATE_FORMATTER.format(links.getCreatedOn()), null, null))
                .toList();
    }
}
