package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral2.app.RepositoryManager;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.mobile.components.WelcomePageView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.eclipse.jgit.lib.ProgressMonitor;

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
            if (loadMessage.get().toLowerCase().startsWith("pull")) {
                return "Checking for updates ...";
            }

            if (loadPercentage.get() == -1) {
                return loadMessage.get();
            }

            return loadMessage.get() + " " + loadPercentage.get() + "%";
        }, loadMessage, loadPercentage));

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
            performUpdate(false);
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
            performUpdate(true);
        }
    }

    private final IntegerProperty loadPercentage = new SimpleIntegerProperty(this, "loadPercentage", 0);

    private final StringProperty loadMessage = new SimpleStringProperty(this, "loadMessage", "");

    /*
     * The "refresh" parameter states whether the update is called due to the first setup of the
     * data repository or because of a call to the refresh page.
     */
    private void performUpdate(boolean refresh) {
        Thread thread = new Thread(() -> RepositoryManager.updateRepository(new ProgressMonitor() {

            double total;
            double acc;

            @Override
            public void showDuration(boolean b) {
            }

            @Override
            public void start(int i) {
            }

            @Override
            public void beginTask(String taskName, int totalWork) {
                // clean up some messy message we get
                final String name = taskName.replace("remote: ", "");

                if (totalWork == -1) {
                    Platform.runLater(() -> {
                        loadMessage.set(name);
                        loadPercentage.set(-1);
                    });
                } else {
                    Platform.runLater(() -> {
                        loadMessage.set(name);
                        loadPercentage.set(0);
                    });
                    acc = 0;
                    total = totalWork;
                }
            }

            @Override
            public void update(int completed) {
                if (total != ProgressMonitor.UNKNOWN) {
                    acc += completed;
                    int p = (int) ((acc / total) * 100);
                    Platform.runLater(() -> loadPercentage.set(p));
                } else {
                    Platform.runLater(() -> loadPercentage.set(-1));
                }
            }

            @Override
            public void endTask() {
                if (refresh) {
                    DataRepository2.getInstance().reload();
                }
            }

            @Override
            public boolean isCancelled() {
                return false;
            }
        }));

        thread.setName("Repository Thread");
        thread.setDaemon(true);
        thread.start();
    }
}
