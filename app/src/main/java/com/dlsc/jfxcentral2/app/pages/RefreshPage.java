package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral2.app.RepositoryManager;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.model.Size;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import one.jpro.routing.LinkUtil;
import one.jpro.routing.sessionmanager.SessionManager;
import org.eclipse.jgit.lib.ProgressMonitor;

public class RefreshPage extends PageBase {

    private final InvalidationListener invalidationListener = it -> {
        if (RepositoryManager.isRepositoryUpdated()) {
            Platform.runLater(() -> {
                SessionManager sessionManager = LinkUtil.getSessionManager(realContent());
                if (sessionManager != null) {
                    sessionManager.gotoURL("/");
                }
            });
        }
    };

    private final WeakInvalidationListener weakInvalidationListener = new WeakInvalidationListener(invalidationListener);

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

    private final IntegerProperty loadPercentage = new SimpleIntegerProperty(this, "loadPercentage", 0);

    private final StringProperty loadMessage = new SimpleStringProperty(this, "loadMessage", "");

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
            performUpdate();
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
            performUpdate();
        });

        Button exitButton = new Button("EXIT");
        exitButton.setFocusTraversable(false);
        exitButton.setCancelButton(true);
        exitButton.setOnAction(evt -> Platform.exit());

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
        label.textProperty().bind(Bindings.createStringBinding(() -> {
            if (loadMessage.get().toLowerCase().startsWith("pull")) {
                return "Checking for updates ...";
            }

            if (loadPercentage.get() == -1) {
                return loadMessage.get();
            }

            return loadMessage.get() + " " + loadPercentage.get() + "%";
        }, loadMessage, loadPercentage));
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        VBox vBox = new VBox(logo, label);
        vBox.getStyleClass().add("inner-box");
        StackPane.setAlignment(vBox, Pos.CENTER);

        StackPane wrapper = new StackPane(vBox);
        wrapper.getStyleClass().add("update-view");

        return wrapper;
    }

    private void performUpdate() {
        Thread thread = new Thread(() -> RepositoryManager.updateRepository(new ProgressMonitor() {

            double total;
            double acc;

            @Override
            public void start(int i) {
            }

            @Override
            public void beginTask(String taskName, int totalWork) {
                Platform.runLater(() -> {
                    loadMessage.set(taskName);
                    loadPercentage.set(0);
                });
                acc = 0;
                total = totalWork;
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
                DataRepository2.getInstance().reload();
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
