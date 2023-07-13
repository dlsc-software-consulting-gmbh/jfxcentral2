package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.LibrariesFilterView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.tiles.LibraryTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class LibrariesCategoryPage extends CategoryPageBase<Library> {

    public LibrariesCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Libraries";
    }

    @Override
    public String description() {
        return "A curated list of libraries available for JavaFX.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Libraries";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Library.class);
    }

    @Override
    protected int getNumberOfGridViewRows() {
        return 5;
    }

    @Override
    protected Callback<Library, TileViewBase<Library>> getTileViewProvider() {
        return LibraryTileView::new;
    }

    @Override
    protected SearchFilterView<Library> createSearchFilterView() {
        return new LibrariesFilterView();
    }

    @Override
    protected ObservableList<Library> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getLibraries());
    }
}
