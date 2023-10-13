package com.dlsc.jfxcentral2.utilities.shadowdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Shadow;

import java.util.List;

public class ShadowEditor extends EffectEditor<Shadow> {

    public ShadowEditor(Shadow effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(Shadow effect) {
        return List.of(
                EffectPropertyFactory.create("BlurType", IkonUtil.blur, BlurType.class, null, effect.blurTypeProperty()),
                EffectPropertyFactory.create("Width", IkonUtil.width, 0, 255, 21, effect.widthProperty()),
                EffectPropertyFactory.create("Height", IkonUtil.height, 0, 255, 21, effect.heightProperty()),
                EffectPropertyFactory.create("Radius", IkonUtil.radius, 0, 127, 10, effect.radiusProperty()),
                EffectPropertyFactory.createWithColorPicker("Color", IkonUtil.color, effect.colorProperty())
        );
    }
}
