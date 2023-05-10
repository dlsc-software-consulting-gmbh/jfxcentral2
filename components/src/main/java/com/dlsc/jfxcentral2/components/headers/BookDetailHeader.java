package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral2.model.SimpleHeaderInfo;

public class BookDetailHeader extends SimpleDetailHeader {

    public BookDetailHeader(SimpleHeaderInfo book) {
        this();
        setSimpleHeaderInfo(book);
    }

    public BookDetailHeader() {
        getStyleClass().add("book-detail-header");

        setWebsiteButtonText("amazon");
        setOnBack(() -> System.out.println(getClass().getSimpleName() + " back"));
        setOnShare(() -> System.out.println(getClass().getSimpleName() + " share"));
    }

}
