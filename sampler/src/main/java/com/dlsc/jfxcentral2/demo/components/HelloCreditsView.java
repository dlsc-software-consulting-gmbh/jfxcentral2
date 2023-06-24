package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.CreditsView;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloCreditsView extends JFXCentralSampleBase {

    private CreditsView creditsView;

    @Override
    protected Region createControl() {
        creditsView = new CreditsView();

        return new ScrollPane(creditsView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        creditsView.sizeProperty().bind(sizeComboBox.valueProperty());
        creditsView.maxWidthProperty().bind(Bindings.createDoubleBinding(() ->
                switch (sizeComboBox.getValue()) {
                    case SMALL -> 317d;
                    case MEDIUM -> 600d;
                    case LARGE -> 1080d;
                }, sizeComboBox.valueProperty()));
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "CreditsView";
    }
}
