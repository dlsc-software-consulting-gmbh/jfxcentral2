package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.BooksFilterView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.gridview.ModelGridView;
import com.dlsc.jfxcentral2.components.tiles.BookTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class BooksCategoryPage extends CategoryPageBase<Book> {

    public BooksCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Books";
    }

    @Override
    public String description() {
        return "A curated list of books covering JavaFX or topics related to JavaFX.";
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
    protected Callback<ModelGridView<Book>, TileViewBase<Book>> getTileViewProvider() {
        return gridView -> new BookTileView();
    }

    @Override
    protected SearchFilterView createSearchFilterView() {
        return new BooksFilterView();
    }

    @Override
    protected ObservableList<Book> getCategoryItems() {
        return DataRepository.getInstance().getBooks();
    }
}
