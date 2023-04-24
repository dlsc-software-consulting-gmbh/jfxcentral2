package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.FeatureView;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Feature;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class HelloFeatureView extends JFXCentralSampleBase {

    private FeatureView fv;

    @Override
    protected Region createControl() {
        fv = new FeatureView();
        fv.sizeProperty().addListener((ob, ov, nv) -> {
            if (nv == Size.LARGE) {
                fv.setFeature(new Feature("Video", "Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Featured", "5 min video", MaterialDesign.MDI_TIMER, Feature.Type.VIDEO, new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/feature-img.png").toExternalForm()), "url ..."));
            } else if (nv == Size.MEDIUM) {
                fv.setFeature(new Feature("Video", "Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Updated", "5 min video", MaterialDesign.MDI_TIMER, Feature.Type.LIBRARY, null, "url ..."));
            } else if (nv == Size.SMALL) {
                fv.setFeature(new Feature("Video", "Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Featured", "5 min video", MaterialDesign.MDI_TIMER, Feature.Type.TOOL, new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/feature-imag-sm1.png").toExternalForm()), "url ..."));
            }
        });
        return new ScrollPane(fv);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);
        fv.sizeProperty().bind(sizeComboBox.sizeProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "FeatureView";
    }
}
