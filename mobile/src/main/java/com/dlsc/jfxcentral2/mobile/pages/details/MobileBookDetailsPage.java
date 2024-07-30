package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Book;

import com.dlsc.jfxcentral2.components.overviewbox.BookOverviewBox;
import com.dlsc.jfxcentral2.mobile.components.LinkedObjectsBox;
import com.dlsc.jfxcentral2.mobile.components.MobilePageHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileBookDetailsPage extends MobileDetailsPageBase<Book> {

    public MobileBookDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Book.class, itemId);
        getStyleClass().add("mobile-book-details-page");
    }

    @Override
    public List<Node> content() {
        Book book = getItem();

        // header
        MobilePageHeader header = new MobilePageHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(book.getName());

        // overview
        BookOverviewBox bookOverviewBox = new BookOverviewBox(book);
        bookOverviewBox.sizeProperty().bind(sizeProperty());
        bookOverviewBox.setIcon(null);
        bookOverviewBox.setTitle(null);

        // linked objects
        LinkedObjectsBox<Book> linkedObjectsBox = new LinkedObjectsBox<>(book);
        linkedObjectsBox.sizeProperty().bind(sizeProperty());

        VBox detailsPageContentWrapper = new VBox(bookOverviewBox, linkedObjectsBox);
        detailsPageContentWrapper.getStyleClass().add("details-page-content-wrapper");

        ScrollPane detailsContentPane = new ScrollPane(detailsPageContentWrapper);
        detailsContentPane.getStyleClass().add("mobile");
        detailsContentPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(detailsContentPane, Priority.ALWAYS);

        return List.of(header, detailsContentPane);
    }
}
