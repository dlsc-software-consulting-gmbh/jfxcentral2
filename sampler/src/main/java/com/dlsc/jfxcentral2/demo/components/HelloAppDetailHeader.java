package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.AppDetailHeader;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.App;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloAppDetailHeader extends JFXCentralSampleBase {

    private AppDetailHeader appDetailHeader;

    @Override
    protected Region createControl() {
        appDetailHeader = new AppDetailHeader(new App("JFXCentral", "https://www.jfx-central.com/home", false, true));
        return new ScrollPane(new StackPane(appDetailHeader));
    }

    @Override
    public Node getControlPanel() {
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton largeBox = new RadioButton("Large");
        RadioButton mediumBox = new RadioButton("Medium");
        toggleGroup.getToggles().addAll(largeBox, mediumBox);
        toggleGroup.selectedToggleProperty().addListener((ob, ov, nv) -> {
            if (nv == largeBox) {
                appDetailHeader.setSize(Size.LARGE);
            } else if (nv == mediumBox) {
                appDetailHeader.setSize(Size.MEDIUM);
            }
        });
        mediumBox.setSelected(true);
        return new VBox(20, largeBox, mediumBox);
    }

    @Override
    public String getSampleName() {
        return "AppDetailHeader";
    }
}
