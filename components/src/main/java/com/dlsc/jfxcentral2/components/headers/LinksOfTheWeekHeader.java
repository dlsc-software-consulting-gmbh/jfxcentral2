package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class LinksOfTheWeekHeader extends CategoryHeader  {

    public LinksOfTheWeekHeader() {
        super();

        getStyleClass().addAll("links-of-the-week-header", "dark-header");

        FontIcon fontIcon = new FontIcon();
        fontIcon.iconCodeProperty().bind(ikonProperty());

        Label label = new Label();
        label.textProperty().bind(titleProperty());
        label.setGraphic(fontIcon);
        label.getStyleClass().add("header-title");
        label.managedProperty().bind(label.visibleProperty());
        label.visibleProperty().bind(titleProperty().isNotEmpty().or(ikonProperty().isNotNull()));

        CustomImageView rssImageView = new CustomImageView();
        rssImageView.setImage(new Image(LinksOfTheWeekHeader.class.getResource("rss.png").toExternalForm()));
        LinkUtil.setLink(rssImageView, "/links/rss");

        VBox box = new VBox(label, rssImageView);
        box.getStyleClass().add("rss-box");

        box.setAlignment(Pos.CENTER);
        setContent(box);
        setTitle("Links of the Week");
        setIkon(IkonUtil.getModelIkon(LinksOfTheWeek.class));
        setBackgroundImage(new Image(LinksOfTheWeekHeader.class.getResource("links-of-the-week-banner.jpg").toExternalForm()));
    }
}
