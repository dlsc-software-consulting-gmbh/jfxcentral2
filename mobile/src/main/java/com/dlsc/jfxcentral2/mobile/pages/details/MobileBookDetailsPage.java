package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.BookDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.BookOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.util.List;

public class MobileBookDetailsPage extends MobileDetailsPageBase<Book> {

    public MobileBookDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Book.class, itemId);
    }

    @Override
    public List<Node> content() {
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

        return List.of(bookDetailHeader, detailsContentPane);
    }
}
