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
        modelProperty().addListener(it -> setWebsite("http://www.amazon.com/dp/" + getModel().getAmazonASIN()));
    }

}
