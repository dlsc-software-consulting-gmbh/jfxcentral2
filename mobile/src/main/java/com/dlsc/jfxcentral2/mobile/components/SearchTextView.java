package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.gemsfx.SearchTextField;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.StackPane;

public class SearchTextView extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "search-text-view";
    private final SizeSupport sizeSupport = new SizeSupport(this);

    public SearchTextView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        SearchTextField searchTextField = new SearchTextField();
        searchTextField.setRound(true);
        searchTextField.promptTextProperty().bind(promptTextProperty());
        searchText.bind(searchTextField.textProperty());

        getChildren().add(searchTextField);
    }

    // blocking

    private final BooleanProperty blocking = new SimpleBooleanProperty(this, "blocking");

    public final boolean isBlocking() {
        return blocking.get();
    }

    public final BooleanProperty blockingProperty() {
        return blocking;
    }

    public final void setBlocking(boolean blocking) {
        this.blocking.set(blocking);
    }

    // size

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    // prompt text

    private final StringProperty promptText = new SimpleStringProperty(this, "promptText" , "Search ...");

    public final String getPromptText() {
        return promptText.get();
    }

    public final StringProperty promptTextProperty() {
        return promptText;
    }

    public final void setPromptText(String promptText) {
        promptTextProperty().set(promptText);
    }

    // text

    private final ReadOnlyStringWrapper searchText = new ReadOnlyStringWrapper(this, "searchText", "");

    public final String getSearchText() {
        return searchText.get();
    }

    public final ReadOnlyStringProperty searchTextProperty() {
        return searchText.getReadOnlyProperty();
    }

}
