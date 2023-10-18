package com.dlsc.jfxcentral2.utilities.shadowdesigner.effect;

import javafx.scene.effect.Blend;
import javafx.scene.effect.Effect;

public class BlendBottomInput extends Blend {
    public BlendBottomInput() {

    }

    public void setInput(Effect effect) {
        setBottomInput(effect);
    }

    public Effect getInput() {
        return getBottomInput();
    }

}
