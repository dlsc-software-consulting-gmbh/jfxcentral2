package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.FeaturesContainer;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.components.topcontent.TopContentContainerView;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class TopContentPage extends PageBase {

    public TopContentPage(ObjectProperty<Size> size) {
        super(size, Mode.LIGHT);
    }

    @Override
    public String title() {
        return "JFXCentral - Team";
    }

    @Override
    public String description() {
        return "JFXCentral2 main developers";
    }

    @Override
    public Node content() {

        CategoryHeader header = new CategoryHeader();
        header.setMode(Mode.LIGHT);
        header.sizeProperty().bind(sizeProperty());
        header.setTitle("Top Content");
        header.setIkon(JFXCentralIcon.TOP_CONTENT);

        // team view
        TopContentContainerView topContentContainerView = new TopContentContainerView();
        topContentContainerView.sizeProperty().bind(sizeProperty());

        // features
        FeaturesContainer featuresContainer = new FeaturesContainer();
        featuresContainer.sizeProperty().bind(sizeProperty());

        StripView stripView = new StripView(header, topContentContainerView ,featuresContainer);
        stripView.sizeProperty().bind(sizeProperty());
        stripView.getStyleClass().add("team-page-wrapper");

        return wrapContent(stripView);
    }
}
