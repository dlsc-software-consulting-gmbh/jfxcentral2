package com.dlsc.jfxcentral2.app.pages.devtools;

import com.dlsc.jfxcentral2.app.pages.PageBase;
import com.dlsc.jfxcentral2.components.FeaturesContainer;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.headers.PacksIconsHeader;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public abstract class DevelopToolsPage extends PageBase {

    public DevelopToolsPage(ObjectProperty<Size> size) {
        super(size, Mode.DARK);
    }

    @Override
    public String title() {
        return "JFXCentral - Online Develop Tools";
    }

    @Override
    public String description() {
        return "JFXCentral - Online Develop Tools";
    }

    @Override
    public Node content() {
        // header
        PacksIconsHeader header = new PacksIconsHeader();
        header.sizeProperty().bind(sizeProperty());

        header.setTitle("SVG Path Extractor");
        header.setIkon(JFXCentralIcon.TOOLS);

        // content;
        Node toolView = getToolView(sizeProperty());

        // features
        FeaturesContainer featuresContainer = new FeaturesContainer();
        featuresContainer.sizeProperty().bind(sizeProperty());

        StripView stripView = new StripView(toolView, featuresContainer);
        stripView.getStyleClass().add("tools-strip-view");

        return wrapContent(header, stripView);
    }

    protected abstract Node getToolView(ObjectProperty<Size> sizeProperty);
}
