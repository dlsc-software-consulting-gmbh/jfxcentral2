package com.dlsc.jfxcentral2.components;

import javafx.scene.control.*;
import javafx.scene.layout.*;

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
                return serverUrl;
            }
        }
        return null;
    }
}
