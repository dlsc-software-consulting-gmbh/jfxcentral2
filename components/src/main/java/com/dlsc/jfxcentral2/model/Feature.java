package com.dlsc.jfxcentral2.model;

import javafx.scene.image.Image;
import org.kordamp.ikonli.Ikon;

public record Feature(String title, String description,
                      String tagName, String remark,
                      Ikon remarkIcon, Type type,
                      Image image, String url) {

    public enum Type {
        VIDEO,
        TOOL,
        LIBRARY,
        APP,
        ARTICLE,
        TUTORIAL,
        CASE
    }
}
