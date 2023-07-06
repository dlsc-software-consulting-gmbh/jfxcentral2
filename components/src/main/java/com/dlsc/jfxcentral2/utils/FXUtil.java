package com.dlsc.jfxcentral2.utils;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class FXUtil {
    private FXUtil() {
    }

    public static void copyToClipboard(String text) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

}
