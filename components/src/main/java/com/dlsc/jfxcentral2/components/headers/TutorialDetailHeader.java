package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.scene.image.Image;

import java.util.Objects;

public class TutorialDetailHeader extends SimpleDetailHeader<Tutorial>  {

    private static final Image BACKGROUND_IMAGE = new Image(Objects.requireNonNull(TutorialDetailHeader.class.getResource("tutorials-banner.jpg")).toExternalForm());

    public TutorialDetailHeader(Tutorial tutorial) {
        super(tutorial);

        getStyleClass().addAll("tutorial-detail-header", "dark-header");

        setBackgroundImage(BACKGROUND_IMAGE);
        setWebsite(tutorial.getUrl());
        setShareUrl("tutorials/" + tutorial.getId());
        setShareText("Found this tutorial on @JFXCentral: " + tutorial.getName());
        setShareTitle("Tutorial: " + tutorial.getName());
        setBackText("ALL TUTORIALS");
        setBackUrl(PagePath.TUTORIALS);
    }
}
