package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.StringUtil;
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
        setIkon(IkonUtil.getModelIkon(Book.class));
        setMaxItemsPerPage(1);
        setHomepageUrlProvider(Book::getUrl);
        setOnWebsite(book -> {
            if (StringUtils.isNotBlank(book.getAmazonASIN())) {
                return "https://www.amazon.com/dp/" + book.getAmazonASIN();
            } else if (StringUtils.isNotBlank(book.getUrl())) {
                return book.getUrl();
            }
            return null;
        });
    }

    @Override
    protected List<Node> createActionButtons(Book book) {
        String url = getOnWebsite().call(book);
        if (StringUtils.isNotBlank(url)) {
            Button websiteBtn = new Button();
            if (StringUtils.isNotBlank(book.getAmazonASIN())) {
                websiteBtn.setText("amazon");
            }else if (StringUtils.isNotBlank(book.getUrl())) {
                websiteBtn.setText(StringUtil.getDomainName(book.getUrl()));
            }
            websiteBtn.getStyleClass().add("amazon-button");
            LinkUtil.setExternalLink(websiteBtn, url);
            return List.of(createDetailsButton(book), createHomepageButton(book), websiteBtn);
        }
        return Collections.emptyList();
    }

    private final ObjectProperty<Callback<Book, String>> onWebsite = new SimpleObjectProperty<>(this, "onWebsite");

    public Callback<Book, String> getOnWebsite() {
        return onWebsite.get();
    }

    public ObjectProperty<Callback<Book, String>> onWebsiteProperty() {
        return onWebsite;
    }

    public void setOnWebsite(Callback<Book, String> onWebsite) {
        this.onWebsite.set(onWebsite);
    }
}
