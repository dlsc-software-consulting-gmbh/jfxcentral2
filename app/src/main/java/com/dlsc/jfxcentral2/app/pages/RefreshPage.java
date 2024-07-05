package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.app.RepositoryManager;
import com.dlsc.jfxcentral2.app.utils.RepositoryUpdater;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.model.Size;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import one.jpro.platform.routing.sessionmanager.SessionManager;

public class RefreshPage extends PageBase {

    /**
     * The invalidation listener is called from the RepositoryManager thread,
     * so any changes done to the UI must be in Platform.runLater.
     */
    private final InvalidationListener invalidationListener = it -> {
        if (RepositoryManager.isRepositoryUpdated()) {
            Platform.runLater(() -> {
                SessionManager sessionManager = getSessionManager();
                if (sessionManager != null) {
                    sessionManager.gotoURL("/");
                }
            });
        }
    };

    private final WeakInvalidationListener weakInvalidationListener = new WeakInvalidationListener(invalidationListener);
    private final RepositoryUpdater repositoryUpdater = new RepositoryUpdater();

    private Node updateView;
    private Node setupView;

    public RefreshPage(ObjectProperty<Size> size) {
        super(size, Mode.DARK);
    }

    @Override
    public String title() {
        return "JFXCentral Refresh";
    }

    @Override
    public String description() {
        return "A central place for anything related to JavaFX.";
    }

    @Override
    public boolean fullscreen() {
        return true;
    }

    @Override
    public Node content() {
        Platform.runLater(() -> {
            RepositoryManager.repositoryUpdatedProperty().addListener(weakInvalidationListener);
            invalidationListener.invalidated(null);
        });

        updateView = createUpdateView();
        setupView = createSetupView();

        updateView.setVisible(!RepositoryManager.isFirstTimeSetup());
        updateView.setManaged(!RepositoryManager.isFirstTimeSetup());
        setupView.setVisible(RepositoryManager.isFirstTimeSetup());
        setupView.setVisible(RepositoryManager.isFirstTimeSetup());

        if (!RepositoryManager.isFirstTimeSetup()) {
            repositoryUpdater.performUpdate(true);
        }

        return new StackPane(updateView, setupView);
    }

    private Node createSetupView() {
        CustomImageView logo = new CustomImageView();
        logo.getStyleClass().addAll("jfx-central-logo", "color", "small");

        Label label1 = new Label("This seems to be the first time the application is running on your system. For JFXCentral to work it first needs to checkout its data repository. The repository is several hundred megabytes in size and checkout might take a while based on your system.");
        label1.setWrapText(true);
        label1.setMinHeight(Region.USE_PREF_SIZE);

        Label label2 = new Label("Proceed with checkout or exit?");

        Button proceedButton = new Button("PROCEED");
        proceedButton.setFocusTraversable(false);
        proceedButton.setDefaultButton(true);
        proceedButton.setOnAction(evt -> {
            updateView.setVisible(true);
            updateView.setManaged(true);
            setupView.setVisible(false);
            setupView.setManaged(false);
            repositoryUpdater.performUpdate(false);
        });

        Button exitButton = new Button("EXIT");
        exitButton.setFocusTraversable(false);
        exitButton.setCancelButton(true);

        // use system exit so it works properly everywhere, including native
        exitButton.setOnAction(evt -> System.exit(0));

        HBox hBox = new HBox(exitButton, proceedButton);
        hBox.getStyleClass().add("button-box");

        VBox vBox = new VBox(logo, label1, label2, hBox);
        vBox.getStyleClass().add("inner-box");
        StackPane.setAlignment(vBox, Pos.CENTER);

        StackPane wrapper = new StackPane(vBox);
        wrapper.getStyleClass().add("setup-view");

        return wrapper;
    }

    private Node createUpdateView() {
        CustomImageView logo = new CustomImageView();
        logo.getStyleClass().addAll("jfx-central-logo", "color", "small");

        Label label = new Label();
        label.setTextAlignment(TextAlignment.CENTER);
        label.textProperty().bind(Bindings.createStringBinding(() -> {
            if (repositoryUpdater.getLoadMessage().toLowerCase().startsWith("pull")) {
                return "Checking for updates ...";
            }

            if (repositoryUpdater.getLoadPercentage() == -1) {
                return repositoryUpdater.getLoadMessage();
            }

            return repositoryUpdater.getLoadMessage() + " " + repositoryUpdater.getLoadPercentage() + "%";
        }, repositoryUpdater.loadMessageProperty(), repositoryUpdater.loadPercentageProperty()));
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        VBox vBox = new VBox(logo, label);
        vBox.getStyleClass().add("inner-box");
        StackPane.setAlignment(vBox, Pos.CENTER);

        StackPane wrapper = new StackPane(vBox);
        wrapper.getStyleClass().add("update-view");

        return wrapper;
    }
}
