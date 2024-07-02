package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.CategoryContentPane;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.gridview.ModelGridView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.mobile.componenets.MobileCategoryHeader;
import com.dlsc.jfxcentral2.mobile.componenets.SearchTextView;
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
import org.apache.commons.lang3.StringUtils;
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
        MobileCategoryHeader header = createCategoryHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(getCategoryTitle());
        header.setIcon(getCategoryIkon());

        // filter
        SearchTextView searchTextField = new SearchTextView();
        searchTextField.setPromptText(getSearchPrompText());
        blockingProperty().bind(searchTextField.blockingProperty());

        // data
        ObservableList<T> itemsList = getCategoryItems();

        FilteredList<T> filteredList = new FilteredList<>(itemsList);
        searchTextField.searchTextProperty().addListener((obs, oldText, newText) -> {
            String searchText = searchTextField.searchTextProperty().getValueSafe().toLowerCase().trim();
            if (StringUtils.isBlank(searchText)) {
                filteredList.setPredicate(null);
            } else {
                filteredList.setPredicate(item -> StringUtils.containsIgnoreCase(item.getName(), searchText)
                        || StringUtils.containsIgnoreCase(item.getDescription(), searchText));
            }
        });

        SortedList<T> sortedList = new SortedList<>(filteredList);

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
        contentPane.getNodes().addAll(searchTextField, stripView);

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
        return 5;
    }

    protected abstract String getCategoryTitle();

    protected abstract Ikon getCategoryIkon();

    protected abstract ObservableList<T> getCategoryItems();

    protected abstract Callback<T, TileViewBase<T>> getTileViewProvider();

    protected Callback<T, Node> getDetailNodeProvider() {
        return null;
    }

    protected MobileCategoryHeader createCategoryHeader() {
        return new MobileCategoryHeader();
    }

    protected CategoryContentPane createCategoryContentPane(Node... nodes) {
        return new CategoryContentPane(nodes);
    }

    protected String getSearchPrompText() {
        return "Search...";
    }

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
