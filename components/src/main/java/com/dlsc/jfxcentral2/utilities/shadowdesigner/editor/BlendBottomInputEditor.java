package com.dlsc.jfxcentral2.utilities.shadowdesigner.editor;

import com.dlsc.jfxcentral2.utilities.shadowdesigner.effect.BlendBottomInput;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.BlendMode;

import java.util.List;

public class BlendBottomInputEditor extends EffectEditor<BlendBottomInput> {

    public BlendBottomInputEditor(BlendBottomInput effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(BlendBottomInput effect) {
        return List.of(
                EffectPropertyFactory.create("Mode", IkonUtil.blendMode, BlendMode.class, null, effect.modeProperty()),
                EffectPropertyFactory.create("Opacity", IkonUtil.opacity, 0, 1, 1, effect.opacityProperty())
        );
    }
}
