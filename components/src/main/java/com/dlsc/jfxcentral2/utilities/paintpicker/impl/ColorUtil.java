package com.dlsc.jfxcentral2.utilities.paintpicker.impl;

import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.HSB;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.HSL;
import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.ColorFormat;
import com.dlsc.jfxcentral2.utils.NumberUtil;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ColorUtil {
    private static final Random RANDOM = new Random();

    private ColorUtil() {
    }

    public static Color randomColor() {
        return new Color(randomDouble(), randomDouble(), randomDouble(), 1.0);
    }

    private static double randomDouble() {
        return RANDOM.nextDouble();
    }


    /**
     * Converts a Color object into its corresponding hexadecimal string representation.
     * <p>
     * If the color's opacity is fully opaque (1.0), then the returned string will
     * be in the form of "#RRGGBB". Otherwise, if there's any transparency, it will
     * be in the form of "#RRGGBBOO", where "OO" represents the opacity component.
     * </p>
     *
     * @param color The Color object to convert.
     * @return A hexadecimal string representing the color.
     */
    public static String colorToWebHex(Color color) {
        int red = (int) Math.round(color.getRed() * 255.0d);
        int green = (int) Math.round(color.getGreen() * 255.0d);
        int blue = (int) Math.round(color.getBlue() * 255.0d);
        int alpha = (int) Math.round(color.getOpacity() * 255.0d);
        if (alpha == 255) {
            return String.format("#%02x%02x%02x", red, green, blue);
        } else {
            return String.format("#%02x%02x%02x%02x", red, green, blue, alpha);
        }
    }

    /**
     * Converts a hexadecimal string representation of a color into a Color object.
     */
    public static Color webHexToColor(String hexColor) {
        return Color.web(hexColor);
    }

    /**
     * Converts a Color object to an integer representation.
     * <p>
     * This method packs the RGBA components of the Color into a single 32-bit integer.
     * The highest 8 bits are for the alpha component (opacity), followed by 8 bits each for
     * the red, green, and blue components.
     * </p>
     * <p>
     * Application Scenario:
     * This can be useful for efficient storage of colors in data structures, databases, or
     * when working with APIs that accept an integer representation of a color.
     * </p>
     *
     * @param color the Color object to be converted.
     * @return a 32-bit integer representing the color.
     */
    public static int colorToInt(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        int a = (int) (color.getOpacity() * 255);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Converts an integer representation of a color to a Color object.
     * <p>
     * This method unpacks a 32-bit integer into its RGBA components to create a Color object.
     * The highest 8 bits of the integer are for the alpha component (opacity), followed by 8 bits each for
     * the red, green, and blue components.
     * </p>
     * <p>
     * Application Scenario:
     * Useful when retrieving color data from data structures, databases, or
     * when working with APIs that provide an integer representation of a color and it needs
     * to be transformed back to a Color object for further manipulation or display.
     * </p>
     *
     * @param packedInt the 32-bit integer representing the color.
     * @return the corresponding Color object.
     */
    public static Color intToColor(int packedInt) {
        double a = ((packedInt >> 24) & 0xFF) / 255.0;
        double r = ((packedInt >> 16) & 0xFF) / 255.0;
        double g = ((packedInt >> 8) & 0xFF) / 255.0;
        double b = (packedInt & 0xFF) / 255.0;

        return new Color(r, g, b, a);
    }

    /**
     * Returns the triadic harmonies of the given color.
     */
    public static List<Color> triadicHarmonies(Color color) {
        double hue = color.getHue();
        Color harmony1 = Color.hsb((hue + 120) % 360, color.getSaturation(), color.getBrightness());
        Color harmony2 = Color.hsb((hue + 240) % 360, color.getSaturation(), color.getBrightness());
        return List.of(color, harmony1, harmony2);
    }

    /**
     * Returns two colors that are close but not identical complements to the given color.
     */
    public static List<Color> splitComplementaryColors(Color color) {
        double hue = color.getHue();
        Color complement1 = Color.hsb((hue + 150) % 360, color.getSaturation(), color.getBrightness());
        Color complement2 = Color.hsb((hue + 210) % 360, color.getSaturation(), color.getBrightness());
        return List.of(color, complement1, complement2);
    }

    /**
     * Returns the complementary color of the given color.
     */
    public static List<Color> complementaryColors(Color color) {
        double hue = color.getHue();
        Color complement = Color.hsb((hue + 180) % 360, color.getSaturation(), color.getBrightness());
        return List.of(color, complement);
    }


    /**
     * Generates a list of monochromatic colors from a given base color.
     *
     * @param baseColor The base color.
     * @param count     The number of colors to generate.
     * @return A list of monochromatic colors.
     */
    public static List<Color> generateMonochromaticColors(Color baseColor, int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    double factor = i / (double) (count - 1);
                    return baseColor.deriveColor(0, 1, 0.5 + factor * 0.5, 1);
                })
                .toList();
    }


    /**
     * Generates a list of analogous colors from a given base color.
     *
     * @param baseColor The base color.
     * @param count The number of colors to generate.
     * @return A list of analagous colors.
     */
    public static List<Color> generateAnalogousColors(Color baseColor, int count) {
        double hueShift = 360.0 / 36;

        int halfCount = count / 2;

        List<Color> colorsLeft = IntStream.rangeClosed(1, halfCount)
                .mapToObj(i -> {
                    double newHue = (baseColor.getHue() - i * hueShift + 360) % 360;
                    return Color.hsb(newHue, baseColor.getSaturation(), baseColor.getBrightness());
                })
                .toList();

        List<Color> colorsRight = IntStream.range(1, halfCount + (count % 2 == 0 ? 1 : 2))
                .mapToObj(i -> {
                    double newHue = (baseColor.getHue() + i * hueShift) % 360;
                    return Color.hsb(newHue, baseColor.getSaturation(), baseColor.getBrightness());
                })
                .toList();

        List<Color> combined = new ArrayList<>(colorsLeft);
        combined.add(baseColor);
        combined.addAll(colorsRight);

        return combined;
    }

    /**
     * Generates a tetradic color scheme based on the given color.
     * A tetradic color scheme uses four colors arranged into two complementary pairs.
     *
     * @param color The base color for which the tetradic colors are to be calculated.
     * @return A list of four colors forming the tetradic color scheme.
     */
    public static List<Color> tetradicColors(Color color) {
        double hue = color.getHue();

        Color secondColor = Color.hsb((hue + 90) % 360, color.getSaturation(), color.getBrightness());
        Color thirdColor = Color.hsb((hue + 180) % 360, color.getSaturation(), color.getBrightness());
        Color fourthColor = Color.hsb((hue + 270) % 360, color.getSaturation(), color.getBrightness());

        return List.of(color, secondColor, thirdColor, fourthColor);
    }


    /**
     * Converts a JavaFX Color to its HSB representation.
     *
     * @param color The JavaFX Color object.
     * @return An HSB object representing the provided color.
     */
    public static HSB colorToHsb(Color color) {
        return new HSB(color.getHue(), color.getSaturation(), color.getBrightness(), color.getOpacity());
    }

    /**
     * Converts an HSB representation of color to a JavaFX Color.
     *
     * @param hsb The HSB representation of the color.
     * @return A JavaFX Color object representing the provided HSB value.
     */
    public static Color hsbToColor(HSB hsb) {
        return hsbToColor(hsb.getHue(), hsb.getSaturation(), hsb.getBrightness(), hsb.getOpacity());
    }

    /**
     * Converts HSB values to a JavaFX Color with full opacity.
     *
     * @param hue        The hue component of the HSB.
     * @param saturation The saturation component of the HSB.
     * @param brightness The brightness component of the HSB.
     * @return A JavaFX Color object representing the provided HSB values with full opacity.
     */
    public static Color hsbToColor(double hue, double saturation, double brightness) {
        return Color.hsb(hue, saturation, brightness, 1.0);
    }

    /**
     * Converts HSB values to a JavaFX Color.
     *
     * @param hue        The hue component of the HSB.
     * @param saturation The saturation component of the HSB.
     * @param brightness The brightness component of the HSB.
     * @param opacity    The opacity component (alpha).
     * @return A JavaFX Color object representing the provided HSB values.
     */
    public static Color hsbToColor(double hue, double saturation, double brightness, double opacity) {
        return Color.hsb(hue, saturation, brightness, opacity);
    }

    /**
     * Converts a JavaFX Color to its HSL representation.
     *
     * @param color The JavaFX Color object.
     * @return An HSL object representing the provided color.
     */
    public static HSL colorToHsl(Color color) {
        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();

        double max = Math.max(r, Math.max(g, b));
        double min = Math.min(r, Math.min(g, b));

        double h, s, l;
        l = (max + min) / 2;

        // If the max and min are the same, it means we have a shade of grey.
        if (max == min) {
            h = s = 0; // achromatic (grey)
        } else {
            double delta = max - min;
            s = l > 0.5 ? delta / (2.0 - max - min) : delta / (max + min);

            // Determine hue
            if (r > g && r > b) {
                h = (g - b) / delta + (g < b ? 6 : 0);
            } else if (g > b) {
                h = (b - r) / delta + 2;
            } else {
                h = (r - g) / delta + 4;
            }
            h /= 6;
        }

        return new HSL(h * 360, s, l, color.getOpacity());
    }

    /**
     * Converts an HSL representation of color to a JavaFX Color.
     *
     * @param hsl The HSL representation of the color.
     * @return A JavaFX Color object representing the provided HSL value.
     */
    public static Color hslToColor(HSL hsl) {
        double h = hsl.getHue() / 360.0;
        double s = hsl.getSaturation();
        double l = hsl.getLightness();

        double r, g, b;

        // If saturation is 0, it means we have a shade of grey.
        if (s == 0) {
            r = g = b = l; // achromatic (grey)
        } else {
            double q = l < 0.5 ? l * (1 + s) : l + s - l * s;
            double p = 2 * l - q;

            // Convert hue to RGB
            r = hueToRgb(p, q, h + 1.0 / 3.0);
            g = hueToRgb(p, q, h);
            b = hueToRgb(p, q, h - 1.0 / 3.0);
        }

        return new Color(r, g, b, hsl.getOpacity());
    }

    public static Color hslToColor(double hue, double saturation, double lightness) {
        return hslToColor(hue, saturation, lightness, 1.0);
    }

    public static Color hslToColor(double hue, double saturation, double lightness, double opacity) {
        return hslToColor(new HSL(hue, saturation, lightness, opacity));
    }

    /**
     * Helper method to convert hue to its RGB representation.
     *
     * @param p First RGB component.
     * @param q Second RGB component.
     * @param t Hue value to be converted.
     * @return The RGB value for the given hue.
     */
    private static double hueToRgb(double p, double q, double t) {
        if (t < 0) t += 1;
        if (t > 1) t -= 1;
        if (t < 1.0 / 6.0) return p + (q - p) * 6 * t;
        if (t < 1.0 / 2.0) return q;
        if (t < 2.0 / 3.0) return p + (q - p) * (2.0 / 3.0 - t) * 6;
        return p;
    }

    public static String formatColorToString(HSB hsb) {
        if (hsb == null) {
            return "";
        }
        return formatColor(hsb.getHue(), hsb.getSaturation(), hsb.getBrightness(), hsb.getOpacity());
    }

    public static String formatColorToString(HSL hsl) {
        if (hsl == null) {
            return "";
        }
        return formatColor(hsl.getHue(), hsl.getSaturation(), hsl.getLightness(), hsl.getOpacity());
    }

    private static String formatColor(double hue, double firstProperty, double secondProperty, double opacity) {
        StringBuilder result = new StringBuilder()
                .append((int) hue).append(",")
                .append(NumberUtil.trimTrailingZeros(firstProperty, 3)).append(",")
                .append(NumberUtil.trimTrailingZeros(secondProperty, 3));
        if (opacity < 1.0) {
            result.append(",").append(NumberUtil.trimTrailingZeros(opacity, 3));
        }
        return result.toString();
    }

    public static String formatColorToString(Color color) {
        if (color == null) {
            return "";
        }
        return formatColorToString(color, ColorFormat.DECIMAL);
    }

    public static String formatColorToString(Color color, ColorFormat colorFormat) {
        if (color == null || colorFormat == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();

        switch (colorFormat) {
            case INTEGER -> {
                result.append((int) (color.getRed() * 255)).append(",");
                result.append((int) (color.getGreen() * 255)).append(",");
                result.append((int) (color.getBlue() * 255));
            }
            case DECIMAL -> {
                result.append(NumberUtil.trimTrailingZeros(color.getRed(), 3)).append(",");
                result.append(NumberUtil.trimTrailingZeros(color.getGreen(), 3)).append(",");
                result.append(NumberUtil.trimTrailingZeros(color.getBlue(), 3));
            }
            case HEX -> {
                return colorToWebHex(color);
            }
        }

        if (color.getOpacity() < 1.0) {
            result.append(",").append(NumberUtil.trimTrailingZeros(color.getOpacity(), 3));
        }

        return result.toString();
    }

}
