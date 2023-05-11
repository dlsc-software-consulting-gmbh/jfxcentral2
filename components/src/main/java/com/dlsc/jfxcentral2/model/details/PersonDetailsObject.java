package com.dlsc.jfxcentral2.model.details;

import javafx.scene.image.Image;

public class PersonDetailsObject extends DetailsObject {

    public PersonDetailsObject() {
    }

    public PersonDetailsObject(String title, Description description, Image photo, boolean isSaved, boolean isLiked) {
        super(title, null, description, photo, null, null, isSaved, isLiked);
    }

}
