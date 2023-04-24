package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.NodeUtil;
import javafx.scene.control.Control;

public class ControlBase extends Control implements Targetable, Sizeable {
    public ControlBase() {
        NodeUtil.initSizeAndTargetPseudoClass(this);
    }

}
