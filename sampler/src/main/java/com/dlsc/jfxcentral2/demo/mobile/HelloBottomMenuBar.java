package com.dlsc.jfxcentral2.demo.mobile;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.mobile.components.BottomMenuBar;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloBottomMenuBar extends JFXCentralSampleBase {

    private BottomMenuBar bottomMenuBar;

    @Override
    protected Region createControl() {
        bottomMenuBar = new BottomMenuBar();
        return new StackPane(bottomMenuBar);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        bottomMenuBar.sizeProperty().bind(sizeComboBox.sizeProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "BottomMenuBar";
    }
}
