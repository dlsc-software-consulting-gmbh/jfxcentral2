package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.GaussianBlur;

import java.util.List;

public class GaussianBlurEditor extends EffectEditor<GaussianBlur> {

    public GaussianBlurEditor(GaussianBlur effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(GaussianBlur effect) {
        return List.of(
                EffectPropertyFactory.create("Radius", IkonUtil.radius, 0, 63, 10, effect.radiusProperty())
        );
    }
}
