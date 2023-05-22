package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.components.tiles.DownloadTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloDownloadTileView extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        Download download = new Download();
        download.setName("Download name");
        download.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore.");
        DownloadTileView companyTileView = new DownloadTileView();
        companyTileView.setData(download);
        return new StackPane(companyTileView);
    }

    @Override
    public String getSampleName() {
        return "DownloadTileView";
    }
}
