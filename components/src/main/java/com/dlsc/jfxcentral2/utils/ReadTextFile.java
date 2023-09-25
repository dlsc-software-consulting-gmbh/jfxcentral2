package com.dlsc.jfxcentral2.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReadTextFile {
    private static final Logger LOGGER = LogManager.getLogger(ReadTextFile.class);
    private ReadTextFile() {
    }

    /**
     * Reads the content of a text file from the resources path using a specified class loader.
     *
     * @param clazz The class whose class loader should be used to locate the resource.
     * @param resourcePackagePath The relative path to the resource.
     * @return The content of the file as a string. Returns an empty string if any error occurs.
     */
    public static String readText(Class<?> clazz, String resourcePackagePath) {
        try (InputStream inputStream = clazz.getResourceAsStream(resourcePackagePath);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream, "InputStream must not be null")))) {
            return bufferedReader.lines().collect(Collectors.joining(StringUtil.NEW_LINE));
        } catch (IOException e) {
            LOGGER.error("Failed to read the file: {}" , resourcePackagePath, e);
            return "";
        }
    }


}
