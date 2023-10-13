package com.dlsc.jfxcentral2.utilities.shadowdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.BoxBlur;

import java.util.List;

public class BoxBlurEditor extends EffectEditor<BoxBlur> {
    public BoxBlurEditor(BoxBlur effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(BoxBlur effect) {
        return List.of(
                EffectPropertyFactory.create("Width", IkonUtil.width, 0, 255, 5, effect.widthProperty()),
                EffectPropertyFactory.create("Height", IkonUtil.height, 0, 255, 5, effect.heightProperty()),
                EffectPropertyFactory.create("Iterations", IkonUtil.iterations, 0, 3, 1, effect.iterationsProperty()));
    }

}
