package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import com.dlsc.jfxcentral2.utils.LOGGER;
import com.dlsc.jfxcentral2.utils.RegistryHelper;
import com.jpro.webapi.WebAPI;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class MastodonShareView extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "mastodon-share-view";

    private final MastodonInputField serverAddress;
    private final Button okButton;

    public MastodonShareView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Label promptLabel = new Label("What is your Mastodon server URL?");
        promptLabel.getStyleClass().add("prompt-label");

        serverAddress = new MastodonInputField();
        serverAddress.setText(RegistryHelper.get(RegistryHelper.RegistryKey.MASTODON_SERVER));
        serverAddress.setOnAction(event -> {
            String shareUrl = buildShareUrl();
            if (StringUtils.isBlank(shareUrl)) {
                return;
            }
            if (WebAPI.isBrowser()) {
                WebAPI.getWebAPI(getScene()).openURLAsTab(shareUrl);
            } else if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(URI.create(shareUrl));
                } catch (IOException e) {
                    LOGGER.error("Failed to browse the URL: {}", shareUrl, e);
                }
            }
        });

        Label exampleLabel = new Label("E.g. mastodon.social, hachyderm.io,...");
        exampleLabel.getStyleClass().add("example-label");

        Button backButton = new Button("Back");
        backButton.getStyleClass().addAll("back-button", "blue-button");
        backButton.setOnAction(event -> Optional.ofNullable(getOnBack()).ifPresent(Runnable::run));
        backButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(backButton, Priority.ALWAYS);

        okButton = new Button("Ok");
        okButton.getStyleClass().addAll("ok-button", "blue-button");

        okButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(okButton, Priority.ALWAYS);

        HBox actionsBox = new HBox(backButton, okButton);
        actionsBox.getStyleClass().add("actions-box");

        getChildren().addAll(promptLabel, serverAddress, exampleLabel, actionsBox);

        createNewShareLink();
        mastodonUrlProperty().addListener(obs -> createNewShareLink());
        serverAddress.textProperty().addListener(obs -> createNewShareLink());
    }

    private void createNewShareLink() {
        String shareUrl = buildShareUrl();
        if (StringUtils.isNotBlank(shareUrl)) {
            ExternalLinkUtil.setExternalLink(okButton, shareUrl);
            okButton.setDisable(false);
        } else {
            okButton.setDisable(true);
        }
    }

    private String buildShareUrl() {
        String serverUrl = serverAddress.getText()
                .trim()
                .replace("http://", "")
                .replace("https://", "")
                .replace("/", "")
                .replace("\\", "");
        if (!serverUrl.isEmpty() && getMastodonUrl() != null) {
            RegistryHelper.put(RegistryHelper.RegistryKey.MASTODON_SERVER, serverUrl);
            return getMastodonUrl().replace("{SERVER}", serverUrl);
        }
        return null;
    }

    // mastodonUrl

    private final StringProperty mastodonUrl = new SimpleStringProperty(this, "mastodonUrl");

    public final StringProperty mastodonUrlProperty() {
        return mastodonUrl;
    }

    public final String getMastodonUrl() {
        return mastodonUrl.get();
    }

    public final void setMastodonUrl(String value) {
        mastodonUrlProperty().set(value);
    }

    // on back button clicked

    private ObjectProperty<Runnable> onBack;

    public final ObjectProperty<Runnable> onBackProperty() {
        if (onBack == null) {
            onBack = new SimpleObjectProperty<>(this, "onBack");
        }
        return onBack;
    }

    public final void setOnBack(Runnable value) {
        onBackProperty().set(value);
    }

    public final Runnable getOnBack() {
        return onBack == null ? null : onBackProperty().get();
    }
}
