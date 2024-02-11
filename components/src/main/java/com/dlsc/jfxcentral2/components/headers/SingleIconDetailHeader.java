package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral2.model.IconInfo;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class SingleIconDetailHeader extends DetailHeaderBase {

    public SingleIconDetailHeader(IconInfo iconInfo) {
        getStyleClass().addAll("single-icon-detail-header");

        Ikon icon = iconInfo.getIkon();
        String iconName = icon.toString();
        String iconDesc = icon.getDescription();
        String ikonliPackId = iconInfo.getIkonliPackId();
        String ikonliPackName = iconInfo.getIkonliPackName();

        setTitle(iconName.replace("_", " "));
        setIkon(icon);
        setCenter(createCenterNode(iconName, ikonliPackName));

        setShareText(String.format("Found this icon named %s from %s on @JFXCentral.", iconName, ikonliPackName));
        setShareUrl("icons/" + ikonliPackId + "/" + iconDesc);
        setShareTitle("Icon: " + iconName);

        setBackUrl("/icons/" + ikonliPackId);
        setBackText("Pack");
    }

    private Pane createCenterNode(String iconName, String ikonliPackName) {
        VBox contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");

        Label nameLabel = new Label(getTitle());
        nameLabel.getStyleClass().add("name");
        nameLabel.setWrapText(true);
        nameLabel.setGraphic(new FontIcon(getIkon()));

        Label descLabel = new Label();
        descLabel.setText(String.format("An icon named '%s' from the icon pack %s.", iconName, ikonliPackName));
        descLabel.getStyleClass().add("description");
        descLabel.setWrapText(true);

        contentBox.getChildren().addAll(nameLabel, descLabel);
        return contentBox;
    }

}
