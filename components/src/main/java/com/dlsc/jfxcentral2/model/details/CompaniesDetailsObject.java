package com.dlsc.jfxcentral2.model.details;

import javafx.scene.image.Image;

public class CompaniesDetailsObject extends DetailsObject {

    public CompaniesDetailsObject() {
    }

    public CompaniesDetailsObject(String title, Description description, Image mainPreview, boolean isSaved, boolean isLiked) {
        super(title, null, description, mainPreview, null, null, isSaved, isLiked);
    }

}
