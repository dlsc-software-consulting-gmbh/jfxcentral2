package com.dlsc.jfxcentral2.mobile.utils;

import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.function.BiConsumer;

public class ListViewUtil {

    /**
     * Adds a mouse click event handler to a ListView. The handler is triggered only when the click occurs on a non-empty ListCell
     * and the mouse has not moved between the press and release of the click. This helps to prevent the handler from being
     * triggered during a drag gesture.
     *
     * @param listView The ListView to which the event handler will be added.
     * @param action   The action to perform when a click occurs, which accepts two parameters: the index of the ListCell and the item object.
     * @param <T>      The type of the items in the ListView.
     */
    public static <T> void addCellClickHandler(ListView<T> listView, BiConsumer<Integer, T> action) {
        listView.setOnMouseClicked(event -> {
            if (!event.isStillSincePress()) {
                return;
            }

            Node node = event.getPickResult().getIntersectedNode();

            // Traverse up to find the ListCell or reach the ListView itself
            while (node != null && node != listView && !(node instanceof ListCell)) {
                node = node.getParent();
            }

            // Check if the click is on a ListCell that contains data
            if (node instanceof ListCell<?> cell && !cell.isEmpty()) {
                // Execute the defined action
                action.accept(cell.getIndex(), (T) cell.getItem());
            }
        });
    }
}
