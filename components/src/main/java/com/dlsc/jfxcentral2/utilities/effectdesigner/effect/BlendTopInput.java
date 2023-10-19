package com.dlsc.jfxcentral2.utilities.effectdesigner.effect;

import javafx.scene.effect.Blend;
import javafx.scene.effect.Effect;

public class BlendTopInput extends Blend {
    public BlendTopInput() {
    }

    public void setInput(Effect effect) {
        setTopInput(effect);
    }

    public Effect getInput() {
        return getTopInput();
    }

}
