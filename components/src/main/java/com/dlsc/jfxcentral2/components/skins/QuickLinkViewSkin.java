package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.QuickLinkView;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.model.DateQuickLink;
import com.dlsc.jfxcentral2.model.ImageQuickLink;
import com.dlsc.jfxcentral2.model.NormalQuickLink;
import com.dlsc.jfxcentral2.model.QuickLink;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class QuickLinkViewSkin extends ControlBaseSkin<QuickLinkView> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

    public QuickLinkViewSkin(QuickLinkView control) {
        super(control);
        control.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> control.toFront());
        layoutBySize();
    }

    public void layoutBySize() {
        QuickLinkView control = getSkinnable();
        QuickLink quickLink = control.getQuickLink();
        control.getStyleClass().removeAll("date-link-view", "normal-link-view", "image-link-view", "empty-link-view");
        if (quickLink instanceof DateQuickLink temp) {
            control.getStyleClass().add("date-link-view");

            Label titleLabel = new Label(temp.getTitle());
            titleLabel.getStyleClass().add("title-label");
            if (temp.getIkon() != null) {
                FontIcon icon = new FontIcon(temp.getIkon());
                icon.getStyleClass().add("icon");
                titleLabel.setGraphic(icon);
            }

            FontIcon linkIcon = new FontIcon(IkonUtil.link);
            linkIcon.getStyleClass().add("link-icon");

            HBox topBox = new HBox(titleLabel, new Spacer(), linkIcon);
            topBox.getStyleClass().add("top-box");

            Label descriptionLabel = new Label(temp.getDescription());
            descriptionLabel.getStyleClass().add("description-label");

            Label dateLabel = new Label(temp.getDate() == null ? null : temp.getDate().format(DATE_FORMATTER));
            dateLabel.getStyleClass().add("date-label");

            HBox bottomBox = new HBox(descriptionLabel, new Spacer(), dateLabel);
            bottomBox.getStyleClass().add("bottom-box");

            VBox contentBox = new VBox(topBox, new Spacer(Orientation.VERTICAL), bottomBox);
            contentBox.getStyleClass().add("content-box");
            getChildren().setAll(contentBox);

        } else if (quickLink instanceof NormalQuickLink temp) {
            control.getStyleClass().add("normal-link-view");

            FontIcon icon = new FontIcon(temp.getIkon());
            icon.getStyleClass().add("icon");

            FontIcon linkIcon = new FontIcon(IkonUtil.link);
            linkIcon.getStyleClass().add("link-icon");

            HBox topBox = new HBox(icon, new Spacer(), linkIcon);
            topBox.getStyleClass().add("top-box");

            Label titleLabel = new Label(temp.getTitle());
            titleLabel.getStyleClass().add("title-label");

            Label descriptionLabel = new Label(temp.getDescription());
            descriptionLabel.getStyleClass().add("description-label");
            descriptionLabel.setWrapText(true);

            VBox contentBox = new VBox(topBox, new Spacer(Orientation.VERTICAL), titleLabel, descriptionLabel);
            contentBox.getStyleClass().add("content-box");
            getChildren().setAll(contentBox);
        } else if (quickLink instanceof ImageQuickLink temp) {
            control.getStyleClass().add("image-link-view");
            control.setStyle("-fx-background-image: url(" + temp.getImageUrl() + ");");
        } else if (quickLink == null) {
            control.getStyleClass().add("empty-link-view");
        }
    }
}
