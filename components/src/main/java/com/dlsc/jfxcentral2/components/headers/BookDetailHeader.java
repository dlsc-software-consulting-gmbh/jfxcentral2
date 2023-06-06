package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Book;
import javafx.scene.image.Image;

public class BookDetailHeader extends SimpleDetailHeader<Book> {

    public BookDetailHeader(Book book) {
        super(book);

        getStyleClass().addAll("book-detail-header", "dark-header");

        setWebsiteButtonText("amazon");
        setWebsite("https://www.amazon.com/dp/" + getModel().getAmazonASIN());
        setBackgroundImage(new Image(BookDetailHeader.class.getResource("books-banner.jpg").toExternalForm()));
        setShareUrl("books/" + book.getId());
        setShareText("Found this book on @JFXCentral: " + book.getName() + " - " + book.getSubtitle());
        setShareTitle("JavaFX book: " + book.getName());
        setBackText("ALL BOOKS");
        setBackUrl("/books");
    }
}
