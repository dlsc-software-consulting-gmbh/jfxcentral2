package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.PaginationControl2;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.List;

public class ModelGridView<T extends ModelObject, E extends TileViewBase<T>> extends PaneBase {
    private Node currentDetailView;

    public ModelGridView() {
        getStyleClass().add("model-grid-view");

        columnsProperty().addListener(it -> layoutBySize());
        rowsProperty().addListener(it -> layoutBySize());
        itemsProperty().addListener((InvalidationListener) it -> layoutBySize());
        detailNodeProviderProperty().addListener(it -> layoutBySize());
        tileViewProviderProperty().addListener(it -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        currentDetailView = null;
        if (isSmall()) {
            VBox contentBox = new VBox();
            contentBox.getStyleClass().add("content-box");
            contentBox.setAlignment(Pos.CENTER);
            getChildren().setAll(contentBox);

            int initItemCount = getRows();
            for (int i = 0; i < initItemCount && i < items.size(); i++) {
                E tileView = createTileView(contentBox, i);
                contentBox.getChildren().add(tileView);
            }

            Button loadMoreButton = new Button("Load More");
            loadMoreButton.getStyleClass().addAll("blue-button", "load-more-button");
            VBox.setMargin(loadMoreButton, new Insets(20, 0, 0, 0));
            contentBox.getChildren().add(loadMoreButton);
            loadMoreButton.setOnAction(event -> {
                int currentIndex = contentBox.getChildren().size() - 1;
                if (currentDetailView != null && contentBox.getChildren().contains(currentDetailView)) {
                    currentIndex--;
                }
                int endIndex = Math.min(currentIndex + getRows(), items.size());
                for (int i = currentIndex; i < endIndex; i++) {
                    E tileView = createTileView(contentBox, i);
                    contentBox.getChildren().add(contentBox.getChildren().size() - 1, tileView);
                }
                currentIndex = endIndex;
                if (currentIndex == items.size()) {
                    loadMoreButton.setDisable(true);
                }
            });

        } else {
            int columns = getColumns();
            int rows = getRows();
            List<T> items = getItems();
            PaginationControl2 paginationControl = new PaginationControl2();
            paginationControl.setPageCount((int) Math.ceil((double) items.size() / (double) (columns * rows)));
            paginationControl.setPageFactory(pageIndex -> {
                GridPane gridPane = new GridPane();
                gridPane.getStyleClass().add("grid-pane");
                int startIndex = pageIndex * columns * rows;
                int endIndex = Math.min(startIndex + columns * rows, items.size());
                for (int i = startIndex; i < endIndex; i++) {
                    int row = (i - startIndex) / columns;
                    int column = (i - startIndex) % columns;
                    T model = items.get(i);
                    E tileView = getTileViewProvider().call(model);
                    tileView.sizeProperty().bind(sizeProperty());
                    gridPane.add(tileView, column, row * 2);
                    //show detail node on click
                    tileView.setOnShowDetailNode(()->{
                        if (currentDetailView != null) {
                            gridPane.getChildren().remove(currentDetailView);
                            currentDetailView = null;
                        }
                        if (getDetailNodeProvider() != null) {
                            currentDetailView = getDetailNodeProvider().call(model);
                            currentDetailView.getStyleClass().add("detail-node");
                            gridPane.add(currentDetailView, 0, row * 2 + 1, columns, 1);
                        }
                    });
                }
                return gridPane;
            });
            getChildren().setAll(paginationControl);
        }
    }

    private E createTileView(VBox contentBox, int i) {
        T model = items.get(i);
        E tileView = getTileViewProvider().call(model);
        tileView.sizeProperty().bind(sizeProperty());
        //show detail node on click
        tileView.setOnShowDetailNode(() -> {
            if (currentDetailView != null) {
                contentBox.getChildren().remove(currentDetailView);
                currentDetailView = null;
            }
            if (getDetailNodeProvider() != null) {
                currentDetailView = getDetailNodeProvider().call(model);
                currentDetailView.getStyleClass().add("detail-node");
                int index = contentBox.getChildren().indexOf(tileView);
                contentBox.getChildren().add(index + 1, currentDetailView);
            }
        });
        return tileView;
    }

    private final IntegerProperty columns = new SimpleIntegerProperty(this, "columns", 3);

    public int getColumns() {
        return columns.get();
    }

    public IntegerProperty columnsProperty() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns.set(columns);
    }

    /**
     * Rows represents the number of loads each time on a small screen
     */
    private final IntegerProperty rows = new SimpleIntegerProperty(this, "rows", 3);

    public int getRows() {
        return rows.get();
    }

    public IntegerProperty rowsProperty() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows.set(rows);
    }

    private final ListProperty<T> items = new SimpleListProperty<>(this, "items", FXCollections.observableArrayList());

    public ObservableList<T> getItems() {
        return items.get();
    }

    public ListProperty<T> itemsProperty() {
        return items;
    }

    public void setItems(ObservableList<T> items) {
        this.items.set(items);
    }

    private final ObjectProperty<Callback<T, Node>> detailNodeProvider = new SimpleObjectProperty<>(this, "detailNodeProvider");

    public Callback<T, Node> getDetailNodeProvider() {
        return detailNodeProvider.get();
    }

    public ObjectProperty<Callback<T, Node>> detailNodeProviderProperty() {
        return detailNodeProvider;
    }

    public void setDetailNodeProvider(Callback<T, Node> detailNodeProvider) {
        this.detailNodeProvider.set(detailNodeProvider);
    }

    private final ObjectProperty<Callback<T, E>> tileViewProvider = new SimpleObjectProperty<>(this, "tileViewProvider");

    public Callback<T, E> getTileViewProvider() {
        return tileViewProvider.get();
    }

    public ObjectProperty<Callback<T, E>> tileViewProviderProperty() {
        return tileViewProvider;
    }

    public void setTileViewProvider(Callback<T, E> tileViewProvider) {
        this.tileViewProvider.set(tileViewProvider);
    }
}
