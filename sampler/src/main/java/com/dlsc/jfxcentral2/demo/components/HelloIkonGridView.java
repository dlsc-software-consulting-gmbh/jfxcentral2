package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.gridview.IkonGridView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class HelloIkonGridView extends JFXCentralSampleBase {

    private IkonGridView ikonGridView;

    @Override
    protected Region createControl() {
        ikonGridView = new IkonGridView();
        ikonGridView.getItems().addAll(MaterialDesign.values());
        ikonGridView.columnsProperty().bind(ikonGridView.sizeProperty().map(s -> s.isLarge() ? 12 : s.isMedium() ? 8 : 3));
        ikonGridView.setRows(6);
        return new ScrollPane(ikonGridView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        ikonGridView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "IkonGridView";
    }


}
