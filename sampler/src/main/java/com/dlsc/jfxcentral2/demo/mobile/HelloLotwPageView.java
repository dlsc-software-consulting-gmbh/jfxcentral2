package com.dlsc.jfxcentral2.demo.mobile;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.mobile.LotwPageView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloLotwPageView extends JFXCentralSampleBase {

    private LotwPageView lotwPageView;

    @Override
    protected Region createControl() {
        lotwPageView = new LotwPageView();
        ScrollPane scrollPane = new ScrollPane(lotwPageView);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.MEDIUM);
        lotwPageView.sizeProperty().bind(sizeComboBox.sizeProperty());

        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "LotwPageView";
    }
}
