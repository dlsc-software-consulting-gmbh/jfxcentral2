package com.dlsc.jfxcentral2.mobile.pages;

import com.dlsc.jfxcentral2.utils.skin.SkinnableControl;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.mobile.skin.MainPageSkin;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import com.dlsc.jfxcentral2.utils.skin.Skin;

public class MainPage extends SkinnableControl {

    private static final String DEFAULT_STYLE_CLASS = "main-page";

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public MainPage() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new MainPageSkin(this);
    }

}
