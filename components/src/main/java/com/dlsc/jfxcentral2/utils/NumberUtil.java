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
     * Formats a double value and removes unnecessary trailing zeros based on specified conditions.
     *
     * @param value         The double value to be formatted.
     * @param keepDecimal   Determines if a decimal point and zero should be retained for integer results.
     *                      If true, even integer results will have ".0" appended.
     *                      For example, with value = 5 and keepDecimal = true, the output will be "5.0".
     *                      If false, results like "5.0" will be converted to "5", removing ".0".
     * @param decimalPlaces The maximum number of decimal places to retain.
     *                      The method rounds the number to this many decimal places.
     *                      For instance, if value = 5.1234 and decimalPlaces = 2, the output will be "5.12".
     * @return              A string representation of the formatted number.
     *                      The primary purpose of this method is to eliminate meaningless trailing zeros
     *                      in decimal numbers, making the display more concise.
     */
    public static String trimTrailingZeros(double value, boolean keepDecimal, int decimalPlaces) {
        String formatStr = "%." + decimalPlaces + "f";
        String formatted = String.format(formatStr, value);

        long intValue = (long) value;
        if (value == intValue) {
            return keepDecimal ? String.format("%.1f", value) : String.valueOf(intValue);
        } else {
            String str = formatted.replaceAll("\\.?0*$", "");
            return keepDecimal && !str.contains(".") ? str + ".0" : str;
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
