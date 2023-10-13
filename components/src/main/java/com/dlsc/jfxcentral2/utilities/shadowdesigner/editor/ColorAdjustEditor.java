package com.dlsc.jfxcentral2.utilities.shadowdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.ColorAdjust;

import java.util.List;

public class ColorAdjustEditor extends EffectEditor<ColorAdjust> {

    public ColorAdjustEditor(ColorAdjust effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(ColorAdjust effect) {
        return List.of(
                EffectPropertyFactory.create("Hue", IkonUtil.hue, -1, 1, 0, effect.hueProperty()),
                EffectPropertyFactory.create("Saturation", IkonUtil.saturation, -1, 1, 0, effect.saturationProperty()),
                EffectPropertyFactory.create("Brightness", IkonUtil.brightness, -1, 1, 0, effect.brightnessProperty()),
                EffectPropertyFactory.create("Contrast", IkonUtil.contrast, -1, 1, 0, effect.contrastProperty()));
    }
}
