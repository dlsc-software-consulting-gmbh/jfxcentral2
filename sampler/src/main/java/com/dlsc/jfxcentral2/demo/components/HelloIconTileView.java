package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.tiles.IconTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.IconModel;
import com.dlsc.jfxcentral2.model.IkonData;
import com.dlsc.jfxcentral2.model.filter.IconStyle;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.fileicons.FileIconsIkonProvider;

public class HelloIconTileView extends JFXCentralSampleBase {

    private IconTileView iconTileView;

    @Override
    protected Region createControl() {
        IconModel iconModel = new IconModel();
        iconModel.setName("FileIcons");
        //Maybe it's possible to filter the icon library by style
        iconModel.setIconStyle(IconStyle.FILLING);
        IkonData ikonData = new IkonData();
        ikonData.setName("AntDesign");
        ikonData.setIkonProvider(new FileIconsIkonProvider());
        iconModel.setIkonData(ikonData);
        iconModel.setDescription("Source files for the custom icon-font used by the File-Icons package./app experience.");

        iconTileView = new IconTileView(iconModel);
        return new StackPane(iconTileView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        iconTileView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "IconTileView";
    }
}
