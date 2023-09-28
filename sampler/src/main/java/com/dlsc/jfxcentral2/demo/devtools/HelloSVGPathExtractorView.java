package com.dlsc.jfxcentral2.demo.devtools;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.OnlineTool;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.onlinetools.pathextractor.SVGPathExtractorView;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.util.Optional;

public class HelloSVGPathExtractorView extends JFXCentralSampleBase {

    private SVGPathExtractorView view;

    @Override
    protected Region createControl() {
        Optional<OnlineTool> path = DataRepository2.getInstance().getOnlineTools().stream().filter(t -> t.getId().contains("path")).findFirst();
        view = new SVGPathExtractorView(path.get());
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
