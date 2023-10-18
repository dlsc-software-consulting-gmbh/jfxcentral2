package com.dlsc.jfxcentral2.utilities.shadowdesigner.editor;

import javafx.scene.effect.Lighting;
import org.kordamp.ikonli.material2.Material2SharpAL;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.octicons.Octicons;
import org.kordamp.ikonli.unicons.UniconsLine;

import java.util.List;

public class LightingEditor extends EffectEditor<Lighting> {

    public LightingEditor(Lighting effect, Runnable deleteAction) {
        super(effect, deleteAction);
    }

    @Override
    protected List<EffectPropperty> createOptions(Lighting effect) {
        return List.of(
                EffectPropertyFactory.create("Diffuse\nConstant", MaterialDesign.MDI_TEXTURE, 0, 2, 1, effect.diffuseConstantProperty()),
                EffectPropertyFactory.create("Specular\nConstant", Octicons.MIRROR_24, 0, 2, 1, effect.specularConstantProperty()),
                EffectPropertyFactory.create("Specular\nExponent", Material2SharpAL.CENTER_FOCUS_WEAK, 0, 40, 20, effect.specularExponentProperty()),
                EffectPropertyFactory.create("Surface\nScale", UniconsLine.CIRCLE_LAYER, 0, 10, 1, effect.surfaceScaleProperty()),
                EffectPropertyFactory.createLightEditor(effect)
        );
    }
}
