package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.PaneBase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.util.Objects;

public class TileViewBase<T extends ModelObject> extends PaneBase {

    private final T item;

    public TileViewBase(T item) {
        this.item = Objects.requireNonNull(item, "item can not be null");
        getStyleClass().add("tile-view-base");
    }

    private final ObjectProperty<Runnable> onShowDetailNode = new SimpleObjectProperty<>(this, "onShowDetailNode");

    public Runnable getOnShowDetailNode() {
        return onShowDetailNode.get();
    }

    public ObjectProperty<Runnable> onShowDetailNodeProperty() {
        return onShowDetailNode;
    }

    public void setOnShowDetailNode(Runnable onShowDetailNode) {
        this.onShowDetailNode.set(onShowDetailNode);
    }

    public T getData() {
        return item;
    }

    private final StringProperty description = new SimpleStringProperty(this, "description");

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    private final StringProperty title = new SimpleStringProperty(this, "title");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    private final ObjectProperty<Image> image = new SimpleObjectProperty<>(this, "image");

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    private final BooleanProperty saveSelected = new SimpleBooleanProperty(this, "saveSelected", false);

    public final boolean getSaveSelected() {
        return saveSelected.get();
    }

    public final BooleanProperty saveSelectedProperty() {
        return saveSelected;
    }

    public final void setSaveSelected(boolean saveSelected) {
        this.saveSelected.set(saveSelected);
    }

    private final BooleanProperty likeSelected = new SimpleBooleanProperty(this, "likeSelected", false);

    public final boolean getLikeSelected() {
        return likeSelected.get();
    }

    public final BooleanProperty likeSelectedProperty() {
        return likeSelected;
    }

    public final void setLikeSelected(boolean likeSelected) {
        this.likeSelected.set(likeSelected);
    }
}