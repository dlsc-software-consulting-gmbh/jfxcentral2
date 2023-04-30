package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.OpenJFXProjectView;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloOpenJFXProjectView extends JFXCentralSampleBase {

    private OpenJFXProjectView openJFXProjectView;

    @Override
    protected Region createControl() {
        openJFXProjectView = new OpenJFXProjectView();
        return new ScrollPane(openJFXProjectView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);
        openJFXProjectView.sizeProperty().bind(sizeComboBox.sizeProperty());
        return new VBox(10, new Label("Change size:"), sizeComboBox);
    }

    @Override
    public String getSampleName() {
        return "OpenJFXProjectView";
    }

}
