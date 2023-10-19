package com.dlsc.jfxcentral2.utilities.effectdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.ColorInput;

import java.util.List;

public class ColorInputEditor extends EffectEditor<ColorInput> {

    public ColorInputEditor(ColorInput effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(ColorInput effect) {
        return List.of(
                EffectPropertyFactory.create("X", IkonUtil.x, -10, 10, 0, effect.xProperty()),
                EffectPropertyFactory.create("Y", IkonUtil.y, -10, 10, 0, effect.yProperty()),
                EffectPropertyFactory.create("Width", IkonUtil.width, 0, 255, 0, effect.widthProperty()),
                EffectPropertyFactory.create("Height", IkonUtil.height, 0, 255, 0, effect.heightProperty()),
                EffectPropertyFactory.createWithPaintPicker("Paint", IkonUtil.color, effect.paintProperty())
        );
    }
}
