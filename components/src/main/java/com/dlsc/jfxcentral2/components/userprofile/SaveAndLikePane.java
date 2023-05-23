package com.dlsc.jfxcentral2.components.userprofile;

import com.dlsc.jfxcentral2.components.PaginationControl2;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.model.SaveAndLikeModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.List;

public class SaveAndLikePane extends PaneBase {

    public SaveAndLikePane() {
        getStyleClass().add("save-and-like-pane");

        PaginationControl2 paginationControl = new PaginationControl2();
        paginationControl.pageCountProperty().bind(Bindings.createIntegerBinding(() -> {
            int size = getItems().size();
            int maxItems = getMaxItems();
            if (size == 0) {
                return 1;
            }
            return (size - 1) / maxItems + 1;
        }, itemsProperty(), maxItemsProperty()));

        paginationControl.pageFactoryProperty().bind(Bindings.createObjectBinding(() -> pageIndex -> {
            List<SaveAndLikeModel> models = getItems().subList(pageIndex * getMaxItems(), Math.min((pageIndex + 1) * getMaxItems(), getItems().size()));
            VBox page = new VBox();
            page.getStyleClass().add("model-page");
            for (int i = 0; i < models.size(); i++) {
                SaveAndLikeCell cell = new SaveAndLikeCell();
                if (i == 0) {
                    cell.getStyleClass().add("first");
                }
                if (i == models.size() - 1) {
                    cell.getStyleClass().add("last");
                }
                cell.sizeProperty().bind(sizeProperty());
                cell.setItem(models.get(i));
                page.getChildren().add(cell);
            }
            return page;
        }, itemsProperty(), maxItemsProperty()));

        maxItemsProperty().bind(sizeProperty().map(it -> it.isSmall() ? 6 : 7));

        getChildren().setAll(paginationControl);

    }

    private final ListProperty<SaveAndLikeModel> items = new SimpleListProperty<>(this, "items", FXCollections.observableArrayList());

    public ObservableList<SaveAndLikeModel> getItems() {
        return items.get();
    }

    public ListProperty<SaveAndLikeModel> itemsProperty() {
        return items;
    }

    public void setItems(ObservableList<SaveAndLikeModel> items) {
        this.items.set(items);
    }

    private final IntegerProperty maxItems = new SimpleIntegerProperty(this, "maxItems", 7);

    public int getMaxItems() {
        return maxItems.get();
    }

    public IntegerProperty maxItemsProperty() {
        return maxItems;
    }

    public void setMaxItems(int maxItems) {
        this.maxItems.set(maxItems);
    }
}
