package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.jfxcentral2.components.PaginationControl2;
import com.dlsc.jfxcentral2.components.PaneBase;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.util.List;

public class SimpleGridView<T> extends PaneBase {

    private DetailView<T> currentDetailView;
    private CellView<T> lasetSelectedCellView;
    public SimpleGridView() {
        getStyleClass().add("simple-grid-view");

        InvalidationListener layoutListener = it -> layoutBySize();


        cellViewProviderProperty().addListener(layoutListener);
        detailNodeProviderProperty().addListener(layoutListener);
        rowsProperty().addListener(layoutListener);
        itemsProperty().addListener(layoutListener);
        columnsProperty().addListener(layoutListener);
    }

    @Override
    protected void layoutBySize() {
        currentDetailView = null;
        int columns = getColumns();
        int rows = getRows();
        List<T> items = getItems();
        PaginationControl2 paginationControl = new PaginationControl2();
        paginationControl.setPageCount((int) Math.ceil((double) items.size() / (double) (columns * rows)));
        paginationControl.setPageFactory(pageIndex -> {
            GridPane gridPane = new GridPane();
            gridPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            gridPane.getStyleClass().add("grid-pane");
            int startIndex = pageIndex * columns * rows;
            int endIndex = Math.min(startIndex + columns * rows, items.size());
            for (int i = startIndex; i < endIndex; i++) {
                int row = (i - startIndex) / columns;
                int column = (i - startIndex) % columns;
                T model = items.get(i);
                    CellView<T> cellView = getCellViewProvider().call(model);
                    cellView.sizeProperty().bind(sizeProperty());
                    cellView.selectedProperty().addListener((ob, ov, nv) ->{
                        if (nv) {
                            if (lasetSelectedCellView != null) {
                                lasetSelectedCellView.setSelected(false);
                            }
                            lasetSelectedCellView = cellView;
                            if (currentDetailView != null) {
                                gridPane.getChildren().remove(currentDetailView);
                                currentDetailView = null;
                            }
                            if (getDetailNodeProvider() != null) {
                                currentDetailView = getDetailNodeProvider().call(model);
                                currentDetailView.sizeProperty().bind(sizeProperty());
                                currentDetailView.getStyleClass().add("detail-node");
                                gridPane.add(currentDetailView, 0, row * 2 + 1, columns, 1);
                            }
                        } else {
                            lasetSelectedCellView = null;
                            if (currentDetailView != null) {
                                gridPane.getChildren().remove(currentDetailView);
                                currentDetailView = null;
                            }
                        }

                    });
                    gridPane.add(cellView, column, row * 2);

            }
            return gridPane;
        });
        getChildren().setAll(paginationControl);
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

    private final ObjectProperty<Callback<T, DetailView<T>>> detailNodeProvider = new SimpleObjectProperty<>(this, "detailNodeProvider");

    public Callback<T, DetailView<T>> getDetailNodeProvider() {
        return detailNodeProvider.get();
    }

    public ObjectProperty<Callback<T, DetailView<T>>> detailNodeProviderProperty() {
        return detailNodeProvider;
    }

    public void setDetailNodeProvider(Callback<T, DetailView<T>> detailNodeProvider) {
        this.detailNodeProvider.set(detailNodeProvider);
    }

    private final ObjectProperty<Callback<T, CellView<T>>> cellViewProvider = new SimpleObjectProperty<>(this, "cellViewProvider");

    public Callback<T, CellView<T>> getCellViewProvider() {
        return cellViewProvider.get();
    }

    public ObjectProperty<Callback<T, CellView<T>>> cellViewProviderProperty() {
        return cellViewProvider;
    }

    public void setCellViewProvider(Callback<T, CellView<T>> cellViewProvider) {
        this.cellViewProvider.set(cellViewProvider);
    }
}
