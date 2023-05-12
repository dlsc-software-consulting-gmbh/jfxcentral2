package com.dlsc.jfxcentral2.model.details;

import javafx.scene.image.Image;

public class AppsDetailsObject extends DetailsObject {

    public AppsDetailsObject() {
    }

    public AppsDetailsObject(String title, Description description, Image mainPreview, boolean isSaved, boolean isLiked) {
        super(title, null, description, mainPreview, null, null, isSaved, isLiked);
    }

}