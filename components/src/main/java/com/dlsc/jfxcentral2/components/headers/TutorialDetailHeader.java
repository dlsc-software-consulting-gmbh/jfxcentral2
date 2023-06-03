package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Tutorial;
import javafx.scene.image.Image;

public class TutorialDetailHeader extends SimpleDetailHeader<Tutorial>  {

    public TutorialDetailHeader(Tutorial tutorial) {
        super(tutorial);

        getStyleClass().addAll("tutorial-detail-header", "dark-header");

        setBackgroundImage(new Image(TutorialDetailHeader.class.getResource("tutorials-banner.jpg").toExternalForm()));
        setWebsite(tutorial.getUrl());
        setShareUrl("tutorials/" + tutorial.getId());
        setShareText("Found this tutorial on @JFXCentral: " + tutorial.getName());
        setMailSubject("Tutorial: " + tutorial.getName());
        setBackText("ALL TUTORIALS");
        setBackUrl("/tutorials");
    }
}
