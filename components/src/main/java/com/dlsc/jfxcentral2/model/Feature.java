package com.dlsc.jfxcentral2.model;

import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;
import org.kordamp.ikonli.Ikon;

public record Feature(String title, String description,
                      String tagName, String remark,
                      Ikon remarkIcon, Type type,
                      ObjectProperty<Image> imageProperty, String url) {

    public enum Type {
        VIDEO,
        TOOL,
        LIBRARY,
        ARTICLE,
        TUTORIAL,
        TIP,
        PERSON, BLOG, BOOK, SHOWCASE
    }
}
