package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Tutorial;

public class TutorialDetailHeader extends SimpleDetailHeader<Tutorial>  {

    public TutorialDetailHeader(Tutorial tutorial) {
        super(tutorial);
        getStyleClass().add("tutorial-detail-header");
    }
}