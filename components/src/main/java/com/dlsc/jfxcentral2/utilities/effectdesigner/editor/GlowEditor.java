package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.Glow;

import java.util.List;

public class GlowEditor extends EffectEditor<Glow> {

    public GlowEditor(Glow effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(Glow effect) {
        return List.of(
                EffectPropertyFactory.create("Level", IkonUtil.level, 0, 1, 0.3, effect.levelProperty())
        );
    }
}
