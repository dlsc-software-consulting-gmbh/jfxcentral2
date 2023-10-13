package com.dlsc.jfxcentral2.utilities.shadowdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.ImageInput;

import java.util.List;

public class ImageInputEditor extends EffectEditor<ImageInput> {

    public ImageInputEditor(ImageInput effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(ImageInput effect) {
        return List.of(
                EffectPropertyFactory.create("X", IkonUtil.x, -10, 10, 0, effect.xProperty()),
                EffectPropertyFactory.create("Y", IkonUtil.y, -10, 10, 0, effect.yProperty()),
                EffectPropertyFactory.create("Image", IkonUtil.image, effect.sourceProperty())
        );
    }
}
