package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.DisplacementMap;

import java.util.List;

public class DisplacementMapEditor extends EffectEditor<DisplacementMap> {

    public DisplacementMapEditor(DisplacementMap effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(DisplacementMap effect) {
        return List.of(
                EffectPropertyFactory.create("OffsetX", IkonUtil.offsetX, -10, 10, 0, effect.offsetXProperty()),
                EffectPropertyFactory.create("OffsetY", IkonUtil.offsetY, -10, 10, 0, effect.offsetYProperty()),
                EffectPropertyFactory.create("ScaleX", IkonUtil.scaleX, -10, 10, 1.0, effect.scaleXProperty()),
                EffectPropertyFactory.create("ScaleY", IkonUtil.scaleY, -10, 10, 1.0, effect.scaleYProperty()),
                EffectPropertyFactory.create("Wrap", IkonUtil.wrap, effect.wrapProperty())
        );
    }
}
