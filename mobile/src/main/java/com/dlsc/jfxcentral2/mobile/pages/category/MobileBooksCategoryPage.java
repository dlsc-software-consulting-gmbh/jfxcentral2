package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.components.tiles.BookTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobileBooksCategoryPage extends MobileCategoryPageBase<Book> {

    public MobileBooksCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected String getCategoryTitle() {
        return "Books";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Book.class);
    }

    @Override
    protected Callback<Book, TileViewBase<Book>> getTileViewProvider() {
        return BookTileView::new;
    }

    @Override
    protected String getSearchPrompText() {
        return "Search for a book";
    }

    @Override
    protected ObservableList<Book> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getBooks());
    }
}
