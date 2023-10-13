package com.dlsc.jfxcentral2.utilities.shadowdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;

import java.util.List;

public class InnerShadowEditor extends EffectEditor<InnerShadow> {
    public InnerShadowEditor(InnerShadow innerShadow, Runnable deleteAction) {
        super(innerShadow, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(InnerShadow effect) {
        return List.of(
                EffectPropertyFactory.create("BlurType", IkonUtil.blur, BlurType.class, null, effect.blurTypeProperty()),
                EffectPropertyFactory.create("Width", IkonUtil.width, 0, 255, 21, effect.widthProperty()),
                EffectPropertyFactory.create("Height", IkonUtil.height, 0, 255, 21, effect.heightProperty()),
                EffectPropertyFactory.create("Radius", IkonUtil.radius, 0, 127, 10, effect.radiusProperty()),
                EffectPropertyFactory.create("OffsetX", IkonUtil.offsetX, -10, 10, 0, effect.offsetXProperty()),
                EffectPropertyFactory.create("OffsetY", IkonUtil.offsetY, -10, 10, 0, effect.offsetYProperty()),
                EffectPropertyFactory.create("Choke", IkonUtil.spread, 0, 1, 0, effect.chokeProperty()),
                EffectPropertyFactory.createWithColorPicker("Color", IkonUtil.color, effect.colorProperty())
        );
    }
}
