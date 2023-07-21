package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.IkonUtil;



import com.jpro.webapi.WebAPI;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class WelcomeView extends PaneBase {

    private final VBox labelBox;
    private final FlowPane flowPane;
    private final Button openJFXProjectButton;
    private final Button installLocallyButton;
    private final Button jfxcentralDataButton;
    private final Button jfxCentralButton;
    private double xOffset;
    private double yOffset;

    public WelcomeView(boolean mobile) {
        getStyleClass().add("welcome-view");

        labelBox = new VBox();
        labelBox.getStyleClass().add("label-box");

        Label label1 = new Label("Home to anything");
        label1.getStyleClass().add("home-label");

        CustomImageView fxLogo = new CustomImageView();
        fxLogo.getStyleClass().add("fx-logo");

        Label label2 = new Label("related", fxLogo);
        label2.getStyleClass().add("related-label");

        Label label3 = new Label("JFX Central is an open source project that you can find on GitHub, with its content in a separate data repository. Feel free to add your project, documentation, book, etc. via a simple pull-request!");
        label3.getStyleClass().add("description-label");
        label3.setWrapText(true);
        label3.setMinHeight(Region.USE_PREF_SIZE);

        labelBox.getChildren().addAll(label1, label2, label3);
        labelBox.setMinHeight(Region.USE_PREF_SIZE);

        flowPane = new FlowPane();
        flowPane.getStyleClass().add("flow-pane");
        jfxCentralButton = new Button("jfxcentral");
        jfxCentralButton.setGraphic(new FontIcon(IkonUtil.github));
        jfxCentralButton.getStyleClass().addAll("transparent-button", "jfxcentral-button");
        LinkUtil.setExternalLink(jfxCentralButton, "https://github.com/dlemmermann/jfxcentral2");

        jfxcentralDataButton = new Button("jfxcentral-data", new FontIcon(IkonUtil.github));
        jfxcentralDataButton.getStyleClass().addAll("transparent-button", "jfxcentral-data-button");
        LinkUtil.setExternalLink(jfxcentralDataButton, "https://github.com/dlemmermann/jfxcentral-data");

        Region downloadRegion = new Region();
        downloadRegion.getStyleClass().add("download-region");
        installLocallyButton = new Button("Install locally", downloadRegion);
        installLocallyButton.getStyleClass().addAll("fill-button", "install-button");
        LinkUtil.setExternalLink(installLocallyButton, "https://downloads.hydraulic.dev/jfxcentral2/download.html");
        installLocallyButton.setVisible(!mobile);
        installLocallyButton.setManaged(!mobile);

        Region openjfxRegion = new Region();
        openjfxRegion.getStyleClass().add("openjfx-region");
        openJFXProjectButton = new Button("OpenJFX project", openjfxRegion);
        openJFXProjectButton.getStyleClass().addAll("fill-button", "openjfx-button");
        LinkUtil.setLink(openJFXProjectButton, "/openjfx");
        setMinHeight(Region.USE_PREF_SIZE);

        if (!WebAPI.isBrowser()) {
            setOnMousePressed(event -> {
                xOffset = getScene().getWindow().getX() - event.getScreenX();
                yOffset = getScene().getWindow().getY() - event.getScreenY();
            });

            setOnMouseDragged(event -> {
                getScene().getWindow().setX(event.getScreenX() + xOffset);
                getScene().getWindow().setY(event.getScreenY() + yOffset);
            });
        }

        layoutBySize();
    }

    protected void layoutBySize() {
        if (isMedium()) {
            flowPane.getChildren().setAll(jfxCentralButton, installLocallyButton, jfxcentralDataButton, openJFXProjectButton);
        } else {
            flowPane.getChildren().setAll(jfxCentralButton, jfxcentralDataButton, installLocallyButton, openJFXProjectButton);
        }
        Pane content = isLarge() ? new HBox() : new VBox();
        content.getStyleClass().add("content");
        content.getChildren().setAll(labelBox, flowPane);
        getChildren().setAll(content);
    }
}
