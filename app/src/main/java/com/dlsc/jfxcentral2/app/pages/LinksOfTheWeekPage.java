package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.MenuView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.gridview.ModelGridView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

import java.util.List;

public class LinksOfTheWeekPage extends CategoryPageBase<LinksOfTheWeek> {

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
        CategoryHeader header = createCategoryHeader();

        // this is a category page, but we still need to use the details content pane for layout purposes
        DetailsContentPane detailsContentPane = new DetailsContentPane();
        detailsContentPane.sizeProperty().bind(sizeProperty());
        detailsContentPane.getFeaturesContainer().getFeatures().setAll(createFeatures());
        detailsContentPane.getMenuView().getItems().setAll(createMenuItems());

        return wrapContent(header, detailsContentPane);
    }

    @Override
    protected ObservableList<LinksOfTheWeek> getCategoryItems() {
        return DataRepository.getInstance().getLinksOfTheWeek();
    }

    @Override
    protected Callback<ModelGridView<LinksOfTheWeek>, TileViewBase<LinksOfTheWeek>> getTileViewProvider() {
        return null;
    }

    @Override
    protected SearchFilterView createSearchFilterView() {
        return null;
    }

    protected List<MenuView.Item> createMenuItems() {
        return FXCollections.observableArrayList(
                new MenuView.Item("16. JAN - 23. JAN", null, null),
                new MenuView.Item("24. JAN - 31. JAN", null, null)
        );
    }
}
