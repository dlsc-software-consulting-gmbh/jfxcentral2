package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.BookDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.BookOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class BookDetailsPage extends DetailsPageBase<Book> {

    public BookDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Book.class, itemId);
    }

    @Override
    public Node content() {
        Book book = getItem();

        // header
        BookDetailHeader bookDetailHeader = new BookDetailHeader(book);
        bookDetailHeader.sizeProperty().bind(sizeProperty());

        // overview box
        BookOverviewBox bookOverviewBox = new BookOverviewBox(book);
        bookOverviewBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(bookOverviewBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(bookDetailHeader, detailsContentPane);
    }
}
