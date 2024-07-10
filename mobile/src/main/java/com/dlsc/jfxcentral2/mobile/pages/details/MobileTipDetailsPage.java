package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.components.overviewbox.TipOverviewBox;
import com.dlsc.jfxcentral2.mobile.components.MobileCategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileTipDetailsPage extends MobileDetailsPageBase<Tip> {

    public MobileTipDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Tip.class, itemId);
    }

    @Override
    public List<Node> content() {
        Tip tip = getItem();

        // header
        MobileCategoryHeader header = new MobileCategoryHeader();
        header.previewImageProperty().bind(ImageManager.getInstance().tipBannerImageProperty(tip));
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(tip.getName());

        // overview
        TipOverviewBox tipOverviewBox = new TipOverviewBox(tip);
        tipOverviewBox.sizeProperty().bind(sizeProperty());

        PrettyScrollPane detailsContentPane = new PrettyScrollPane(new StackPane(tipOverviewBox));
        detailsContentPane.getStyleClass().add("mobile");
        detailsContentPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(detailsContentPane, Priority.ALWAYS);


        return List.of(header, detailsContentPane);
    }
}
