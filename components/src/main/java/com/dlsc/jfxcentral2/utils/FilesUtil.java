package com.dlsc.jfxcentral2.utils;

import scala.collection.mutable.StringBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FilesUtil {
    private FilesUtil() {

    }

    public static String readText(String filePath) {
        String lineSeparator = System.lineSeparator();

        try (InputStream inputStream = FilesUtil.class.getResourceAsStream(filePath);
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            StringBuilder stringBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append(lineSeparator);
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
