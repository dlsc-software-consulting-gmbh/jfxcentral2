package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.CustomTabPane;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.CustomTab;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class HelloCustomTabPane extends JFXCentralSampleBase {

    private CustomTabPane customTabPane;

    @Override
    protected Region createControl() {
        customTabPane = new CustomTabPane();
        customTabPane.getTabs().setAll(
                new CustomTab("Tab 1", new Label("This is the content of tab 1.")),
                new CustomTab("Tab 2", new Label("This is the content of tab 2.")),
                new CustomTab("Tab 3", new Label("This is the content of tab 3."))
        );
        return customTabPane;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        customTabPane.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "CustomTabPane";
    }
}
