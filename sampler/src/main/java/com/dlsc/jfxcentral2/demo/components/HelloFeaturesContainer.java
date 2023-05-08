package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.FeaturesContainer;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Feature;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.List;

public class HelloFeaturesContainer extends JFXCentralSampleBase {


    @Override
    protected Region createControl() {

        FeaturesContainer smallFC = new FeaturesContainer();
        smallFC.getFeatures().setAll(createFeatures());
        smallFC.setSize(Size.SMALL);

        FeaturesContainer mediumFC = new FeaturesContainer();
        mediumFC.getFeatures().setAll(createFeatures());
        mediumFC.setSize(Size.MEDIUM);

        FeaturesContainer largerFC = new FeaturesContainer();
        largerFC.getFeatures().setAll(createFeatures());
        largerFC.setSize(Size.LARGE);

        return new TabPane(
                new Tab("FeaturesContainer sm", smallFC),
                new Tab("FeaturesContainer md", mediumFC),
                new Tab("FeaturesContainer ld", largerFC)
        );
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
