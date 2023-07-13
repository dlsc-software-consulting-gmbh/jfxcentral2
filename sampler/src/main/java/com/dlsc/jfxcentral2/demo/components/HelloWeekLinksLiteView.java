package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.WeekLinksLiteView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloWeekLinksLiteView extends JFXCentralSampleBase {

    private WeekLinksLiteView weekLinksLiteView;

    @Override
    protected Region createControl() {
        weekLinksLiteView = new WeekLinksLiteView();
        return new ScrollPane(weekLinksLiteView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        weekLinksLiteView.sizeProperty().bind(sizeComboBox.sizeProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "WeekLinksLiteView";
    }
}
