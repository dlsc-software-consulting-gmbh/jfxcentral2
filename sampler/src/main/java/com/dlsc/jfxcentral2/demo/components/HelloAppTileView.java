package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.AppTileView;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.App;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloAppTileView extends JFXCentralSampleBase {

    private AppTileView appTileView;

    @Override
    protected Region createControl() {
        appTileView = new AppTileView();
        appTileView.setData(new App("App Name", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore.", new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/app-thumbnail-01.png").toExternalForm()), "https://www.dlsc.com", false, true));
        return new StackPane(appTileView);
    }

    @Override
    public Node getControlPanel() {
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton largerButton = new RadioButton("Large");
        RadioButton smallerButton = new RadioButton("Small");
        toggleGroup.getToggles().addAll(largerButton, smallerButton);
        largerButton.setSelected(true);
        largerButton.selectedProperty().addListener((ob, ov, nv) ->{
            if (nv) {
                appTileView.setSize(Size.LARGE);
            }
        });
        smallerButton.selectedProperty().addListener((ob, ov, nv) ->{
            if (nv) {
                appTileView.setSize(Size.SMALL);
            }
        });
        return new VBox(10, largerButton, smallerButton);
    }

    @Override
    public String getSampleName() {
        return "AppTileView";
    }
}
