package com.dlsc.jfxcentral2.mobile.componenets;

import com.dlsc.gemsfx.SearchTextField;
import com.dlsc.jfxcentral.data.model.ModelObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.function.Predicate;

public class ModelListView<T extends ModelObject> extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "model-list-view";
    private final Callback<String, Predicate<T>> defaultFilter = text -> tip -> StringUtils.isBlank(text)
            || StringUtils.containsIgnoreCase(tip.getName(), text)
            || StringUtils.containsIgnoreCase(tip.getDescription(), text);

    public ModelListView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        SearchTextField searchTextField = new SearchTextField(true);
        searchTextField.promptTextProperty().bind(promptTextProperty());
        StackPane searchWrapper = new StackPane(searchTextField);
        searchWrapper.getStyleClass().add("search-wrapper");

        FilteredList<T> filteredList = new FilteredList<>(itemsList);
        filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> getFilter().call(searchTextField.textProperty().getValueSafe()), searchTextField.textProperty()));

        SortedList<T> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(comparatorProperty());

        ListView<T> listView = new ListView<>(sortedList);
        listView.cellFactoryProperty().bind(cellFactoryProperty());
        listView.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(listView, Priority.ALWAYS);

        getChildren().setAll(searchWrapper, listView);
        setMaxHeight(Double.MAX_VALUE);
    }

    private final ObservableList<T> itemsList = FXCollections.observableArrayList();

    public final ObservableList<T> getItemsList() {
        return itemsList;
    }

    // promptText

    private final StringProperty promptText = new SimpleStringProperty(this, "promptText", "Search...");

    public final String getPromptText() {
        return promptText.get();
    }

    public final StringProperty promptTextProperty() {
        return promptText;
    }

    public final void setPromptText(String promptText) {
        this.promptText.set(promptText);
    }

    // filter

    private ObjectProperty<Callback<String, Predicate<T>>> filter;

    public final ObjectProperty<Callback<String, Predicate<T>>> filterProperty() {
        if (filter == null) {
            filter = new SimpleObjectProperty<>(this, "filter", defaultFilter);
        }
        return filter;
    }

    public final Callback<String, Predicate<T>> getFilter() {
        return filter == null ? defaultFilter : filter.get();
    }

    public final void setFilter(Callback<String, Predicate<T>> filter) {
        filterProperty().set(filter);
    }

    // comparator

    public final ObjectProperty<Comparator<T>> comparator = new SimpleObjectProperty<>(this, "comparator",  Comparator.comparing((T modelObject) -> StringUtils.defaultIfEmpty(modelObject.getName(),"").toLowerCase()));

    public final ObjectProperty<Comparator<T>> comparatorProperty() {
        return comparator;
    }

    public final Comparator<T> getComparator() {
        return comparator.get();
    }

    public final void setComparator(Comparator<T> comparator) {
        this.comparator.set(comparator);
    }

    // cell factory

    private final ObjectProperty<Callback<ListView<T>, ListCell<T>>> cellFactory = new SimpleObjectProperty<>(this, "cellFactory");

    public final void setCellFactory(Callback<ListView<T>, ListCell<T>> value) {
        cellFactoryProperty().set(value);
    }

    public final Callback<ListView<T>, ListCell<T>> getCellFactory() {
        return cellFactory.get();
    }

    public final ObjectProperty<Callback<ListView<T>, ListCell<T>>> cellFactoryProperty() {
        return cellFactory;
    }

}
