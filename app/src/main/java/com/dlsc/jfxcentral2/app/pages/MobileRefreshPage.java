package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.app.RepositoryManager;
import com.dlsc.jfxcentral2.app.utils.RepositoryUpdater;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.mobile.components.WelcomePageView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class MobileRefreshPage extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "mobile-refresh-page";

    /**
     * The invalidation listener is called from the RepositoryManager thread,
     * so any changes done to the UI must be in Platform.runLater.
     */
    private final InvalidationListener invalidationListener = it -> {
        if (RepositoryManager.isRepositoryUpdated()) {
            Platform.runLater(() -> MobileLinkUtil.getToPage(PagePath.HOME));
        }
    };

    private final WeakInvalidationListener weakInvalidationListener = new WeakInvalidationListener(invalidationListener);
    private final RepositoryUpdater repositoryUpdater = new RepositoryUpdater();

    public MobileRefreshPage(ObjectProperty<Size> size) {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Platform.runLater(() -> {
            RepositoryManager.repositoryUpdatedProperty().addListener(weakInvalidationListener);
            invalidationListener.invalidated(null);
        });

        // top part
        WelcomePageView welcomePageView = new WelcomePageView();
        StackPane topBox = new StackPane(welcomePageView);
        topBox.getStyleClass().add("top-box");

        Label loadLabel = new Label("Progress download github repository. 38%");
        loadLabel.getStyleClass().add("load-label");
        loadLabel.setManaged(false);
        loadLabel.setVisible(false);
        loadLabel.setTextAlignment(TextAlignment.CENTER);
        loadLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            if (repositoryUpdater.getLoadMessage().toLowerCase().startsWith("pull")) {
                return "Checking for updates ...";
            }

            if (repositoryUpdater.getLoadPercentage() == -1) {
                return repositoryUpdater.getLoadMessage();
            }

            return repositoryUpdater.getLoadMessage() + " " + repositoryUpdater.getLoadPercentage() + "%";
        }, repositoryUpdater.loadMessageProperty(), repositoryUpdater.loadPercentageProperty()));

        // bottom part
        Button startButton = new Button("Get Started");
        startButton.getStyleClass().add("start-button");
        startButton.setVisible(RepositoryManager.isFirstTimeSetup());
        startButton.setVisible(RepositoryManager.isFirstTimeSetup());
        startButton.setOnAction(evt -> {
            startButton.setVisible(false);
            startButton.setManaged(false);
            loadLabel.setVisible(true);
            loadLabel.setManaged(true);
            repositoryUpdater.performUpdate(false);
        });

        Label welcomeLabel = new Label("Welcome to JFXCentral");
        welcomeLabel.getStyleClass().add("welcome-label");
        welcomeLabel.managedProperty().bind(welcomeLabel.visibleProperty());
        welcomeLabel.visibleProperty().bind(startButton.visibleProperty().not());

        Label descLabel = new Label("Home to anything related to JavaFX.");
        descLabel.getStyleClass().add("desc-label");
        descLabel.managedProperty().bind(descLabel.visibleProperty());
        descLabel.visibleProperty().bind(startButton.visibleProperty().not());

        VBox bottomBox = new VBox(welcomeLabel, descLabel, startButton, loadLabel);
        bottomBox.getStyleClass().add("bottom-box");
        VBox.setVgrow(bottomBox, Priority.ALWAYS);

        CustomImageView logo = new CustomImageView();
        logo.getStyleClass().addAll("jfx-central-logo", "color");

        VBox content = new VBox(topBox, bottomBox, logo);
        content.getStyleClass().add("content-box");
        getChildren().add(content);

        if (!RepositoryManager.isFirstTimeSetup()) {
            repositoryUpdater.performUpdate(true);
        }
    }

}
