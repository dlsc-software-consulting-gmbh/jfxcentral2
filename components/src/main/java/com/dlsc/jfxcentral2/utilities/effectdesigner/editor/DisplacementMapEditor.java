package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.DisplacementMap;

import java.util.List;

public class DisplacementMapEditor extends EffectEditor<DisplacementMap> {
    /**
     * This value can be freely adjusted, but please be mindful that if the range is unreasonable,
     * the affected area may be too large or too small.
     */
    private static final int MAX_VAL = 300;
    public DisplacementMapEditor(DisplacementMap effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(DisplacementMap effect) {
        return List.of(
                EffectPropertyFactory.create("OffsetX", IkonUtil.offsetX, -MAX_VAL, MAX_VAL, 0, effect.offsetXProperty()),
                EffectPropertyFactory.create("OffsetY", IkonUtil.offsetY, -MAX_VAL, MAX_VAL, 0, effect.offsetYProperty()),
                EffectPropertyFactory.create("ScaleX", IkonUtil.scaleX, -MAX_VAL, MAX_VAL, 1.0, effect.scaleXProperty()),
                EffectPropertyFactory.create("ScaleY", IkonUtil.scaleY, -MAX_VAL, MAX_VAL, 1.0, effect.scaleYProperty()),
                EffectPropertyFactory.create("Wrap", IkonUtil.wrap, effect.wrapProperty())
        );
    }
}
