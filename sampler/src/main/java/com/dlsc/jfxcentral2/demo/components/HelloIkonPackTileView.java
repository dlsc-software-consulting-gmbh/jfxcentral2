package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.tiles.IkonPackModelTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.filter.IconPack;
import com.dlsc.jfxcentral2.model.ikon.IkonPackModel;
import com.dlsc.jfxcentral2.utils.IkonModelUtil;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloIkonPackTileView extends JFXCentralSampleBase {

    private IkonPackModelTileView ikonPackModelTileView;

    @Override
    protected Region createControl() {
        IkonPackModel ikonPackModel = IkonModelUtil.getInstance().getIkonPackModel(IconPack.ANTDESIGNICONSFILLED);
        ikonPackModel.setDescription("Source files for the custom icon-font used by the File-Icons package./app experience.");

        ikonPackModelTileView = new IkonPackModelTileView(ikonPackModel);
        return new StackPane(ikonPackModelTileView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        ikonPackModelTileView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "IconTileView";
    }
}
