package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;

import java.util.List;

public class DropShadowEditor extends EffectEditor<DropShadow> {
    /**
     * This value can be freely adjusted, but please be mindful that if the range is unreasonable,
     * the affected area may be too large or too small.
     */
    private static final int MAX_VAL = 300;
    public DropShadowEditor(DropShadow dropShadow, Runnable deleteAction) {
        super(dropShadow, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(DropShadow effect) {
        return List.of(
                EffectPropertyFactory.create("BlurType", IkonUtil.blur, BlurType.class, null, effect.blurTypeProperty()),
                EffectPropertyFactory.create("Width", IkonUtil.width, 0, 255, 21, effect.widthProperty()),
                EffectPropertyFactory.create("Height", IkonUtil.height, 0, 255, 21, effect.heightProperty()),
                EffectPropertyFactory.create("Radius", IkonUtil.radius, 0, 127, 10, effect.radiusProperty()),
                EffectPropertyFactory.create("OffsetX", IkonUtil.offsetX, -MAX_VAL, MAX_VAL, 0, effect.offsetXProperty()),
                EffectPropertyFactory.create("OffsetY", IkonUtil.offsetY, -MAX_VAL, MAX_VAL, 0, effect.offsetYProperty()),
                EffectPropertyFactory.create("Spread", IkonUtil.spread, 0, 1, 0, effect.spreadProperty()),
                EffectPropertyFactory.createWithColorPicker("Color", IkonUtil.color, effect.colorProperty())
        );
    }
}
