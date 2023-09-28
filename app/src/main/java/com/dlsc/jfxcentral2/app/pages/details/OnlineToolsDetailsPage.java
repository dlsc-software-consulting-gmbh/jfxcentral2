package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.OnlineTool;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.OnlineToolDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.OnlineToolOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class OnlineToolsDetailsPage extends DetailsPageBase<OnlineTool> {

    public OnlineToolsDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, OnlineTool.class, itemId);
    }


    @Override
    public Node content() {
        OnlineTool onlineTool = getItem();

        // header
        OnlineToolDetailHeader header = new OnlineToolDetailHeader(onlineTool);
        header.sizeProperty().bind(sizeProperty());

        // overview
        OnlineToolOverviewBox overviewBox = new OnlineToolOverviewBox(onlineTool);
        overviewBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(overviewBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(header, detailsContentPane);
    }


}
