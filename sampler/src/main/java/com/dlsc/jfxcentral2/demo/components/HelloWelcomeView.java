package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.WelcomeView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloWelcomeView extends JFXCentralSampleBase {

    private WelcomeView welcomeView;

    @Override
    public String getSampleName() {
        return "WelcomeView";
    }

    @Override
    public Region createControl() {
        welcomeView = new WelcomeView(false);
        return new ScrollPane(welcomeView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);
        welcomeView.sizeProperty().bind(sizeComboBox.sizeProperty());
        return new VBox(10, new Label("Change size:"), sizeComboBox);
    }
}
