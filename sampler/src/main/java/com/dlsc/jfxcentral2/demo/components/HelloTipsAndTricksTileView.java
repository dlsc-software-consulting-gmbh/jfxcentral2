package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.components.tiles.TipsAndTricksTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;

public class HelloTipsAndTricksTileView extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        Tip data = new Tip();
        data.setName("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod");
        data.setDescription("Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident...");
        data.setCreatedOn(LocalDate.now());
        return new StackPane(new TipsAndTricksTileView(data));
    }

    @Override
    public String getSampleName() {
        return "TipsAndTricksTileView";
    }
}
