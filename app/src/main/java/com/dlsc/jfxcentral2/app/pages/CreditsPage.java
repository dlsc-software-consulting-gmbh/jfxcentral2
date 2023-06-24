package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.CreditsView;
import com.dlsc.jfxcentral2.components.FeaturesContainer;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class CreditsPage extends PageBase {

    public CreditsPage(ObjectProperty<Size> size) {
        super(size, Mode.LIGHT);
    }

    @Override
    public String title() {
        return "JFXCentral - Credits";
    }

    @Override
    public String description() {
        return "JFXCentral - Credits";
    }

    @Override
    public Node content() {

        CategoryHeader header = new CategoryHeader();
        header.setMode(Mode.LIGHT);
        header.sizeProperty().bind(sizeProperty());
        header.setTitle("Credits");
        header.setIkon(JFXCentralIcon.HANDSHAKE);

        // Credits view
        CreditsView creditsView = new CreditsView();
        creditsView.sizeProperty().bind(sizeProperty());

        // features
        FeaturesContainer featuresContainer = new FeaturesContainer();
        featuresContainer.sizeProperty().bind(sizeProperty());

        StripView stripView = new StripView(header, creditsView, featuresContainer);
        stripView.sizeProperty().bind(sizeProperty());
        stripView.getStyleClass().add("simple-page-wrapper");

        return wrapContent(stripView);
    }
}
