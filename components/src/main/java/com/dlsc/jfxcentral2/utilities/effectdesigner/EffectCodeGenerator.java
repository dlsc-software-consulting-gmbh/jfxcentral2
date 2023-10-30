package com.dlsc.jfxcentral2.utilities.effectdesigner;

import com.dlsc.jfxcentral2.utilities.paintpicker.impl.PaintConvertUtil;
import com.dlsc.jfxcentral2.utilities.effectdesigner.effect.BlendBottomInput;
import com.dlsc.jfxcentral2.utilities.effectdesigner.effect.BlendTopInput;
import com.dlsc.jfxcentral2.utilities.effectdesigner.effect.LightingBumpInput;
import com.dlsc.jfxcentral2.utilities.effectdesigner.effect.LightingContentInput;
import com.dlsc.jfxcentral2.utils.LOGGER;
import com.dlsc.jfxcentral2.utils.NumberUtil;
import com.dlsc.jfxcentral2.utils.StringUtil;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
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
import javafx.scene.effect.Lighting;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class EffectCodeGenerator {

    private EffectCodeGenerator() {
    }

    public static String effectsToJavaCode(List<Effect> effects) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < effects.size(); i++) {
            effectToJavaCode(sb, effects.get(i), i);
        }

        return sb.toString();
    }

    private static void effectToJavaCode(StringBuilder sb, Effect effect, int index) {
        String className = effect.getClass().getSimpleName();
        if (StringUtils.equalsAnyIgnoreCase(className, "BlendTopInput", "BlendBottomInput")) {
            className = "Blend";
        }
        if (StringUtils.equalsAnyIgnoreCase(className, "LightingBumpInput", "LightingContentInput")) {
            className = "Lighting";
        }

        String instanceName = "effect" + index;
        sb.append(className).append(" ").append(instanceName).append(" = new ").append(className).append("();").append(StringUtil.NEW_LINE);

        if (effect instanceof Blend temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof Bloom temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof BoxBlur temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof ColorAdjust temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof ColorInput temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof DisplacementMap temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof DropShadow temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof GaussianBlur temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof Glow temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof ImageInput temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof InnerShadow temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof Lighting temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof MotionBlur temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof PerspectiveTransform temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof Reflection temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof SepiaTone temp) {
            handleEffect(sb, instanceName, temp);
        } else if (effect instanceof Shadow temp) {
            handleEffect(sb, instanceName, temp);
        } else {
            LOGGER.error("Unknown effect {}", effect);
        }

        if (index > 0) {
            String prevEffectName = "effect" + (index - 1);
            if (effect instanceof BlendBottomInput) {
                sb.append(instanceName).append(".setBottomInput(").append(prevEffectName).append(");").append(StringUtil.NEW_LINE);
            } else if (effect instanceof BlendTopInput) {
                sb.append(instanceName).append(".setTopInput(").append(prevEffectName).append(");").append(StringUtil.NEW_LINE);
            } else if (effect instanceof LightingBumpInput) {
                sb.append(instanceName).append(".setBumpInput(").append(prevEffectName).append(");").append(StringUtil.NEW_LINE);
            } else if (effect instanceof LightingContentInput) {
                sb.append(instanceName).append(".setContentInput(").append(prevEffectName).append(");").append(StringUtil.NEW_LINE);
            } else {
                sb.append(instanceName).append(".setInput(").append(prevEffectName).append(");").append(StringUtil.NEW_LINE);
            }
        }

        sb.append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, Blend effect) {
        sb.append(instanceName).append(".setMode(").append("BlendMode.").append(effect.getMode()).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setOpacity(").append(roundNumber(effect.getOpacity())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, Glow effect) {
        sb.append(instanceName).append(".setLevel(").append(roundNumber(effect.getLevel())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, Bloom effect) {
        sb.append(instanceName).append(".setThreshold(").append(roundNumber(effect.getThreshold())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, BoxBlur effect) {
        sb.append(instanceName).append(".setWidth(").append(roundNumber(effect.getWidth())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setHeight(").append(roundNumber(effect.getHeight())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setIterations(").append(roundNumber(effect.getIterations())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, ColorAdjust effect) {
        sb.append(instanceName).append(".setHue(").append(roundNumber(effect.getHue())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setSaturation(").append(roundNumber(effect.getSaturation())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setBrightness(").append(roundNumber(effect.getBrightness())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setContrast(").append(roundNumber(effect.getContrast())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, ColorInput effect) {
        sb.append(instanceName).append(".setX(").append(roundNumber(effect.getX())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setY(").append(roundNumber(effect.getY())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setWidth(").append(roundNumber(effect.getWidth())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setHeight(").append(roundNumber(effect.getHeight())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setPaint(").append(PaintConvertUtil.convertPaintToJavaCode(effect.getPaint(), false)).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, DisplacementMap effect) {
        sb.append(instanceName).append(".setOffsetX(").append(roundNumber(effect.getOffsetX())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setOffsetY(").append(roundNumber(effect.getOffsetY())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setScaleX(").append(roundNumber(effect.getScaleX())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setScaleY(").append(roundNumber(effect.getScaleY())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, DropShadow effect) {
        buildShadowProperty(sb, instanceName, effect.getBlurType(), effect.getWidth(), effect.getHeight(), effect.getRadius(), effect.getColor());
        sb.append(instanceName).append(".setOffsetX(").append(roundNumber(effect.getOffsetX())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setOffsetY(").append(roundNumber(effect.getOffsetY())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setSpread(").append(roundNumber(effect.getSpread())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, GaussianBlur effect) {
        sb.append(instanceName).append(".setRadius(").append(roundNumber(effect.getRadius())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, ImageInput effect) {
        sb.append(instanceName).append(".setX(").append(roundNumber(effect.getX())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setY(").append(roundNumber(effect.getY())).append(");").append(StringUtil.NEW_LINE);
        Image source = effect.getSource();
        if (source != null && source.getUrl() != null) {
            sb.append(instanceName).append(".setSource(new Image(\"").append(source.getUrl()).append("\"));").append(StringUtil.NEW_LINE);
        }
    }

    private static void handleEffect(StringBuilder sb, String instanceName, InnerShadow effect) {
        buildShadowProperty(sb, instanceName, effect.getBlurType(), effect.getWidth(), effect.getHeight(), effect.getRadius(), effect.getColor());
        sb.append(instanceName).append(".setOffsetX(").append(roundNumber(effect.getOffsetX())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setOffsetY(").append(roundNumber(effect.getOffsetY())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setChoke(").append(roundNumber(effect.getChoke())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, Lighting effect) {
        sb.append(instanceName).append(".setDiffuseConstant(").append(roundNumber(effect.getDiffuseConstant())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setSpecularConstant(").append(roundNumber(effect.getSpecularConstant())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setSpecularExponent(").append(roundNumber(effect.getSpecularExponent())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setSurfaceScale(").append(roundNumber(effect.getSurfaceScale())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, MotionBlur effect) {
        sb.append(instanceName).append(".setAngle(").append(roundNumber(effect.getAngle())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setRadius(").append(roundNumber(effect.getRadius())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, PerspectiveTransform effect) {
        sb.append(instanceName).append(".setLlx(").append(roundNumber(effect.getLlx())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setLly(").append(roundNumber(effect.getLly())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setLrx(").append(roundNumber(effect.getLrx())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setLry(").append(roundNumber(effect.getLry())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setUlx(").append(roundNumber(effect.getUlx())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setUly(").append(roundNumber(effect.getUly())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setUrx(").append(roundNumber(effect.getUrx())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setUry(").append(roundNumber(effect.getUry())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, Reflection effect) {
        sb.append(instanceName).append(".setFraction(").append(roundNumber(effect.getFraction())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setTopOffset(").append(roundNumber(effect.getTopOffset())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setBottomOpacity(").append(roundNumber(effect.getBottomOpacity())).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setTopOpacity(").append(roundNumber(effect.getTopOpacity())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, SepiaTone effect) {
        sb.append(instanceName).append(".setLevel(").append(roundNumber(effect.getLevel())).append(");").append(StringUtil.NEW_LINE);
    }

    private static void handleEffect(StringBuilder sb, String instanceName, Shadow effect) {
        buildShadowProperty(sb, instanceName, effect.getBlurType(), effect.getWidth(), effect.getHeight(), effect.getRadius(), effect.getColor());
    }

    private static void buildShadowProperty(StringBuilder sb, String instanceName, BlurType blurType, double width, double height, double radius, Color color) {
        sb.append(instanceName).append(".setBlurType(").append("BlurType.").append(blurType).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setWidth(").append(roundNumber(width)).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setHeight(").append(roundNumber(height)).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setRadius(").append(roundNumber(radius)).append(");").append(StringUtil.NEW_LINE)
                .append(instanceName).append(".setColor(").append(PaintConvertUtil.convertPaintToJavaCode(color, false)).append(");").append(StringUtil.NEW_LINE);
    }

    private static String roundNumber(double value) {
        return NumberUtil.trimTrailingZeros(value, 3);
    }

    public static String convertToCss(InnerShadow innerShadow) {
        return "-fx-effect: innershadow(" +
                blurTypeToString(innerShadow.getBlurType()) + ", " +
                PaintConvertUtil.convertPaintToCss(innerShadow.getColor()) + ", " +
                innerShadow.getRadius() + ", " +
                innerShadow.getChoke() + ", " +
                innerShadow.getOffsetX() + ", " +
                innerShadow.getOffsetY() +
                ");";
    }

    public static String convertToCss(DropShadow dropShadow) {
        return "-fx-effect: dropshadow(" +
                blurTypeToString(dropShadow.getBlurType()) + ", " +
                PaintConvertUtil.convertPaintToCss(dropShadow.getColor()) + ", " +
                dropShadow.getRadius() + ", " +
                dropShadow.getSpread() + ", " +
                dropShadow.getOffsetX() + ", " +
                dropShadow.getOffsetY() +
                ");";
    }

    private static String blurTypeToString(BlurType blurType) {
        return switch (blurType) {
            case ONE_PASS_BOX -> "one-pass-box";
            case TWO_PASS_BOX -> "two-pass-box";
            case THREE_PASS_BOX -> "three-pass-box";
            case GAUSSIAN -> "gaussian";
        };
    }
}
