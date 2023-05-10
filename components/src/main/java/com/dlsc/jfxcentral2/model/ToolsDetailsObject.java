package com.dlsc.jfxcentral2.model;

import javafx.scene.image.Image;

import java.util.List;

public class ToolsDetailsObject extends DetailsObject {

    public ToolsDetailsObject() {
    }

    public ToolsDetailsObject(String title, DetailsObject.Description description, List<Image> previews, boolean isSaved, boolean isLiked) {
        super(title, null, description, null, null, previews, isSaved, isLiked);
    }

}
