package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.AppTileView;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.tiles.AppTileData;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloAppTileView extends JFXCentralSampleBase {

    private AppTileView appTileView;

    @Override
    protected Region createControl() {
        appTileView = new AppTileView();
        appTileView.setData(new AppTileData("App Name", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore.", new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/app-thumbnail-01.png").toExternalForm()), "https://www.dlsc.com", false, true));
        return new StackPane(appTileView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        appTileView.sizeProperty().bind(sizeComboBox.valueProperty());
        return new VBox(10, sizeComboBox);
    }

    @Override
    public String getSampleName() {
        return "AppTileView";
    }
}
