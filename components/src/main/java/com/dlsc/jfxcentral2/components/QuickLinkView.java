package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.QuickLinkViewSkin;
import com.dlsc.jfxcentral2.model.QuickLink;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import one.jpro.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;

public class QuickLinkView extends ControlBase {

    public QuickLinkView(QuickLink quickLink) {
        getStyleClass().add("quick-link-view");
        setQuickLink(quickLink);
        if (quickLink != null && StringUtils.isNotBlank(quickLink.getLinkUrl())) {
            LinkUtil.setLink(this, quickLink.getLinkUrl());
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new QuickLinkViewSkin(this);
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
