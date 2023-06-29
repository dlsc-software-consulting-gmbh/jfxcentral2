package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.utils.StringUtil;
import javafx.scene.image.Image;
import org.apache.commons.lang3.StringUtils;

public class BookDetailHeader extends SimpleDetailHeader<Book> {

    public BookDetailHeader(Book book) {
        super(book);

        getStyleClass().addAll("book-detail-header", "dark-header");

        if (StringUtils.isNotBlank(book.getAmazonASIN())) {
            setWebsiteButtonText("amazon");
            setWebsite("https://www.amazon.com/dp/" + getModel().getAmazonASIN());
        } else if (StringUtils.isNotBlank(book.getUrl())) {
            setWebsiteButtonText(StringUtil.getDomainName(book.getUrl()));
            setWebsite(book.getUrl());
        }

        setBackgroundImage(new Image(BookDetailHeader.class.getResource("books-banner.jpg").toExternalForm()));
        setShareUrl("books/" + book.getId());
        setShareText("Found this book on @JFXCentral: " + book.getName() + " - " + book.getSubtitle());
        setShareTitle("JavaFX book: " + book.getName());
        setBackText("ALL BOOKS");
        setBackUrl("/books");
    }
}
