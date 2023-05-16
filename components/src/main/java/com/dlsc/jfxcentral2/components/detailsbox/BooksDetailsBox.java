package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.List;
import java.util.function.Consumer;

public class BooksDetailsBox extends DetailsBoxBase<Book> {
    public BooksDetailsBox() {
        getStyleClass().add("books-details-box");
        setTitle("BOOKS");
        setIkon(IkonUtil.book);
        setMaxItemsPerPage(1);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getName());
        });

        setOnHomepage(detailsObject -> {
            System.out.println("On Homepage: " + detailsObject.getName());
        });

        setOnAmazon(detailsObject -> {
            System.out.println("On Amazon: " + detailsObject.getName());
        });

    }

    @Override
    protected List<Node> createActionButtons(Book model) {
        Button amazonButton = new Button("amazon");
        amazonButton.getStyleClass().add("amazon-button");
        amazonButton.managedProperty().bind(amazonButton.visibleProperty());
        amazonButton.visibleProperty().bind(onAmazonProperty().isNotNull());
        amazonButton.setOnAction(e -> {
            if (getOnAmazon() != null) {
                getOnAmazon().accept(model);
            }
        });
        return List.of(createDetailsButton(model), createHomepageButton(model, onHomepageProperty()), amazonButton);
    }

    private final ObjectProperty<Consumer<Book>> onHomepage = new SimpleObjectProperty<>(this, "onHomepage");

    public Consumer<Book> getOnHomepage() {
        return onHomepage.get();
    }

    public ObjectProperty<Consumer<Book>> onHomepageProperty() {
        return onHomepage;
    }

    public void setOnHomepage(Consumer<Book> onHomepage) {
        this.onHomepage.set(onHomepage);
    }

    private final ObjectProperty<Consumer<Book>> onAmazon = new SimpleObjectProperty<>(this, "onAmazon");

    public Consumer<Book> getOnAmazon() {
        return onAmazon.get();
    }

    public ObjectProperty<Consumer<Book>> onAmazonProperty() {
        return onAmazon;
    }

    public void setOnAmazon(Consumer<Book> onAmazon) {
        this.onAmazon.set(onAmazon);
    }
}
