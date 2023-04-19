package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.QuickLinkView;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.NormalQuickLink;
import com.dlsc.jfxcentral2.model.QuickLink;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

public class HelloQuickLinkView extends JFXCentralSampleBase {

    private QuickLinkView quickLinkView;

    @Override
    protected Region createControl() {
        QuickLink quickLink = new NormalQuickLink(
                "Tools",
                "Lorem ipsum dolor sit amet, consectetur. Lorem ipsum dolor sit amet, consectetur",
                MaterialDesignT.TOOLS,
                "xxx url...");
        quickLinkView = new QuickLinkView(quickLink);

        return new ScrollPane(new VBox(30, quickLinkView));
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        quickLinkView.sizeProperty().bind(sizeComboBox.sizeProperty());
        return new VBox(10, new Label("Chang Size"), sizeComboBox);
    }

    @Override
    public String getSampleName() {
        return "QuickLinkView";
    }
}
