package com.dlsc.jfxcentral2.components;

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
            String serverUrl = serverAddress.getText().trim();
            if (!serverUrl.isEmpty()) {
                RegistryHelper.put(RegistryHelper.RegistryKey.MASTODON_SERVER, serverUrl);

                String shareUrl = String.format(
                        "https://%s/share?text=%s%%0A%s",
                        serverUrl,
                        URLEncoder.encode("Test", StandardCharsets.UTF_8),
                        URLEncoder.encode(url, StandardCharsets.UTF_8)
                );

                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(new URI(shareUrl));
                    }
                } catch (Exception e) {
                    System.err.println("Can't open browser: " + e.getMessage());
                }
            }
        }
        return "";
    }
}
