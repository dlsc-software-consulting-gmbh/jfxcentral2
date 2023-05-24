package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.ToolDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.ToolOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class ToolDetailsPage extends DetailsPageBase<Tool> {

    public ToolDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Tool.class, itemId);
    }


    @Override
    public Node content() {
        // header
        ToolDetailHeader header = new ToolDetailHeader(getItem());
        header.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(new ToolOverviewBox(getItem()));
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(header, detailsContentPane);
    }
}
