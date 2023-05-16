package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.tiles.AppTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloAppTileView extends JFXCentralSampleBase {

    private AppTileView appTileView;

    @Override
    protected Region createControl() {
        appTileView = new AppTileView();
        RealWorldApp app = new RealWorldApp();
        app.setName("App Name");
        app.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore.");
        appTileView.setData(app);
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
