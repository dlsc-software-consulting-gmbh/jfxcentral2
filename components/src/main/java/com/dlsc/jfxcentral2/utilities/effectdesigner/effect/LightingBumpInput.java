package com.dlsc.jfxcentral2.utilities.effectdesigner.effect;

import javafx.scene.effect.Effect;
import javafx.scene.effect.Lighting;

public class LightingBumpInput extends Lighting {

    public LightingBumpInput() {
    }

    public void setInput(Effect effect) {
        setBumpInput(effect);
    }

    public Effect getInput() {
        return getBumpInput();
    }

}
