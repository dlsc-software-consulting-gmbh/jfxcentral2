package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.Reflection;

import java.util.List;

public class ReflectionEditor extends EffectEditor<Reflection> {
    /**
     * This value can be freely adjusted, but please be mindful that if the range is unreasonable,
     * the affected area may be too large or too small.
     */
    private static final int MAX_VAL = 300;
    public ReflectionEditor(Reflection effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(Reflection effect) {
        return List.of(
                EffectPropertyFactory.create("Fraction", IkonUtil.fraction, 0, 1, 0.75, effect.fractionProperty()),
                EffectPropertyFactory.create("Top Offset", IkonUtil.topOffset, -MAX_VAL, MAX_VAL, 0, effect.topOffsetProperty()),
                EffectPropertyFactory.create("Top Opacity", IkonUtil.opacity, 0, 1, 0.5, effect.topOpacityProperty()),
                EffectPropertyFactory.create("Bottom Opacity", IkonUtil.opacity, 0, 1, 0, effect.bottomOpacityProperty())
        );
    }
}
