package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.QuickLinksGenerator;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HomePageTopView extends PaneBase {

    public HomePageTopView() {
        getStyleClass().add("home-page-top-view");

        WelcomeView welcomeView = new WelcomeView(isMobile());
        welcomeView.sizeProperty().bind(sizeProperty());
        StackPane.setAlignment(welcomeView, Pos.TOP_CENTER);

        FeaturesContainer featuresContainer = new FeaturesContainer();
        featuresContainer.sizeProperty().bind(sizeProperty());

        QuickLinksContainer quickLinksContainer = new QuickLinksContainer();
        quickLinksContainer.sizeProperty().bind(sizeProperty());
        quickLinksContainer.getQuickLinks().setAll(QuickLinksGenerator.generateQuickLinks(getSize()));
        sizeProperty().addListener((ob, ov, nv) -> quickLinksContainer.getQuickLinks().setAll(QuickLinksGenerator.generateQuickLinks(nv)));

        VBox box = new VBox(featuresContainer, quickLinksContainer);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("features-links-box");
        StackPane.setAlignment(box, Pos.BOTTOM_CENTER);
        getChildren().setAll(welcomeView,box);
    }
}
