package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.ControlBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.control.SkinBase;

public class ControlBaseSkin<T extends ControlBase> extends SkinBase<T> {
    private Size oldSize;
    private Size newSize;
    protected ControlBaseSkin(T control) {
        super(control);
        newSize = control.getSize();
        control.sizeProperty().addListener((observable, oldValue, newValue) -> {
            oldSize = oldValue;
            newSize = newValue;
            layoutBySize();
        });
    }
    public Size getOldSize() {
        return oldSize;
    }

    public Size getNewSize() {
        return newSize;
    }

    public boolean isLgToMdOrMdToLg() {
        if (oldSize == null || newSize == null) {
            return false;
        }
        return (oldSize.isLarge() && newSize.isMedium()) || (oldSize.isMedium() && newSize.isLarge());
    }

    public boolean isSmToMdOrMdToSm() {
        if (oldSize == null || newSize == null) {
            return false;
        }
        return (oldSize.isSmall() && newSize.isMedium()) || (oldSize.isMedium() && newSize.isSmall());
    }

    protected void layoutBySize() {
    }

    public boolean isSmall() {
        return getSkinnable().getSize().isSmall();
    }

    public boolean isMedium() {
        return getSkinnable().getSize().isMedium();
    }

    public boolean isLarge() {
        return getSkinnable().getSize().isLarge();
    }

    public boolean isDesktop() {
        return getSkinnable().getTarget().isDesktop();
    }

    public boolean isBrowser() {
        return getSkinnable().getTarget().isBrowser();
    }

    public boolean isMobile() {
        return getSkinnable().getTarget().isMobile();
    }

}
