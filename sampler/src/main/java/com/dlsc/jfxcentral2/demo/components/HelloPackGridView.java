package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.gridview.ModelGridView;
import com.dlsc.jfxcentral2.components.tiles.IkonPackModelTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.filter.IconPack;
import com.dlsc.jfxcentral2.model.ikon.IkonPackModel;
import com.dlsc.jfxcentral2.utils.IkonModelUtil;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloPackGridView extends JFXCentralSampleBase {

    private ModelGridView<IkonPackModel> gridView;

    @Override
    protected Region createControl() {

        gridView = new ModelGridView<>();
        gridView.setTileViewProvider(IkonPackModelTileView::new);
        IconPack[] values = IconPack.values();
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                continue;
            }
            IkonPackModel ikonPackModel = IkonModelUtil.getInstance().getIkonPackModel(values[i]);
            if (ikonPackModel != null) {
                gridView.getItems().add(ikonPackModel);
            }
        }

        return new ScrollPane(gridView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        gridView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "ModelGridView<IkonPackModel>";
    }
}
