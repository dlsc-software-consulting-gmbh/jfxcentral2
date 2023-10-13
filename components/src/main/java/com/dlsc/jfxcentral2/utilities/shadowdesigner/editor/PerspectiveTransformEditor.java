package com.dlsc.jfxcentral2.utilities.shadowdesigner.editor;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.effect.PerspectiveTransform;

import java.util.List;

public class PerspectiveTransformEditor extends EffectEditor<PerspectiveTransform> {

    public PerspectiveTransformEditor(PerspectiveTransform effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(PerspectiveTransform effect) {
        return List.of(
                EffectPropertyFactory.create("Llx", IkonUtil.llx, -10, 10, 0, effect.llxProperty()),
                EffectPropertyFactory.create("Lly", IkonUtil.lly, -10, 10, 0, effect.llyProperty()),
                EffectPropertyFactory.create("Lrx", IkonUtil.lrx, -10, 10, 0, effect.lrxProperty()),
                EffectPropertyFactory.create("Lry", IkonUtil.lry, -10, 10, 0, effect.lryProperty()),
                EffectPropertyFactory.create("Ulx", IkonUtil.ulx, -10, 10, 0, effect.ulxProperty()),
                EffectPropertyFactory.create("Uly", IkonUtil.uly, -10, 10, 0, effect.ulyProperty()),
                EffectPropertyFactory.create("Urx", IkonUtil.urx, -10, 10, 0, effect.urxProperty()),
                EffectPropertyFactory.create("Ury", IkonUtil.ury, -10, 10, 0, effect.uryProperty()));
    }

}
