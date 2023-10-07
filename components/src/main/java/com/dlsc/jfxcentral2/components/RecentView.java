package com.dlsc.jfxcentral2.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;

import java.util.List;

public class RecentView<T> extends FlowPane {

    private final ToggleGroup toggleGroup = new ToggleGroup();
    private boolean updating = false;

    public RecentView() {
        getStyleClass().add("recent-view");

        items.addListener((ListChangeListener<T>) c -> {
            if (!updating) {
                updateView();
            }
        });

        selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || !items.contains(newVal)) {
                toggleGroup.selectToggle(null);
                return;
            }
            for (Node child : getChildren()) {
                CustomToggleButton btn = (CustomToggleButton) child;
                if (newVal.equals(btn.getUserData())) {
                    btn.setSelected(true);
                    break;
                }
            }
        });

        selectedIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            if (newIndex == null || newIndex.intValue() < 0 || newIndex.intValue() >= items.size()) {
                toggleGroup.selectToggle(null);
                return;
            }
            int index = newIndex.intValue();
            if (index >= 0 && index < items.size()) {
                CustomToggleButton btn = (CustomToggleButton) getChildren().get(index);
                btn.setSelected(true);
            }
        });

        toggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                T item = (T) newToggle.getUserData();
                setSelectedItem(item);
                setSelectedIndex(items.indexOf(item));
            } else {
                setSelectedItem(null);
                setSelectedIndex(-1);
            }
        });

        maxItemsProperty().addListener((ob, oldVal, newVal) -> updateView());
        itemFactoryProperty().addListener((obs, oldFactory, newFactory) -> updateView());

        updateView();
    }

    private CustomToggleButton createItemCell(T item) {
        Node node = itemFactory.get().call(item);
        CustomToggleButton btn = new CustomToggleButton();
        btn.setGraphic(node);
        btn.setToggleGroup(toggleGroup);
        btn.setUserData(item);
        return btn;
    }

    private void updateView() {
        updating = true;
        try {
            T tempSelectedItem = getSelectedItem();

            getChildren().clear();
            toggleGroup.getToggles().clear();
            int overflowCount = items.size() - getMaxItems();
            if (overflowCount > 0) {
                items.subList(0, overflowCount).clear();
            }
            List<CustomToggleButton> nodes = items.stream()
                    .map(this::createItemCell)
                    .toList();
            getChildren().addAll(nodes);

            nodes.forEach(node -> {
                if (node.getUserData().equals(tempSelectedItem)) {
                    node.setSelected(true);
                }
            });

        } finally {
            updating = false;
        }
    }

    private final ObjectProperty<Callback<T, Node>> itemFactory = new SimpleObjectProperty<>(this, "itemFactory", t -> new Label(t.toString()));

    public Callback<T, Node> getItemFactory() {
        return itemFactory.get();
    }

    public ObjectProperty<Callback<T, Node>> itemFactoryProperty() {
        return itemFactory;
    }

    public void setItemFactory(Callback<T, Node> itemFactory) {
        this.itemFactory.set(itemFactory);
    }

    private final ObservableList<T> items = FXCollections.observableArrayList();

    public ObservableList<T> getItems() {
        return items;
    }

    private final IntegerProperty maxItems = new SimpleIntegerProperty(this, "maxItems", 10);

    public IntegerProperty maxItemsProperty() {
        return maxItems;
    }

    public int getMaxItems() {
        return maxItems.get();
    }

    public void setMaxItems(int maxItems) {
        this.maxItems.set(maxItems);
    }

    private final ObjectProperty<T> selectedItem = new SimpleObjectProperty<>(this, "selectedItem");

    public ObjectProperty<T> selectedItemProperty() {
        return selectedItem;
    }

    public T getSelectedItem() {
        return selectedItem.get();
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem.set(selectedItem);
    }

    private final IntegerProperty selectedIndex = new SimpleIntegerProperty(this, "selectedIndex", -1);

    public IntegerProperty selectedIndexProperty() {
        return selectedIndex;
    }

    public int getSelectedIndex() {
        return selectedIndex.get();
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex.set(selectedIndex);
    }
}
