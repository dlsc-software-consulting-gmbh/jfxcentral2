package com.dlsc.jfxcentral2.model;

import javafx.scene.image.Image;

public class DownloadsDetailsObject extends DetailsObject {

    public DownloadsDetailsObject(String title, Description description, Image mainPreview, boolean isSaved, boolean isLiked) {
        super(title, null, description, mainPreview, null, null, isSaved, isLiked);
    }
}
