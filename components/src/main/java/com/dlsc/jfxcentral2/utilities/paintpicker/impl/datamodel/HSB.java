package com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel;

import com.dlsc.jfxcentral2.utilities.paintpicker.impl.ColorUtil;

import java.util.Objects;

public  class HSB {
    private  double hue;
    private  double saturation;
    private  double brightness;
    private double opacity = 1.0;

    public HSB() {
    }

    public HSB(double hue, double saturation, double brightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    public HSB(double hue, double saturation, double brightness, double opacity) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
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

    public double getBrightness() {
        return brightness;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
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
        HSB hsb = (HSB) o;
        return Double.compare(hue, hsb.hue) == 0 && Double.compare(saturation, hsb.saturation) == 0 && Double.compare(brightness, hsb.brightness) == 0 && Double.compare(opacity, hsb.opacity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hue, saturation, brightness, opacity);
    }

    @Override
    public String toString() {
        return ColorUtil.formatColorToString(this);
    }

}