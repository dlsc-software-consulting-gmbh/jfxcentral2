package com.dlsc.jfxcentral2.utils;

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

}