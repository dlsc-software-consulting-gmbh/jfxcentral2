package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.QuickLinkViewSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Skin;
import org.kordamp.ikonli.Ikon;

public class QuickLinkView extends ControlBase {
    /**
     * If the image link is not empty, then the component serves as an image placeholder;
     * If the image link is empty, the component acts as a regular QuickLinkView component.
     */
    public record QuickLink(String title, String description, Ikon ikon, String url, String imageUrl) {
    }

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
