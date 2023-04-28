package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.TipsAndTricksTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.TipsAndTricks;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.time.ZonedDateTime;

public class HelloTipsAndTricksTileView extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        TipsAndTricks data = new TipsAndTricks(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod",
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident...",
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/tips-tricks-thumbnail-01.png").toExternalForm()),
                "https://www.dlsc.com",
                true,
                true,
                ZonedDateTime.now()
                );
        return new StackPane(new TipsAndTricksTileView(data));
    }

    @Override
    public String getSampleName() {
        return "TipsAndTricksTileView";
    }
}
