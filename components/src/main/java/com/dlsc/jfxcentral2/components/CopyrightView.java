package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.LinkUtil;

import java.time.Year;

public class CopyrightView extends PaneBase {

    private final HBox poweredByBox;
    private final Label copyrightLabel;

    public CopyrightView() {
        getStyleClass().add("copyright-view");

        copyrightLabel = new Label("© " + Year.now() + " DLSC Software & Consulting GmbH");
        ExternalLinkUtil.setExternalLink(copyrightLabel, "https://dlsc.com");

        Region leftCurlyBraces = new Region();
        leftCurlyBraces.getStyleClass().addAll("curly-braces", "left");

        Region rightCurlyBraces = new Region();
        rightCurlyBraces.getStyleClass().addAll("curly-braces", "right");

        Label jproLabel = new Label("JPro");
        jproLabel.getStyleClass().add("jpro-label");

        Label poweredByLabel = new Label("Powered by");
        poweredByLabel.getStyleClass().add("powered-by-label");

        poweredByBox = new HBox(poweredByLabel, leftCurlyBraces, jproLabel, rightCurlyBraces);
        poweredByBox.getStyleClass().add("powered-by-box");
        ExternalLinkUtil.setExternalLink(poweredByBox, "https://www.jpro.one");

        updateUI();
    }

    protected void layoutBySize() {
        if (!isLgToMdOrMdToLg()) {
            updateUI();
        }
    }

    private void updateUI() {
        if (isSmall()) {
            VBox box = new VBox(copyrightLabel, poweredByBox);
            box.getStyleClass().add("content");
            getChildren().setAll(box);
        } else {
            HBox box = new HBox(copyrightLabel, new Spacer(), poweredByBox);
            box.getStyleClass().add("content");
            getChildren().setAll(box);
        }
    }
}
