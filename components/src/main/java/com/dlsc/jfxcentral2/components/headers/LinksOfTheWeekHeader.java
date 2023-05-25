package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.image.Image;

public class LinksOfTheWeekHeader extends CategoryHeader  {

    public LinksOfTheWeekHeader() {
        getStyleClass().addAll("links-detail-header", "dark-header");
        setTitle("Links of the Week");
        setIkon(IkonUtil.getModelIkon(LinksOfTheWeek.class));
        setBackgroundImage(new Image(LinksOfTheWeekHeader.class.getResource("links-of-the-week-banner.jpg").toExternalForm()));
    }
}
