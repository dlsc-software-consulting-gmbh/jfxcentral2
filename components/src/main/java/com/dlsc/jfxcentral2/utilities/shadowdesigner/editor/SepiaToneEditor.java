package com.dlsc.jfxcentral2.utilities.shadowdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.SepiaTone;

import java.util.List;

public class SepiaToneEditor extends EffectEditor<SepiaTone> {

    public SepiaToneEditor(SepiaTone effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(SepiaTone effect) {
        return List.of(
                EffectPropertyFactory.create("Level", IkonUtil.level, 0, 1, 1, effect.levelProperty())
        );
    }
}
