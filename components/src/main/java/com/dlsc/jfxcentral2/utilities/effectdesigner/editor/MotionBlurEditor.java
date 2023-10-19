package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.MotionBlur;

import java.util.List;

public class MotionBlurEditor extends EffectEditor<MotionBlur> {

    public MotionBlurEditor(MotionBlur effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(MotionBlur effect) {
        return List.of(
                EffectPropertyFactory.create("Angle", IkonUtil.angle, 0, 360, 0, effect.angleProperty()),
                EffectPropertyFactory.create("Radius", IkonUtil.radius, 0, 63, 10, effect.radiusProperty())
        );
    }
}
