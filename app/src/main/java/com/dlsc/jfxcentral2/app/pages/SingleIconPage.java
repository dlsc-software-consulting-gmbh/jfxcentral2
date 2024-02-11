package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.FeaturesContainer;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.gridview.IkonDetailExtraView;
import com.dlsc.jfxcentral2.components.gridview.IkonDetailView;
import com.dlsc.jfxcentral2.components.headers.SingleIconDetailHeader;
import com.dlsc.jfxcentral2.model.IconInfo;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class SingleIconPage extends PageBase {

    private final IconInfo iconInfo;
    private final boolean isShareable;

    public SingleIconPage(ObjectProperty<Size> size, IconInfo iconInfo, boolean isShareable) {
        super(size, Mode.DARK);
        this.iconInfo = iconInfo;
        this.isShareable = isShareable;
    }

    @Override
    public String title() {
        return "JFXCentral - " + iconInfo.getIkon().getDescription();
    }

    @Override
    public String description() {
        return "This page showcases the detailed view of an icon.";
    }

    @Override
    public Node content() {
        // header
        SingleIconDetailHeader header = new SingleIconDetailHeader(iconInfo);
        header.sizeProperty().bind(sizeProperty());

        // content
        IkonDetailView ikonDetailView = new IkonDetailExtraView(iconInfo);
        ikonDetailView.sizeProperty().bind(sizeProperty());

        PaneBase contentWrapper = new PaneBase();
        contentWrapper.sizeProperty().bind(sizeProperty());
        contentWrapper.getChildren().add(ikonDetailView);
        contentWrapper.getStyleClass().addAll("single-icon-content-wrapper");

        // features
        FeaturesContainer featuresContainer = new FeaturesContainer();
        featuresContainer.sizeProperty().bind(sizeProperty());
        PaneBase featuresWrapper = new PaneBase();
        featuresWrapper.sizeProperty().bind(sizeProperty());
        featuresWrapper.getChildren().add(featuresContainer);
        featuresWrapper.getStyleClass().add("single-icon-features-wrapper");

        return wrapContent(header, contentWrapper, featuresWrapper);
    }

}
