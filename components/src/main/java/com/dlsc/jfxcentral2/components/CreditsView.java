package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.CreditModel;
import com.dlsc.jfxcentral2.model.License;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import one.jpro.routing.LinkUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CreditsView extends PaneBase {
    private static final Logger LOGGER = LogManager.getLogger(CreditsView.class);
    public CreditsView() {
        getStyleClass().add("credits-view");
        VBox container = new VBox();
        container.getStyleClass().add("container");
        getChildren().add(container);

        // message
        Label message = new Label("Building this application was only possible because of all the fine work in many other open source projects. The projects listed below are our direct dependencies and we want to say \"Thank You!\" to them for their valuable contributions.");
        message.setWrapText(true);
        message.getStyleClass().add("message-label");
        container.getChildren().add(message);

        List<CreditModel> credits = loadCredits();
        credits.sort((o1, o2) -> o1.getId().compareToIgnoreCase(o2.getId()));
        int itemSize = credits.size();
        for (int i = 0; i < itemSize; i++) {
            CreditModel credit = credits.get(i);
            CreditItemView creditItemView = new CreditItemView(credit);
            container.getChildren().add(creditItemView);

            if (i != itemSize - 1) {
                Region dividingLine = new Region();
                dividingLine.getStyleClass().add("dividing-line");
                container.getChildren().add(dividingLine);
            }
        }

    }

    private List<CreditModel> loadCredits() {
        String filePath = "/com/dlsc/jfxcentral2/credits/credits.json";
        Type listType = new TypeToken<List<CreditModel>>() {
        }.getType();
        try (InputStream inputStream = getClass().getResourceAsStream(filePath);
             InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(inputStream));
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            return new Gson().fromJson(bufferedReader, listType);
        } catch (IOException e) {
            LOGGER.error("Failed to load credits from " + filePath, e);
            return Collections.emptyList();
        }
    }

    private static class CreditItemView extends VBox {

        public CreditItemView(CreditModel creditModel) {
            getStyleClass().add("credit-item-view");

            CustomImageView logoImage = new CustomImageView();
            logoImage.getStyleClass().add("logo-image");

            URL resource = getClass().getResource("/com/dlsc/jfxcentral2/credits/" + creditModel.getId() + "/logo.png");
            if (resource != null) {
                logoImage.setImage(new Image(resource.toExternalForm()));
            }

            StackPane logoWrapper = new StackPane(logoImage);
            logoWrapper.getStyleClass().add("logo-wrapper");

            Label nameLabel = new Label();
            nameLabel.getStyleClass().add("name-label");
            nameLabel.setText(creditModel.getName());

            HBox licenceBox = new HBox();
            licenceBox.getStyleClass().add("licence-box");
            licenceBox.setMaxWidth(Region.USE_PREF_SIZE);

            License license = creditModel.getLicence();
            if (license != null) {
                Label licenceTypeLabel = new Label();
                licenceTypeLabel.getStyleClass().add("licence-type-label");
                licenceTypeLabel.setText(license.getType());

                Label licenceVersionLabel = new Label();
                licenceVersionLabel.getStyleClass().add("licence-version-label");
                licenceVersionLabel.setText(license.getVersion());

                licenceBox.getChildren().addAll(licenceTypeLabel, licenceVersionLabel);
            } else {
                Label version = new Label("V " + creditModel.getVersion());
                version.getStyleClass().add("version-label");
                licenceBox.getChildren().add(version);
            }

            Button linkButton = new Button();
            linkButton.setFocusTraversable(false);
            linkButton.getStyleClass().add("link-button");
            linkButton.setGraphic(new FontIcon(IkonUtil.link));
            LinkUtil.setExternalLink(linkButton, creditModel.getUrl());

            Label descriptionLabel = new Label();
            descriptionLabel.getStyleClass().add("description-label");
            descriptionLabel.setWrapText(true);
            descriptionLabel.setText(creditModel.getDescription());

            VBox titleBox = new VBox(nameLabel, licenceBox);
            titleBox.getStyleClass().add("name-box");
            HBox.setHgrow(titleBox, Priority.ALWAYS);

            HBox topBox = new HBox(logoWrapper, titleBox, linkButton);
            topBox.getStyleClass().add("top-box");

            getChildren().setAll(topBox, descriptionLabel);

        }
    }

}

