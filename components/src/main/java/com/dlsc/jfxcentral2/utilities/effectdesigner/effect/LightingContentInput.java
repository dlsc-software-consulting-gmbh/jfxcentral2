package com.dlsc.jfxcentral2.utilities.effectdesigner.effect;

import javafx.scene.effect.Effect;
import javafx.scene.effect.Lighting;

public class LightingContentInput extends Lighting {

    public LightingContentInput() {

    }

    public void setInput(Effect effect) {
        setContentInput(effect);
    }

    public Effect getInput() {
        return getContentInput();
    }
}
