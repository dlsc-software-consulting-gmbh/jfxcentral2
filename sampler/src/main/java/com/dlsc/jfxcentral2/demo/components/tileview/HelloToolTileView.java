package com.dlsc.jfxcentral2.demo.components.tileview;

import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.components.tiles.ToolTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloToolTileView extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        Tool tool = new Tool();
        tool.setName("CSSFX");
        tool.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do...");
        ToolTileView toolTileView = new ToolTileView();
        toolTileView.setData(tool);
        return new StackPane(toolTileView);
    }

    @Override
    public String getSampleName() {
        return "ToolTileView";
    }
}
