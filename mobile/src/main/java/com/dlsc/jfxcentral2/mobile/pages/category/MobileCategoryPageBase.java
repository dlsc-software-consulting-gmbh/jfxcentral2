package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.CategoryContentPane;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.gridview.ModelGridView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.OSUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

import java.util.List;

public abstract class MobileCategoryPageBase<T extends ModelObject> extends VBox {

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public MobileCategoryPageBase(ObjectProperty<Size> size) {
        sizeProperty().bind(size);
        getChildren().setAll(content());
    }

    public List<Node> content() {
        // header
        CategoryHeader header = createCategoryHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(getCategoryTitle());
        header.setIkon(getCategoryIkon());
        header.setDescription(getCategoryDescription());

        // filter
        SearchFilterView<T> filterView = createSearchFilterView();
        filterView.sizeProperty().bind(sizeProperty());
        blockingProperty().bind(filterView.blockingProperty());

        // data
        ObservableList<T> itemsList = getCategoryItems();

        FilteredList<T> filteredList = new FilteredList<>(itemsList);
        filteredList.predicateProperty().bind(filterView.predicateProperty());

        SortedList<T> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(filterView.comparatorProperty());

        // grid view
        ModelGridView<T> gridView = createGridView();
        gridView.sizeProperty().bind(sizeProperty());
        gridView.setTileViewProvider(getTileViewProvider());
        if (!OSUtil.isNative()) {
            gridView.setDetailNodeProvider(getDetailNodeProvider());
        }
        gridView.setColumns(getNumberOfGridViewColumns());
        gridView.rowsProperty().bind(gridView.sizeProperty().map(size -> size == Size.SMALL ? 10 : getNumberOfGridViewRows()));
        gridView.setItems(sortedList);

        // wrap grid view in a strip view
        StripView stripView = new StripView(gridView);
        stripView.sizeProperty().bind(sizeProperty());

        // details
        CategoryContentPane contentPane = createCategoryContentPane();
        contentPane.sizeProperty().bind(sizeProperty());
        contentPane.getNodes().addAll(filterView, stripView);

        VBox.setVgrow(contentPane, Priority.ALWAYS);
        return List.of(header, contentPane);
    }

    protected String getCategoryDescription() {
        return null;
    }

    protected int getNumberOfGridViewColumns() {
        return 3;
    }

    protected int getNumberOfGridViewRows() {
        return 3;
    }

    protected abstract String getCategoryTitle();

    protected abstract Ikon getCategoryIkon();

    protected abstract ObservableList<T> getCategoryItems();

    protected abstract Callback<T, TileViewBase<T>> getTileViewProvider();

    protected Callback<T, Node> getDetailNodeProvider() {
        return null;
    }

    protected CategoryHeader createCategoryHeader() {
        return new CategoryHeader();
    }

    protected CategoryContentPane createCategoryContentPane(Node... nodes) {
        return new CategoryContentPane(nodes);
    }

    protected abstract SearchFilterView<T> createSearchFilterView();

    protected ModelGridView<T> createGridView() {
        return new ModelGridView<>();
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

    // blocking property to control the glass pane

    private final BooleanProperty blocking = new SimpleBooleanProperty(this, "blocking");

    public boolean isBlocking() {
        return blocking.get();
    }

    public BooleanProperty blockingProperty() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking.set(blocking);
    }
}
