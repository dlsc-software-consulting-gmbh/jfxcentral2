package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.LOGGER;
import com.dlsc.jfxcentral2.utils.RegistryHelper;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.awt.*;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MastodonShareView extends Dialog<String> {

    private final String url;
    private final TextField serverAddress;

    public MastodonShareView(String url) {
        this.url = url;

        setTitle("Share on Mastodon");

        // Set up the dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("What is your Mastodon server URL?"), 0, 0);
        grid.add(new Label("E.g. mastodon.social, hachyderm.io,..."), 0, 1);

        serverAddress = new TextField();
        serverAddress.setText(RegistryHelper.get(RegistryHelper.RegistryKey.MASTODON_SERVER));
        grid.add(serverAddress, 1, 0);

        // Add the content to the dialog
        getDialogPane().setContent(grid);

        // Set up the dialog buttons
        getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.APPLY));
        getDialogPane().getButtonTypes().add(new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE));

        // Result handler
        setResultConverter(this::handleButtonClick);
    }

    private String handleButtonClick(ButtonType buttonType) {
        if (buttonType != null && buttonType.getButtonData() == ButtonBar.ButtonData.APPLY) {
            String serverUrl = serverAddress.getText()
                    .trim()
                    .replace("http://", "")
                    .replace("https://", "")
                    .replace("/", "")
                    .replace("\\", "");
            if (!serverUrl.isEmpty()) {
                RegistryHelper.put(RegistryHelper.RegistryKey.MASTODON_SERVER, serverUrl);
                String shareUrl = url.replace("{SERVER}", serverUrl);
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(new URI(shareUrl));
                    }
                } catch (Exception e) {
                    LOGGER.error("Can't open browser: {}", e.getMessage());
                }
            }
        }
        return "";
    }
}
