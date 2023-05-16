package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Book;

public class BookDetailHeader extends SimpleDetailHeader<Book> {

    public BookDetailHeader(Book book) {
        this();
        setModel(book);
    }

    public BookDetailHeader() {
        getStyleClass().add("book-detail-header");

        setWebsiteButtonText("amazon");
        setOnWebsite(() -> {
            if (getModel() != null) {
                System.out.println(getModel().getName() + " Book website");
            }
        });
        setOnBack(() -> System.out.println(getClass().getSimpleName() + " back"));
        setOnShare(() -> System.out.println(getClass().getSimpleName() + " share"));
    }

}
