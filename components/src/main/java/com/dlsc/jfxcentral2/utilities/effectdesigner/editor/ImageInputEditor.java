package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.ImageInput;

import java.util.List;

public class ImageInputEditor extends EffectEditor<ImageInput> {
    /**
     * This value can be freely adjusted, but please be mindful that if the range is unreasonable,
     * the affected area may be too large or too small.
     */
    private static final int MAX_VAL = 300;
    public ImageInputEditor(ImageInput effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(ImageInput effect) {
        return List.of(
                EffectPropertyFactory.create("X", IkonUtil.x, -MAX_VAL, MAX_VAL, 0, effect.xProperty()),
                EffectPropertyFactory.create("Y", IkonUtil.y, -MAX_VAL, MAX_VAL, 0, effect.yProperty()),
                EffectPropertyFactory.create("Image", IkonUtil.image, effect.sourceProperty())
        );
    }
}
