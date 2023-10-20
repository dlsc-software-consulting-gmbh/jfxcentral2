package com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel;

import com.dlsc.jfxcentral2.utilities.paintpicker.impl.ColorUtil;

import java.util.Objects;

public class HSL {
    private double hue;
    private double saturation;
    private double lightness;
    private double opacity = 1.0;

    public HSL() {
    }

    public HSL(double hue, double saturation, double lightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.lightness = lightness;
    }

    public HSL(double hue, double saturation, double lightness, double opacity) {
        this.hue = hue;
        this.saturation = saturation;
        this.lightness = lightness;
        this.opacity = opacity;
    }

    public double getHue() {
        return hue;
    }

    public void setHue(double hue) {
        this.hue = hue;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public double getLightness() {
        return lightness;
    }

    public void setLightness(double lightness) {
        this.lightness = lightness;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HSL hsl = (HSL) o;
        return Double.compare(hue, hsl.hue) == 0 && Double.compare(saturation, hsl.saturation) == 0 && Double.compare(lightness, hsl.lightness) == 0 && Double.compare(opacity, hsl.opacity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hue, saturation, lightness, opacity);
    }

    @Override
    public String toString() {
        return ColorUtil.formatColorToString(this);
    }

}
