package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.util.Callback;
import one.jpro.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

public class BooksDetailsBox extends DetailsBoxBase<Book> {

    public BooksDetailsBox() {
        super();

        getStyleClass().add("books-details-box");

        setTitle("BOOKS");
        setIkon(IkonUtil.book);
        setMaxItemsPerPage(1);
        setHomepageUrlProvider(Book::getUrl);
        setOnAmazon(book -> "https://www.amazon.com/dp/" + book.getAmazonASIN());
    }

    @Override
    protected List<Node> createActionButtons(Book book) {
        String url = getOnAmazon().call(book);
        if (StringUtils.isNotBlank(url)) {
            Button amazonButton = new Button("amazon");
            amazonButton.getStyleClass().add("amazon-button");
            LinkUtil.setLink(amazonButton, url);
            return List.of(createDetailsButton(book), createHomepageButton(book), amazonButton);
        }

        return Collections.emptyList();
    }

    private final ObjectProperty<Callback<Book, String>> onAmazon = new SimpleObjectProperty<>(this, "onAmazon");

    public Callback<Book, String> getOnAmazon() {
        return onAmazon.get();
    }

    public ObjectProperty<Callback<Book, String>> onAmazonProperty() {
        return onAmazon;
    }

    public void setOnAmazon(Callback<Book, String> onAmazon) {
        this.onAmazon.set(onAmazon);
    }
}
