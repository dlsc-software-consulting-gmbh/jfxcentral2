package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.ControlBase;
import javafx.scene.control.SkinBase;

public class ControlBaseSkin<T extends ControlBase> extends SkinBase<T> {

    protected ControlBaseSkin(T control) {
        super(control);
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

    public boolean isComputer() {
        return getSkinnable().getTarget().isComputer();
    }

    public boolean isEmbedded() {
        return getSkinnable().getTarget().isEmbedded();
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

    public boolean isTablet() {
        return getSkinnable().getTarget().isTablet();
    }

}
