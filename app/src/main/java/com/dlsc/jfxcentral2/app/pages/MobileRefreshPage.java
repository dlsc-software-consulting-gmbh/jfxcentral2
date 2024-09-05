package com.dlsc.jfxcentral2.app.pages;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import animatefx.animation.GlowBackground;
import animatefx.animation.SlideOutUp;
import animatefx.animation.Tada;
import animatefx.animation.Wobble;
import com.dlsc.jfxcentral2.app.RepositoryManager;
import com.dlsc.jfxcentral2.app.utils.RepositoryUpdater;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.mobile.components.IntroPane;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import one.jpro.jproutils.treeshowing.TreeShowing;

public class MobileRefreshPage extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "mobile-refresh-page";

    /**
     * The invalidation listener is called from the RepositoryManager thread,
     * so any changes done to the UI must be in Platform.runLater.
     */
    private final InvalidationListener invalidationListener = it -> {
        if (RepositoryManager.isRepositoryUpdated()) {
            Platform.runLater(() -> {
                if (isGoToHomePage()) {
                    MobileLinkUtil.getToPage(PagePath.HOME);
                }
            });
        }
    };

    private boolean goToHomePage = false;
    private final WeakInvalidationListener weakInvalidationListener = new WeakInvalidationListener(invalidationListener);
    private final RepositoryUpdater repositoryUpdater = new RepositoryUpdater();
    private final CustomImageView logo;

    public MobileRefreshPage() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Platform.runLater(() -> {
            RepositoryManager.repositoryUpdatedProperty().addListener(weakInvalidationListener);
            invalidationListener.invalidated(null);
        });

        logo = new CustomImageView();
        logo.getStyleClass().addAll("jfx-central-logo", "color");

        boolean firstTimeSetup = RepositoryManager.isFirstTimeSetup();

        if (firstTimeSetup) {
            setupFirstTimeUI();
        } else {
            goToHomePage = true;
            setupUpdateUI();
        }
    }

    private boolean isGoToHomePage() {
        return goToHomePage;
    }

    private void setupFirstTimeUI() {
        // center part (intro pane)
        IntroPane introPane = new IntroPane();
        VBox.setVgrow(introPane, Priority.ALWAYS);

        Label loadLabel = new Label();
        loadLabel.getStyleClass().add("load-label");
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
        Button startButton = new Button("Start");
        startButton.getStyleClass().add("start-button");

        startButton.setOnAction(evt -> {
            FadeOut fadeOut = new FadeOut(startButton);
            fadeOut.setSpeed(2);

            FadeIn fadeIn = new FadeIn(loadLabel);
            fadeIn.setSpeed(2);

            fadeOut.setOnFinished(e -> {
                startButton.setVisible(false);
                loadLabel.setVisible(true);
                fadeIn.play();
            });

            fadeOut.play();

            goToHomePage = true;

            if (RepositoryManager.isRepositoryUpdated()) {
                Platform.runLater(() -> {
                    MobileLinkUtil.getToPage(PagePath.HOME);
                });
            }
        });

        StackPane bottomBox = new StackPane(startButton, loadLabel);
        bottomBox.getStyleClass().add("bottom-box");

        VBox content = new VBox(logo, introPane, bottomBox);
        content.getStyleClass().add("content-box");
        getChildren().add(content);

        repositoryUpdater.performUpdate(false);
    }

    private void setupUpdateUI() {
        Label tipsLabel = new Label("Checking for updates ...");

        Region dividingLine = new Region();
        dividingLine.getStyleClass().add("dividing-line");

        VBox updateContentBox = new VBox(logo, dividingLine, tipsLabel);
        updateContentBox.getStyleClass().add("update-content-box");
        updateContentBox.setMaxHeight(Region.USE_PREF_SIZE);

        getChildren().add(updateContentBox);

        // start the update process
        repositoryUpdater.performUpdate(true);
    }

}
