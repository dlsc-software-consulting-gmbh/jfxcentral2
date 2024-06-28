package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.mobile.componenets.BottomMenuBar;
import com.dlsc.jfxcentral2.mobile.componenets.MobileDevelopToolBar;
import com.dlsc.jfxcentral2.mobile.pages.MainPage;
import com.dlsc.jfxcentral2.mobile.pages.MobileHomePage;
import com.dlsc.jfxcentral2.mobile.pages.MobileLinksOfTheWeekPage;
import com.dlsc.jfxcentral2.mobile.pages.category.MobileLibrariesCategoryPage;
import com.dlsc.jfxcentral2.mobile.pages.category.MobilePeopleCategoryPage;
import com.dlsc.jfxcentral2.mobile.pages.category.MobileShowcasesCategoryPage;
import com.dlsc.jfxcentral2.mobile.pages.details.MobileLibraryDetailsPage;
import com.dlsc.jfxcentral2.mobile.pages.details.MobilePersonDetailsPage;
import com.dlsc.jfxcentral2.mobile.pages.details.MobileShowcaseMobileDetailsPage;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.MobileRoute;
import com.dlsc.jfxcentral2.utils.MobileRouter;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import com.gluonhq.attach.display.DisplayService;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;

public class JFXCentral2MobileApp extends Application {

    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(Size.LARGE);

    static {
        if (!RepositoryManager.isCountryEqualToChina()) {
            Locale.setDefault(Locale.US);
        }
    }

    private MobileDevelopToolBar developToolBar;

    @Override
    public void start(Stage stage) {
        System.setProperty("prism.lcdtext", "false");

        // set jpro.imagemanager.cache to ~/.jfxcentral/imagecache
        System.setProperty("jpro.imagemanager.cache", new File(new File(System.getProperty("user.home")), ".jfxcentral/imagecache").getAbsolutePath());
        System.setProperty("run.on.mobile", "true");

        MobileRouter router = createMobileRouter();
        if (Boolean.getBoolean("develop")) {
            developToolBar = new MobileDevelopToolBar(router);
        } else {
            stage.initStyle(StageStyle.UNDECORATED);
        }

        MainPage mainPage = new MainPage();
        mainPage.sizeProperty().bind(size);

        // add notch pane
        StackPane notchPane = new StackPane();
        notchPane.getStyleClass().add("notch-pane");
        VBox.setVgrow(mainPage, Priority.ALWAYS);
        VBox parent = new VBox(notchPane, mainPage);
        if (developToolBar != null) {
            parent.getChildren().add(0, developToolBar);
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
                .and(MobileRoute.get(PagePath.HOME, r -> new MobileHomePage(size)))
                .and(MobileRoute.redirect("/index", PagePath.HOME))
                .and(MobileRoute.redirect("/home", PagePath.HOME))
                .and(MobileRoute.get(PagePath.LINKS, r -> new MobileLinksOfTheWeekPage(size)))
                .and(createCategoryOrDetailRoute(PagePath.SHOWCASES, RealWorldApp.class, () -> new MobileShowcasesCategoryPage(size), id -> new MobileShowcaseMobileDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute(PagePath.REAL_WORLD, RealWorldApp.class, () -> new MobileShowcasesCategoryPage(size), id -> new MobileShowcaseMobileDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute(PagePath.LIBRARIES, Library.class, () -> new MobileLibrariesCategoryPage(size), id -> new MobileLibraryDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute(PagePath.PEOPLE, Person.class, () -> new MobilePeopleCategoryPage(size), id -> new MobilePersonDetailsPage(size, id)));
    }

    private MobileRoute createCategoryOrDetailRoute(String path, Class<? extends ModelObject> clazz, Supplier<Node> categoryResponse, Callback<String, Node> detailedResponse) {
        return new MobileRoute(path, url -> {
            int index = url.lastIndexOf("/");
            if (index > 0 && clazz != null) {
                String id = url.substring(index + 1).trim();
                if (!DataRepository2.getInstance().isValidId(clazz, id)) {
                    return new Label("Error: 404");
                }
                return detailedResponse.call(id);
            }
            return categoryResponse.get();
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
