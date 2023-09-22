package com.dlsc.jfxcentral2.demo.devtools;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.devtools.pathextractor.SVGPathExtractorView;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloSVGPathExtractorView extends JFXCentralSampleBase {

    private SVGPathExtractorView view;

    @Override
    protected Region createControl() {
        view = new SVGPathExtractorView();
        ScrollPane scrollPane = new ScrollPane(view);
        scrollPane.setPrefWidth(1600);

        return scrollPane;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        view.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "SVGPathExtractorView";
    }
}
