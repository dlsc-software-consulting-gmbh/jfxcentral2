package com.dlsc.jfxcentral2.model;

import javafx.scene.image.Image;

public class VideosDetailsObject extends DetailsObject {

    public VideosDetailsObject() {
    }

    public VideosDetailsObject(String title, Description description, Image mainPreview, String mainPreviewDescription, boolean isSaved, boolean isLiked) {
        super(title, null, description, mainPreview, mainPreviewDescription, null, isSaved, isLiked);
    }
}
