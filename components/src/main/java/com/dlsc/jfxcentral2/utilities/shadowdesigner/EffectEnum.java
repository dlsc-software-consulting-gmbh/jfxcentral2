package com.dlsc.jfxcentral2.utilities.shadowdesigner;

import com.dlsc.jfxcentral2.utilities.shadowdesigner.effect.BlendBottomInput;
import com.dlsc.jfxcentral2.utilities.shadowdesigner.effect.BlendTopInput;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.ImageInput;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum EffectEnum {
    //BLEND("Blend", Blend.class),
    BLEND_TOP_INPUT("Blend (TopInPut)", BlendTopInput.class),
    BLEND_BOTTOM_INPUT("Blend (BottomInput)", BlendBottomInput.class),
    BLOOM("Bloom", Bloom.class),
    BOX_BLUR("BoxBlur", BoxBlur.class),
    COLOR_ADJUST("ColorAdjust", ColorAdjust.class),
    COLOR_INPUT("ColorInput", ColorInput.class),
    DISPLACEMENT_MAP("DisplacementMap", DisplacementMap.class),
    DROP_SHADOW("DropShadow", DropShadow.class),
    GAUSSIAN_BLUR("GaussianBlur", GaussianBlur.class),
    GLOW("Glow", Glow.class),
    IMAGE_INPUT("ImageInput", ImageInput.class),
    INNER_SHADOW("InnerShadow", InnerShadow.class),
    //LIGHTING("Lighting", Lighting.class),
    //LIGHTING_BUMP_INPUT("Lighting (BumpInput)", LightingBumpInput.class),
    //LIGHTING_CONTENT_INPUT("Lighting (ContentInput)", LightingContentInput.class),
    MOTION_BLUR("MotionBlur", MotionBlur.class),
    PERSPECTIVE_TRANSFORM("PerspectiveTransform", PerspectiveTransform.class),
    REFLECTION("Reflection", Reflection.class),
    SEPIA_TONE("SepiaTone", SepiaTone.class),
    SHADOW("Shadow", Shadow.class);

    private static final Logger LOGGER = LogManager.getLogger(EffectEnum.class);
    private final String name;
    private final Class<? extends Effect> effectClass;

    EffectEnum(String name, Class<? extends Effect> effectClass) {
        this.name = name;
        this.effectClass = effectClass;
    }

    public String getName() {
        return name;
    }

    public Class<? extends Effect> getEffectClass() {
        return effectClass;
    }

    public static Class<? extends Effect>[] getAllEffectClass() {
        EffectEnum[] values = EffectEnum.values();
        Class<? extends Effect>[] classes = new Class[values.length];
        for (int i = 0; i < values.length; i++) {
            classes[i] = values[i].getEffectClass();
        }
        return classes;
    }

    public static String[] getAllNames() {
        EffectEnum[] values = EffectEnum.values();
        String[] names = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            names[i] = values[i].getName();
        }
        return names;
    }

    public static EffectEnum findByName(String name) {
        EffectEnum[] values = EffectEnum.values();
        for (EffectEnum value : values) {
            if (StringUtils.equalsIgnoreCase(value.getName(), name)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
