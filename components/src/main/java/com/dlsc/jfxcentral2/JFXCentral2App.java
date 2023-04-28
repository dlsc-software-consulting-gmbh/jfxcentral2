package com.dlsc.jfxcentral2;

import com.dlsc.jfxcentral2.components.CopyrightView;
import com.dlsc.jfxcentral2.components.FooterView;
import com.dlsc.jfxcentral2.components.HomePageTopView;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.SponsorsView;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.components.WebsiteChangesView;
import com.dlsc.jfxcentral2.components.WeekLinksLiteView;
import com.dlsc.jfxcentral2.model.DateQuickLink;
import com.dlsc.jfxcentral2.model.ImageQuickLink;
import com.dlsc.jfxcentral2.model.QuickLink;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class JFXCentral2App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // menubar
        TopMenuBar topMenuBar = new TopMenuBar();
        topMenuBar.sizeProperty().bind(sizeProperty());

        // HomePage TopView
        HomePageTopView homePageTopView = new HomePageTopView();
        homePageTopView.sizeProperty().bind(sizeProperty());

        StackPane welcomeStackPane = new StackPane(homePageTopView, topMenuBar);
        StackPane.setAlignment(topMenuBar, Pos.TOP_CENTER);

        // links of the Week
        WeekLinksLiteView weekLinksLiteView = new WeekLinksLiteView();
        weekLinksLiteView.sizeProperty().bind(sizeProperty());
        weekLinksLiteView.setMdString("""
                #### Hello World
                Foojay.io, the website for Friends Of OpenJDK, published the podcast ‘The State of JavaFX Framework, Libraries, and Projects’. These guests spoke about the JavaFX framework itself, but also about the libraries and applications that are built with it:
                > Pedro Duque Vieira
                                
                Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n
                Are you considering to write a JavaFX Mastodon client? Take a look at the Bigbone project, that aims to implement the Mastodon API project by André Gasser. \n""");

        // website changes
        WebsiteChangesView websiteChangesView = new WebsiteChangesView();
        websiteChangesView.sizeProperty().bind(sizeProperty());
        websiteChangesView.getQuickLinks().setAll(generateQuickLinks());

        // sponsors
        SponsorsView sponsorsView = new SponsorsView();
        sponsorsView.sizeProperty().bind(sizeProperty());

        // footer
        FooterView footerView = new FooterView();
        footerView.sizeProperty().bind(sizeProperty());

        // copyright
        CopyrightView copyrightView = new CopyrightView();
        copyrightView.sizeProperty().bind(sizeProperty());

        VBox uiBox = new VBox(welcomeStackPane, weekLinksLiteView, websiteChangesView, sponsorsView, footerView, copyrightView);
        uiBox.getStyleClass().add("ui");
        uiBox.setAlignment(Pos.BOTTOM_CENTER);
        uiBox.setMaxWidth(Region.USE_PREF_SIZE);
//        uiBox.prefWidthProperty().bind(Bindings.createDoubleBinding(() -> switch (getSize()) {
//            case SMALL -> 375d;
//            case MEDIUM -> 768d;
//            case LARGE -> 1445d;
//        }, sizeProperty()));
//        uiBox.minWidthProperty().bind(uiBox.prefWidthProperty());
//        uiBox.maxWidthProperty().bind(uiBox.prefWidthProperty());

        StackPane.setAlignment(uiBox, Pos.TOP_CENTER);

        StackPane background = new StackPane(uiBox);
        background.getStyleClass().add("background");

        ScrollPane scrollPane = new ScrollPane(background);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane);
        scene.getStylesheets().add(JFXCentral2App.class.getResource("theme.css").toExternalForm());

        primaryStage.setTitle("JFXCentral 2");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();

        SizeComboBox sizeComboBox = new SizeComboBox();
        sizeComboBox.valueProperty().bindBidirectional(sizeProperty());

        VBox controlsBox = new VBox(sizeComboBox);
        controlsBox.getStyleClass().add("controls");

        Scene controlsScene = new Scene(controlsBox);
        controlsScene.getStylesheets().add(JFXCentral2App.class.getResource("theme.css").toExternalForm());

        Stage controlsStage = new Stage(StageStyle.UTILITY);
        controlsStage.setTitle("Controls");
        controlsStage.setAlwaysOnTop(true);
        controlsStage.setScene(controlsScene);
        controlsStage.sizeToScene();
        controlsStage.setX(100);
        controlsStage.setY(100);
        controlsStage.show();

        CSSFX.start();
    }

    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(this, "size", Size.LARGE);

    public Size getSize() {
        return size.get();
    }

    public ObjectProperty<Size> sizeProperty() {
        return size;
    }

    public void setSize(Size size) {
        this.size.set(size);
    }

    private List<QuickLink> generateQuickLinks() {
        List<QuickLink> quickLinks = new ArrayList<>();
        Random random = new Random();
        int imageQuickLinkCount = random.nextInt(2) + 2;
        int dateQuickLinkCount = random.nextInt(2) + 3;
        int nullCount = 7 - imageQuickLinkCount - dateQuickLinkCount;
        List<String> imageUrlList = new ArrayList<>();
        //currently there are only 3 images for testing
        for (int i = 0; i < 3; i++) {
            imageUrlList.add("/com/dlsc/jfxcentral2/demoimages/quick-link-lg" + i + ".png");
        }
        Collections.shuffle(imageUrlList);
        imageUrlList.subList(0, imageQuickLinkCount).forEach(url -> {
            quickLinks.add(new ImageQuickLink(JFXCentral2App.class.getResource(url).toExternalForm()));
        });

        for (int i = 0; i < dateQuickLinkCount; i++) {
            quickLinks.add(new DateQuickLink("JDKMon", "Download", null, "xxx url...", ZonedDateTime.now().plusDays(i)));
        }
        for (int i = 0; i < nullCount; i++) {
            quickLinks.add(null);
        }
        Collections.shuffle(quickLinks);
        return quickLinks;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
