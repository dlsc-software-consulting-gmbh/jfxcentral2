package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.NodeUtil;
import javafx.scene.layout.StackPane;

public abstract class PaneBase extends StackPane implements Targetable, Sizeable {

    public PaneBase() {
        NodeUtil.initSizeAndTargetPseudoClass(this,null, this::layoutBySize);
    }

    protected abstract void layoutBySize();

}
