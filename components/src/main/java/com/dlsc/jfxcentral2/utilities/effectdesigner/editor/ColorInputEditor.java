package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.ColorInput;

import java.util.List;

public class ColorInputEditor extends EffectEditor<ColorInput> {
    /**
     * This value can be freely adjusted, but please be mindful that if the range is unreasonable,
     * the affected area may be too large or too small.
     */
    private static final int MAX_VAL = 600;

    public ColorInputEditor(ColorInput effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(ColorInput effect) {
        return List.of(
                EffectPropertyFactory.create("X", IkonUtil.x, -MAX_VAL, MAX_VAL, 0, effect.xProperty()),
                EffectPropertyFactory.create("Y", IkonUtil.y, -MAX_VAL, MAX_VAL, 0, effect.yProperty()),
                EffectPropertyFactory.create("Width", IkonUtil.width, 0, MAX_VAL, 0, effect.widthProperty()),
                EffectPropertyFactory.create("Height", IkonUtil.height, 0, MAX_VAL, 0, effect.heightProperty()),
                EffectPropertyFactory.createWithPaintPicker("Paint", IkonUtil.color, effect.paintProperty())
        );
    }
}
