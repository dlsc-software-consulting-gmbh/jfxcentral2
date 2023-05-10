package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.app.pages.PageBase;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;

public abstract class DetailsPageBase<T extends ModelObject> extends PageBase {

    public DetailsPageBase(ObjectProperty<Size> size, Class<? extends T> clazz, String itemId) {
        super(size, TopMenuBar.Mode.DARK);
        setItem((T) DataRepository.getInstance().getByID(clazz, itemId));
    }

    @Override
    public String title() {
        return "JFXCentral - " + getItem().getName();
    }

    @Override
    public String description() {
        String text = getItem().getDescription();

        // TODO: replace with StringUtils
        if (text != null && !text.trim().equals("")) {
            return text;
        }

        return "";
    }

    @Override
    public Node content() {
        return new Label(getItem().getName());
    }

    private final ObjectProperty<T> item = new SimpleObjectProperty<>(this, "item");

    public ModelObject getItem() {
        return item.get();
    }

    public ObjectProperty<T> itemProperty() {
        return item;
    }

    public void setItem(T item) {
        this.item.set(item);
    }
}
