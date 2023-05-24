package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Book;

public class BookDetailHeader extends SimpleDetailHeader<Book> {

    public BookDetailHeader(Book book) {
        super(book);

        getStyleClass().add("book-detail-header");
        setWebsiteButtonText("amazon");
        setWebsite("https://www.amazon.com/dp/" + getModel().getAmazonASIN());
    }
}
