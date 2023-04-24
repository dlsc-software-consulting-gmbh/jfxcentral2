package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.FeaturesContainer;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Feature;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.List;

public class HelloFeaturesContainer extends JFXCentralSampleBase {

    private FeaturesContainer featuresContainer;

    @Override
    protected Region createControl() {
        featuresContainer = new FeaturesContainer(createFeatures());
        return new ScrollPane(featuresContainer);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);
        featuresContainer.sizeProperty().bind(sizeComboBox.sizeProperty());
        return sizeComboBox;
    }

    private List<Feature> createFeatures() {
        return List.of(
                new Feature("Video", "[1] Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Featured", "5 min video", MaterialDesign.MDI_TIMER, Feature.Type.VIDEO, new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/feature-img.png").toExternalForm()), "url ..."),
                new Feature("Video", "[2] Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Featured", "5 min video", MaterialDesign.MDI_TIMER, Feature.Type.VIDEO, new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/feature-img.png").toExternalForm()), "url ..."),
                new Feature("Video", "[3] Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Featured", "5 min video", MaterialDesign.MDI_TIMER, Feature.Type.VIDEO, new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/feature-img.png").toExternalForm()), "url ..."));
    }

    @Override
    public String getSampleName() {
        return "FeaturesContainer";
    }
}
