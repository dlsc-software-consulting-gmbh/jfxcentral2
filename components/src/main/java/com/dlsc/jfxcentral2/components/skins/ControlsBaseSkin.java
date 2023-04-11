package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.ControlBase;
import javafx.scene.control.SkinBase;

public class ControlsBaseSkin<T extends ControlBase> extends SkinBase<T> {

    protected ControlsBaseSkin(T control) {
        super(control);
    }


}
