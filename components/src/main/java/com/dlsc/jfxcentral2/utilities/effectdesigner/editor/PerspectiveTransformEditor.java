package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.PerspectiveTransform;

import java.util.List;

public class PerspectiveTransformEditor extends EffectEditor<PerspectiveTransform> {
    /**
     * This value can be freely adjusted, but please be mindful that if the range is unreasonable,
     * the affected area may be too large or too small.
     */
    private static final int MAX_VAL = 600;

    public PerspectiveTransformEditor(PerspectiveTransform effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(PerspectiveTransform effect) {
        return List.of(
                EffectPropertyFactory.create("Llx", IkonUtil.llx, -MAX_VAL, MAX_VAL, 0, effect.llxProperty()),
                EffectPropertyFactory.create("Lly", IkonUtil.lly, -MAX_VAL, MAX_VAL, 0, effect.llyProperty()),
                EffectPropertyFactory.create("Lrx", IkonUtil.lrx, -MAX_VAL, MAX_VAL, 0, effect.lrxProperty()),
                EffectPropertyFactory.create("Lry", IkonUtil.lry, -MAX_VAL, MAX_VAL, 0, effect.lryProperty()),
                EffectPropertyFactory.create("Ulx", IkonUtil.ulx, -MAX_VAL, MAX_VAL, 0, effect.ulxProperty()),
                EffectPropertyFactory.create("Uly", IkonUtil.uly, -MAX_VAL, MAX_VAL, 0, effect.ulyProperty()),
                EffectPropertyFactory.create("Urx", IkonUtil.urx, -MAX_VAL, MAX_VAL, 0, effect.urxProperty()),
                EffectPropertyFactory.create("Ury", IkonUtil.ury, -MAX_VAL, MAX_VAL, 0, effect.uryProperty()));
    }

}
