package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.model.details.AppsDetailsObject;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class DetailsPageBase<T extends ModelObject> extends PageBase {

    private final int itemId;

    public DetailsPageBase(ObjectProperty<Size> size, TopMenuBar.Mode menuMode, int itemId) {
        super(size, menuMode);
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    private final ListProperty<AppsDetailsObject> apps = new SimpleListProperty<>(this, "apps", FXCollections.observableArrayList());

    public ObservableList<AppsDetailsObject> getApps() {
        return apps.get();
    }

    public ListProperty<AppsDetailsObject> appsProperty() {
        return apps;
    }

    public void setApps(ObservableList<AppsDetailsObject> apps) {
        this.apps.set(apps);
    }
}
