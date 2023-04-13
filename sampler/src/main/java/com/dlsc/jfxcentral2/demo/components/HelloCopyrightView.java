package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.CopyrightView;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloCopyrightView extends JFXCentralSampleBase {

    private CopyrightView copyrightView;

    @Override
    protected Region createControl() {
        copyrightView = new CopyrightView();
        return new ScrollPane(copyrightView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        copyrightView.sizeProperty().bind(sizeComboBox.sizeProperty());
        return new VBox(new Label("Change Size"), sizeComboBox);
    }

    @Override
    public String getSampleName() {
        return "CopyrightView";
    }
}
