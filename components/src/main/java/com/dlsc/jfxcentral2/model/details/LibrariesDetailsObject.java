package com.dlsc.jfxcentral2.model.details;

import javafx.scene.image.Image;

import java.util.List;

public class LibrariesDetailsObject extends DetailsObject {

    public LibrariesDetailsObject(String title, Image titleImage, Description description, List<Image> previews, boolean isSaved, boolean isLiked) {
        super(title, titleImage, description, null, null, previews, isSaved, isLiked);
    }
}
