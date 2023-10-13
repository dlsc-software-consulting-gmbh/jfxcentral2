package com.dlsc.jfxcentral2.utilities.shadowdesigner.editor;

import javafx.scene.effect.Lighting;

import java.util.List;

public class LightingBumpInputEditor extends EffectEditor<Lighting> {

    public LightingBumpInputEditor(Lighting effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(Lighting effect) {
        return List.of(
                EffectPropertyFactory.create("Diffuse Constant", null, 0, 2, 1, effect.diffuseConstantProperty()),
                EffectPropertyFactory.create("Specular Constant", null, 0, 2, 1, effect.specularConstantProperty()),
                EffectPropertyFactory.create("Specular Exponent", null, 0, 40, 20, effect.specularExponentProperty()),
                EffectPropertyFactory.create("Surface Scale", null, 0, 10, 1, effect.surfaceScaleProperty())

        );
    }
}
