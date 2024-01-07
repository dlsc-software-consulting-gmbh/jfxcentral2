package com.dlsc.jfxcentral2.utils;

public class NumberUtil {

    private NumberUtil() {
    }

    public static String trimTrailingZeros(double value) {
        return trimTrailingZeros(value, false, 2);
    }

    public static String trimTrailingZeros(double value, int decimalPlaces) {
        return trimTrailingZeros(value, false, decimalPlaces);
    }


    /**
     * Simplifies a double value by trimming trailing zeros and, if desired, the decimal point.
     *
     * @param value         The double value to be simplified.
     * @param keepDecimal   If set to true, the decimal point is retained even if the number is an integer;
     *                      if set to false, the decimal point is removed for integer values.
     * @param decimalPlaces The number of decimal places to keep. The double value is rounded to this many decimal places.
     * @return              A string representation of the simplified double value.
     */
    public static String trimTrailingZeros(double value, boolean keepDecimal, int decimalPlaces) {
        String formatStr = "%." + decimalPlaces + "f";
        String formatted = String.format(formatStr, value);

        if (keepDecimal) {
            return formatted.replaceAll("\\.?0*$", "");
        } else {
            long intValue = (long) value;
            if (value == intValue) {
                return String.valueOf(intValue);
            } else {
                return formatted.replaceAll("\\.?0*$", "");
            }
        }
    }

    /**
     * Clamps a value between a minimum and maximum value.
     *
     * @param value The value to be clamped.
     * @param min   The minimum allowable value.
     * @param max   The maximum allowable value.
     * @return The clamped value, which will be between min and max (inclusive).
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }


}
