package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.components.tiles.LibraryTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobileLibrariesCategoryPage extends MobileCategoryPageBase<Library> {

    public MobileLibrariesCategoryPage(ObjectProperty<Size> size) {
        super(size);
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
    protected Callback<Library, TileViewBase<Library>> getTileViewProvider() {
        return LibraryTileView::new;
    }

    @Override
    protected String getSearchPrompText() {
        return "Search for a library";
    }

    @Override
    protected ObservableList<Library> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getLibraries());
    }
}
