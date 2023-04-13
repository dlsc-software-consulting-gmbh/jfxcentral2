package com.dlsc.jfxcentral2;

import com.dlsc.jfxcentral2.components.AutoGrowRegion;
import com.dlsc.jfxcentral2.components.CopyrightView;
import com.dlsc.jfxcentral2.components.FooterView;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.SponsorsView;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JFXCentral2App extends Application {

    @Override
    public void start(Stage primaryStage) {
        //top menubar
        TopMenuBar topMenuBar = new TopMenuBar();
        topMenuBar.sizeProperty().bind(sizeProperty());

        // sponsor view
        SponsorsView sponsorsView = new SponsorsView();
        sponsorsView.sizeProperty().bind(sizeProperty());
        sponsorsView.showLogoCountProperty().bind(Bindings.createIntegerBinding(() -> switch (getSize()) {
            case SMALL -> 2;
            case MEDIUM -> 3;
            case LARGE -> 5;
        }, sizeProperty()));

        // footer view
        FooterView footerView = new FooterView();
        footerView.sizeProperty().bind(sizeProperty());

        // copyright view
        CopyrightView copyrightView = new CopyrightView();
        copyrightView.sizeProperty().bind(sizeProperty());

        VBox uiBox = new VBox(topMenuBar,new AutoGrowRegion(Orientation.VERTICAL), sponsorsView, footerView,copyrightView);
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

    public static void main(String[] args) {
        launch(args);
    }
}
