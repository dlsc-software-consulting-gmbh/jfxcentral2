package com.dlsc.jfxcentral2.mobile.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class MobilePagination<T> extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "mobile-pagination";
    private final StackPane contentWrapper;

    public MobilePagination() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Button previousButton = new Button();
        previousButton.getStyleClass().addAll("nav-button", "previous-button");
        previousButton.setGraphic(new FontIcon(MaterialDesign.MDI_CHEVRON_LEFT));
        previousButton.disableProperty().bind(index.isEqualTo(0));
        previousButton.setOnAction(e -> setIndex(getIndex() - 1));

        Button nextButton = new Button();
        nextButton.getStyleClass().addAll("nav-button", "next-button");
        nextButton.setGraphic(new FontIcon(MaterialDesign.MDI_CHEVRON_RIGHT));
        nextButton.disableProperty().bind(Bindings.createBooleanBinding(() -> getIndex() >= items.size() - 1, indexProperty(), getItems()));
        nextButton.setOnAction(e -> setIndex(getIndex() + 1));

        Label pageLabel = new Label();
        pageLabel.getStyleClass().add("page-label");
        pageLabel.textProperty().bind(Bindings.concat(indexProperty().add(1), " / ", Bindings.size(items)));

        HBox navBox = new HBox(previousButton, pageLabel, nextButton);
        navBox.getStyleClass().add("nav-box");
        pageLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(pageLabel, Priority.ALWAYS);

        contentWrapper = new StackPane();
        contentWrapper.getStyleClass().add("content-wrapper");
        VBox.setVgrow(contentWrapper, Priority.ALWAYS);

        getChildren().addAll(contentWrapper, navBox);

        itemProperty().bind(Bindings.createObjectBinding(() -> {
            boolean validatedIndex = isValidIndex();
            return validatedIndex ? getItems().get(getIndex()) : null;
        }, indexProperty(), getItems()));

        updatedPageView();
        indexProperty().addListener(it -> updatedPageView());
    }

    protected void updatedPageView() {
        boolean validatedIndex = isValidIndex();
        if (validatedIndex) {
            contentWrapper.getChildren().setAll(getCellFactory().call(getIndex()));
        } else {
            contentWrapper.getChildren().clear();
        }
    }

    private boolean isValidIndex() {
        int currentIndex = getIndex();
        int size = getItems().size();
        return currentIndex >= 0 && currentIndex < size;
    }

    public final void setSelectedItem(T item) {
        setIndex(getItems().indexOf(item));
    }

    // items

    private final ObservableList<T> items = FXCollections.observableArrayList();

    public ObservableList<T> getItems() {
        return items;
    }

    // index

    private final IntegerProperty index = new SimpleIntegerProperty(this, "index", -1);

    public final IntegerProperty indexProperty() {
        return index;
    }

    public final int getIndex() {
        return index.get();
    }

    public final void setIndex(int index) {
        this.index.set(index);
    }

    // item

    private final ReadOnlyObjectWrapper<T> item = new ReadOnlyObjectWrapper<>(this, "item");

    public final T getItem() {
        return item.get();
    }

    public final ReadOnlyObjectWrapper<T> itemProperty() {
        return item;
    }

    private void setItem(T item) {
        this.item.set(item);
    }

    // cellFactory

    private final ObjectProperty<Callback<Integer, Node>> cellFactory = new SimpleObjectProperty<>(this, "cellFactory", i -> new Label("Page " + (i + 1)));

    public final ObjectProperty<Callback<Integer, Node>> cellFactoryProperty() {
        return cellFactory;
    }

    public final Callback<Integer, Node> getCellFactory() {
        return cellFactory.get();
    }

    public final void setCellFactory(Callback<Integer, Node> cellFactory) {
        cellFactoryProperty().set(cellFactory);
    }

}