package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.mobile.components.MobileCategoryHeader;
import com.dlsc.jfxcentral2.mobile.components.ModelListCell;
import com.dlsc.jfxcentral2.mobile.components.ModelListView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;

import java.util.List;
import java.util.function.Predicate;

public abstract class MobileCategoryPageBase<T extends ModelObject> extends VBox {

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public MobileCategoryPageBase(ObjectProperty<Size> size) {
        sizeProperty().bind(size);
        getChildren().setAll(content());
        // swipe right to go back to home
        // setOnSwipeRight(evt-> MobileLinkUtil.getToPage(PagePath.HOME));
    }

    public List<Node> content() {
        // header
        MobileCategoryHeader header = createCategoryHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(getCategoryTitle());
        header.setIcon(getCategoryIkon());

        // list view
        ModelListView<T> listView = new ModelListView<>();
        listView.getItemsList().setAll(getCategoryItems());
        listView.promptTextProperty().set(getSearchPromptText());
        listView.setFilter(getFilter());
        listView.setMaxHeight(Double.MAX_VALUE);
        listView.setCellFactory(cellFactory());
        VBox.setVgrow(listView, Priority.ALWAYS);

        return List.of(header, listView);
    }

    protected Callback<String, Predicate<T>> getFilter(){
        return text -> model -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(model.getName(), text)
                || StringUtils.containsIgnoreCase(model.getDescription(), text);
    }

    protected Callback<ListView<T>, ListCell<T>> cellFactory() {
        return param -> new ModelListCell<>();
    }

    protected abstract String getCategoryTitle();

    protected abstract Ikon getCategoryIkon();

    protected abstract ObservableList<T> getCategoryItems();

    protected MobileCategoryHeader createCategoryHeader() {
        return new MobileCategoryHeader();
    }

    protected String getSearchPromptText() {
        return "Search...";
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
