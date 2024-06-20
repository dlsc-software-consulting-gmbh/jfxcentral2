package com.dlsc.jfxcentral2.demo.mobile;

import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.mobile.BottomMenuBar;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
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
        ComboBox<Size> sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll(Size.values());
        sizeComboBox.valueProperty().bindBidirectional(bottomMenuBar.sizeProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "BottomMenuBar";
    }
}
