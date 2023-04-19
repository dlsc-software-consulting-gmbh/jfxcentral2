package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.QuickLinkViewSkin;
import com.dlsc.jfxcentral2.model.QuickLink;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Skin;

public class QuickLinkView extends ControlBase {

    public QuickLinkView() {
        this(null);
    }

    public QuickLinkView(QuickLink quickLink) {
        getStyleClass().add("quick-link-view");
        setQuickLink(quickLink);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new QuickLinkViewSkin(this);
    }

    private final ObjectProperty<QuickLink> quickLink = new SimpleObjectProperty<>(this, "quickLink");

    public QuickLink getQuickLink() {
        return quickLink.get();
    }

    public ObjectProperty<QuickLink> quickLinkProperty() {
        return quickLink;
    }

    public void setQuickLink(QuickLink quickLink) {
        this.quickLink.set(quickLink);
    }

}
