package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.OnlineTool;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.onlinetools.pathextractor.SVGPathExtractorView;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.fileicons.FileIcons;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignS;

public class OnlineToolOverviewBox extends OverviewBox<OnlineTool> {
    private Node toolPane;
    private Node readmeView;
    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(OnlineToolOverviewBox.class);
    public OnlineToolOverviewBox(OnlineTool model) {
        super(model);
        getStyleClass().add("online-tool-overview-box");
    }



    @Override
    protected Node createTopNode() {
        if (toolPane == null) {
            toolPane = createToolPane(getModel());
        }
        return toolPane;
    }

    @Override
    protected Node createBottomNode() {
        if (readmeView == null) {
            readmeView = readmeView(getModel());
        }
        return readmeView;
    }

    private Node createToolPane(OnlineTool model) {
        if (ModelObjectTool.isOnlineToolCanBeUsed(model)) {
            if (StringUtils.containsIgnoreCase(model.getDescription(), "path")) {
                setTitle("SVG Path Extractor");
                setIcon(MaterialDesignS.SHAPE_OUTLINE);
                return new SVGPathExtractorView(model);
            }
            return createComingSoonPane();
        }

        return createComingSoonPane();
    }

    private Node readmeView(OnlineTool model) {
        String onlineToolReadMe = DataRepository2.getInstance().getOnlineToolReadMe(model);
        CustomMarkdownView markdownView = new CustomMarkdownView(onlineToolReadMe);
        markdownView.setDisable(!ModelObjectTool.isOnlineToolCanBeUsed(model));
        return markdownView;
    }

    private Node createComingSoonPane() {
        Label label = new Label("Coming soon. Stay tuned!" ,new FontIcon(FileIcons.CAFFE2));
        label.getStyleClass().add("coming-soon-label");
        return label;
    }

}
