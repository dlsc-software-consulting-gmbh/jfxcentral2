package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.CategoryContentPane;
import com.dlsc.jfxcentral2.components.filters.BooksFilterView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class BooksCategoryPage extends CategoryPageBase<Person> {

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
    public Node content() {
        // header
        CategoryHeader header = createCategoryHeader("Books", IkonUtil.getModelIkon(Book.class));

        // filter
        BooksFilterView filterView = new BooksFilterView();
        filterView.sizeProperty().bind(sizeProperty());

        // details
        CategoryContentPane contentPane = createCategoryContentPane();
        contentPane.getNodes().add(filterView);

        return wrapContent(header, contentPane);
    }
}
