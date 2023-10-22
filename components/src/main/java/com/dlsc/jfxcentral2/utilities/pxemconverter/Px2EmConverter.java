package com.dlsc.jfxcentral2.utilities.pxemconverter;

import com.dlsc.jfxcentral2.utils.NumberUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Px2EmConverter {
    private Px2EmConverter() {
    }

    /**
     * Converts px units to em units for specified CSS properties in a CSS file.
     *
     * @param sourceFile        the source CSS file
     * @param targetFile        the target CSS file
     * @param propertiesToConvert a list of CSS properties to be converted
     * @param baseFontSize      the base font size for em unit calculation
     * @param addComment        whether to add original value as comment
     * @throws IOException      if an I/O error occurs
     */
    public static void convert(File sourceFile, File targetFile, List<String> propertiesToConvert, double baseFontSize, boolean addComment) throws IOException {
        String content = Files.readString(sourceFile.toPath());
        Pattern commentPattern = Pattern.compile("/\\*.*?\\*/");
        Matcher commentMatcher = commentPattern.matcher(content);

        Map<Integer, String> commentsMap = new HashMap<>();
        while (commentMatcher.find()) {
            commentsMap.put(commentMatcher.start(), commentMatcher.group());
        }

        for (String property : propertiesToConvert) {
            String regex = property + "\\s*:\\s*([0-9]+(?:\\.[0-9]+)?(?:em|px)?(?:\\s*[0-9]+(?:\\.[0-9]+)?(?:em|px)?)*)\\s*;?";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);

            StringBuilder sb = new StringBuilder();
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                boolean inComment = false;
                for (Map.Entry<Integer, String> entry : commentsMap.entrySet()) {
                    int commentStart = entry.getKey();
                    int commentEnd = commentStart + entry.getValue().length();
                    if (start >= commentStart && end <= commentEnd) {
                        inComment = true;
                        break;
                    }
                }

                if (!inComment) {
                    String values = matcher.group(1);
                    if (!values.contains("em")) {
                        String emValues = convertToEm(values, baseFontSize);
                        if (addComment) {
                            matcher.appendReplacement(sb, property + ": " + emValues + ";" + "/* " + values + " */");
                        } else {
                            matcher.appendReplacement(sb, property + ": " + emValues + ";");
                        }
                    } else {
                        matcher.appendReplacement(sb, property + ": " + values + ";");
                    }
                }
            }
            matcher.appendTail(sb);
            content = sb.toString();
        }

        Files.writeString(Objects.requireNonNullElse(targetFile, sourceFile).toPath(), content);
    }

    //public static void convert(File sourceFile, File targetFile, List<String> propertiesToConvert, double baseFontSize, boolean addComment) throws IOException {
    //    String content = Files.readString(sourceFile.toPath());
    //
    //    for (String property : propertiesToConvert) {
    //        String regex = property + "\\s*:\\s*([0-9]+(?:\\.[0-9]+)?(?:em|px)?(?:\\s*[0-9]+(?:\\.[0-9]+)?(?:em|px)?)*)\\s*;?";
    //        Pattern pattern = Pattern.compile(regex);
    //        Matcher matcher = pattern.matcher(content);
    //
    //        StringBuilder sb = new StringBuilder();
    //        while (matcher.find()) {
    //            String values = matcher.group(1);
    //            if (!values.contains("em")) {
    //                String emValues = convertToEm(values, baseFontSize);
    //                if (addComment) {
    //                    matcher.appendReplacement(sb, property + ": " + emValues + ";" + " /* " + values + " */");
    //                } else {
    //                    matcher.appendReplacement(sb, property + ": " + emValues + ";");
    //                }
    //            } else {
    //                matcher.appendReplacement(sb, property + ": " + values + ";");
    //            }
    //        }
    //        matcher.appendTail(sb);
    //        content = sb.toString();
    //    }
    //
    //    Files.writeString(Objects.requireNonNullElse(targetFile, sourceFile).toPath(), content);
    //}

    public static String convertToEm(String values, double baseFontSize) {
        if (values.trim().endsWith("em")) {
            return values.trim();
        }

        String[] valueArray = values.split("\\s+");
        StringBuilder emBuilder = new StringBuilder();
        for (String value : valueArray) {
            if (!value.isEmpty()) {
                double px = Double.parseDouble(value.replaceAll("px", ""));
                double em = px / baseFontSize;
                emBuilder.append(NumberUtil.trimTrailingZeros(em, 5)).append("em").append(" ");
            }
        }
        return emBuilder.toString().trim();
    }

}
