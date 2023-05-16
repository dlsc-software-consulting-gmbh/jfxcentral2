package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.FeatureView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Feature;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloFeatureView extends JFXCentralSampleBase {

    @Override
    protected Region createControl() {
        FeatureView fv = new FeatureView(new Feature("Video", "Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Featured", "5 min video", IkonUtil.timer, Feature.Type.VIDEO, new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/feature-img.png").toExternalForm()), "url ..."));
        fv.setSize(Size.LARGE);
        FeatureView fv0 = new FeatureView(new Feature("Video", "Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Featured", "5 min video", IkonUtil.timer, Feature.Type.VIDEO, new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/feature-img.png").toExternalForm()), "url ..."));
        fv0.setSize(Size.MEDIUM);
        FeatureView fv1 = new FeatureView(new Feature("Video", "Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Updated", "5 min video", IkonUtil.timer, Feature.Type.LIBRARY, null, "url ..."));
        fv1.setSize(Size.SMALL);
        FeatureView fv2 = new FeatureView(new Feature("Video", "Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Featured", "5 min video", IkonUtil.timer, Feature.Type.TOOL, new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/feature-imag-sm1.png").toExternalForm()), "url ..."));
        fv2.setSize(Size.SMALL);

        return new ScrollPane(new VBox(30, fv, fv0, fv1, fv2));
    }

    @Override
    public String getSampleName() {
        return "FeatureView";
    }
}
