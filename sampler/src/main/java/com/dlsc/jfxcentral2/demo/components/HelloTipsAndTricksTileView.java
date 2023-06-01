package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.tiles.TipsAndTricksTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;

public class HelloTipsAndTricksTileView extends JFXCentralSampleBase {

    private TipsAndTricksTileView view;

    @Override
    protected Region createControl() {
        Tip data = new Tip();
        data.setName("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod");
        data.setDescription("Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident...");
        data.setCreatedOn(LocalDate.now());
        view = new TipsAndTricksTileView(data);
        return new StackPane(view);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        view.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "TipsAndTricksTileView";
    }
}
