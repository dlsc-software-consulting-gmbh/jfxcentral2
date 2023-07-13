package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Book;
import org.apache.commons.lang3.StringUtils;

public class BooksFilterView extends SimpleModelObjectSearchFilterView<Book> {

    public BooksFilterView() {
        getStyleClass().add("books-filter-view");
        setSearchPromptText("Search for a book");

        setOnSearch(text -> book -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(book.getName(), text)
                || StringUtils.containsIgnoreCase(book.getDescription(), text));

    }
}
