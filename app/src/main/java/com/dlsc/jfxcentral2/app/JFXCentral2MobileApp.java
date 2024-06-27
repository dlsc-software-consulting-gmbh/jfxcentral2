package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral2.mobile.componenets.BottomMenuBar;
import com.dlsc.jfxcentral2.mobile.pages.MobileLinksOfTheWeekPage;
import com.dlsc.jfxcentral2.mobile.pages.MainPage;
import com.dlsc.jfxcentral2.mobile.pages.MobileHomePage;
import com.dlsc.jfxcentral2.mobile.util.MobileLinkUtil;
import com.dlsc.jfxcentral2.mobile.util.MobileRouter;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import com.gluonhq.attach.display.DisplayService;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.Locale;
import java.util.Objects;

public class JFXCentral2MobileApp extends Application {

    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(Size.LARGE);

    static {
        if (!RepositoryManager.isCountryEqualToChina()) {
            Locale.setDefault(Locale.US);
        }
    }

    private TextField pathField;

    @Override
    public void start(Stage stage) {
        System.setProperty("prism.lcdtext", "false");

        // set jpro.imagemanager.cache to ~/.jfxcentral/imagecache
        System.setProperty("jpro.imagemanager.cache", new File(new File(System.getProperty("user.home")), ".jfxcentral/imagecache").getAbsolutePath());

        MobileRouter router = createMobileRouter();

        if (Boolean.getBoolean("develop")) {
            pathField = new TextField(PagePath.HOME);
            router.currentPathProperty().addListener((obs, oldPath, newPath) -> pathField.setText(newPath));
            pathField.setOnAction(evt -> {
                String path = pathField.getText();
                MobileLinkUtil.getToPage(path);
            });
        }else {
            stage.initStyle(StageStyle.UNDECORATED);
        }

        MainPage mainPage = new MainPage();
        mainPage.sizeProperty().bind(size);

        // add notch pane
        StackPane notchPane = new StackPane();
        notchPane.getStyleClass().add("notch-pane");
        VBox.setVgrow(mainPage, Priority.ALWAYS);
        VBox parent = new VBox(notchPane, mainPage);
        if (pathField!=null) {
            parent.getChildren().add(0,pathField);
        }

        DisplayService.create().ifPresent(service -> {
            if (service.hasNotch()) {
                notchPane.getStyleClass().add("notch");
            }
        });

        // scene
        Scene scene = new Scene(parent, 450, 800);
        scene.setFill(Color.web("#070B32"));
        scene.widthProperty().addListener((it -> updateSizeProperty(scene)));
        scene.getStylesheets().add(Objects.requireNonNull(NodeUtil.class.getResource("/com/dlsc/jfxcentral2/theme.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(BottomMenuBar.class.getResource("/com/dlsc/jfxcentral2/mobile/mobile.css")).toExternalForm());
        // scene.focusOwnerProperty().addListener(it -> System.out.println("focus owner: " + scene.getFocusOwner()));
        updateSizeProperty(scene);

        stage.setScene(scene);
        stage.setFullScreenExitHint("");

        // Mike Hearn explicitly requested to use this approach to exit the app
        stage.setOnCloseRequest(evt -> System.exit(0));
        stage.show();
    }

    private void updateSizeProperty(Scene scene) {
        double sceneWidth = scene.getWidth();
        if (sceneWidth < 760) {
            size.set(Size.SMALL);
        } else if (sceneWidth <= 1320) {
            size.set(Size.MEDIUM);
        } else {
            size.set(Size.LARGE);
        }
    }

    private MobileRouter createMobileRouter() {
        return MobileRouter.getInstance()
                .add(PagePath.HOME, ()-> new MobileHomePage(size))
                .add("/home", ()-> new MobileHomePage(size))
                .add("/index", ()-> new MobileHomePage(size))
                .add(PagePath.LINKS, ()-> new MobileLinksOfTheWeekPage(size));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
