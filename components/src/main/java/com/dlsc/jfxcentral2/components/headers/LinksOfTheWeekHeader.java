package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;
import scala.Option;

import java.util.Objects;

public class LinksOfTheWeekHeader extends CategoryHeader  {

    private static final Image BACKGROUND_IMAGE = new Image(Objects.requireNonNull(LinksOfTheWeekHeader.class.getResource("links-of-the-week-banner.jpg")).toExternalForm());
    private static final Image RSS_IMAGE = new Image(Objects.requireNonNull(LinksOfTheWeekHeader.class.getResource("rss.png")).toExternalForm());

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
        rssImageView.setImage(RSS_IMAGE);
        LinkUtil.setLinkInternalNoPush(rssImageView, "/lotw/rss.xml", Option.empty(), false);

        HBox box = new HBox(label, rssImageView);
        box.getStyleClass().add("rss-box");
        box.setAlignment(Pos.CENTER);

        Label descriptionLabel = new Label("Do you have JavaFX news you want to share with the community? Let us know!\nSend a mail to links@jfx-central.com");
        descriptionLabel.getStyleClass().add("description-label");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMinHeight(Region.USE_PREF_SIZE);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);

        VBox vbox = new VBox(20, box, descriptionLabel);
        vbox.setAlignment(Pos.CENTER);

        setContent(vbox);
        setTitle("Links of the Week");
        setIkon(IkonUtil.getModelIkon(LinksOfTheWeek.class));
        setBackgroundImage(BACKGROUND_IMAGE);
    }
}
