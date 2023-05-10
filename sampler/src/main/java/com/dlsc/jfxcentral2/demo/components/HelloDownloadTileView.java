package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.tiles.DownloadTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Download;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloDownloadTileView extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        Download download = new Download(
                "Download name",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore.",
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/download-thumbnail-01.png").toExternalForm()),
                "https://www.dlsc.com",
                false,
                true);

        DownloadTileView companyTileView = new DownloadTileView(download);
        return new StackPane(companyTileView);
    }

    @Override
    public String getSampleName() {
        return "DownloadTileView";
    }
}
