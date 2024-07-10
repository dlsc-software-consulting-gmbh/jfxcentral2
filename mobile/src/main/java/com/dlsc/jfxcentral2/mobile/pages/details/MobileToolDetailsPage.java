package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.components.overviewbox.ToolOverviewBox;
import com.dlsc.jfxcentral2.mobile.components.MobileCategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileToolDetailsPage extends MobileDetailsPageBase<Tool> {

    public MobileToolDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Tool.class, itemId);
    }


    @Override
    public List<Node> content() {
        Tool tool = getItem();

        // header
        MobileCategoryHeader header = new MobileCategoryHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(tool.getName());

        // overview
        ToolOverviewBox toolOverviewBox = new ToolOverviewBox(tool);
        toolOverviewBox.sizeProperty().bind(sizeProperty());

        PrettyScrollPane detailsContentPane = new PrettyScrollPane(new StackPane(toolOverviewBox));
        detailsContentPane.getStyleClass().add("mobile");
        detailsContentPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(detailsContentPane, Priority.ALWAYS);

        return List.of(header, detailsContentPane);
    }
}
