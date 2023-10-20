package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.Bloom;

import java.util.List;

public class BloomEditor extends EffectEditor<Bloom> {

    public BloomEditor(Bloom effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(Bloom effect) {
        return List.of(
                EffectPropertyFactory.create("Threshold", IkonUtil.threshold, 0, 1, 0.3, effect.thresholdProperty()));
    }
}
