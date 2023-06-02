package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.model.Book;

import java.util.Comparator;

public class BooksFilterView extends SimpleSearchFilterView<Book> {
    public BooksFilterView() {
        getStyleClass().add("books-filter-view");
        setSearchPromptText("Search for a book");
        setComparator(Comparator.comparing(Book::getName));

        setOnSearch((keywords, filterEnums) -> {
            System.out.println("------------Search book------------");
            System.out.println("Search keywords: " + keywords);
            System.out.println("Selected Filters: " + filterEnums);
        });

        selectedFiltersProperty().addListener((ob, ov, nv) -> {
            System.out.println("------------Books Selected filters ------------");
            System.out.println("Selected filters: " + nv);
        });
    }
}
