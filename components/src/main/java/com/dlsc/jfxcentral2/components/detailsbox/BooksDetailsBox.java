package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.model.BooksDetailsObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.kordamp.ikonli.materialdesign2.MaterialDesignB;

import java.util.List;
import java.util.function.Consumer;

public class BooksDetailsBox extends DetailsBoxBase<BooksDetailsObject> {
    public BooksDetailsBox() {
        getStyleClass().add("books-details-box");
        setTitle("BOOKS");
        setIkon(MaterialDesignB.BOOK_OPEN_VARIANT);
        setMaxItemsPerPage(1);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getTitle());
        });

        setOnHomepage(detailsObject -> {
            System.out.println("On Homepage: " + detailsObject.getTitle());
        });

        setOnAmazon(detailsObject -> {
            System.out.println("On Amazon: " + detailsObject.getTitle());
        });

    }

    @Override
    protected List<Node> createActionButtons(BooksDetailsObject model) {
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

    private final ObjectProperty<Consumer<BooksDetailsObject>> onHomepage = new SimpleObjectProperty<>(this, "onHomepage");

    public Consumer<BooksDetailsObject> getOnHomepage() {
        return onHomepage.get();
    }

    public ObjectProperty<Consumer<BooksDetailsObject>> onHomepageProperty() {
        return onHomepage;
    }

    public void setOnHomepage(Consumer<BooksDetailsObject> onHomepage) {
        this.onHomepage.set(onHomepage);
    }

    private final ObjectProperty<Consumer<BooksDetailsObject>> onAmazon = new SimpleObjectProperty<>(this, "onAmazon");

    public Consumer<BooksDetailsObject> getOnAmazon() {
        return onAmazon.get();
    }

    public ObjectProperty<Consumer<BooksDetailsObject>> onAmazonProperty() {
        return onAmazon;
    }

    public void setOnAmazon(Consumer<BooksDetailsObject> onAmazon) {
        this.onAmazon.set(onAmazon);
    }
}
