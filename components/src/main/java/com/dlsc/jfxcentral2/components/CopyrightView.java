package com.dlsc.jfxcentral2.components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CopyrightView extends PaneBase {

    private final HBox poweredByBox;
    private final Label copyrightLabel;

    public CopyrightView() {
        getStyleClass().add("copyright-view");

        copyrightLabel = new Label("© 2023 DLSC Software & Consulting GmbH");

        Region leftCurlyBraces = new Region();
        leftCurlyBraces.getStyleClass().addAll("curly-braces","left");
        Region rightCurlyBraces = new Region();
        rightCurlyBraces.getStyleClass().addAll("curly-braces","right");
        poweredByBox = new HBox(
                new Label("Powered by"),
                leftCurlyBraces,
                new Label("JPro"),
                rightCurlyBraces
        );
        poweredByBox.getStyleClass().add("powered-by-box");

        layoutBySize();
    }

    protected void layoutBySize() {
        if (isSmall()) {
            VBox box = new VBox(copyrightLabel,poweredByBox);
            box.getStyleClass().add("content");
            getChildren().setAll(box);
        }else {
            HBox box = new HBox(copyrightLabel,new AutoGrowRegion(), poweredByBox);
            box.getStyleClass().add("content");
            getChildren().setAll(box);
        }
    }
}
