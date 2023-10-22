package com.dlsc.jfxcentral2.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class FileUtil {

    /**
     * Deletes files within a directory while skipping files that are in use.
     * Delete files as much as possible, ignoring occupied files
     * @param directory the directory to clear
     */
    public static void clearDirectory(File directory) {
        // check directory
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                // Ignore file deletion results;
                // Because the file may be in use and cannot be deleted, we just have to try to delete it.
                file.delete();
            } else if (file.isDirectory()) {
                clearDirectory(file);
                file.delete();
            }
        }
    }

    public static String getFileExtension(File file) {
        return extractFileExtension(file.getName());
    }

    public static String extractFileExtension(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        return null;
    }

    /**
     * Returns the name of the file without its file extension.
     *
     * @param file the file
     * @return the name of the file without its file extension
     */
    public static String getFileNameWithoutExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, dotIndex);
    }

}