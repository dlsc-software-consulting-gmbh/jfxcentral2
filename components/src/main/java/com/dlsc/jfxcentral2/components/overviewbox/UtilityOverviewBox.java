package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Utility;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.utilities.cssplayground.CssPlaygroundView;
import com.dlsc.jfxcentral2.utilities.paintpicker.GradientDesignerView;
import com.dlsc.jfxcentral2.utilities.pathextractor.SVGPathExtractorView;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.elusive.Elusive;
import org.kordamp.ikonli.fileicons.FileIcons;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.materialdesign2.MaterialDesignS;

public class UtilityOverviewBox extends OverviewBox<Utility> {

    private Node toolPane;
    private Node readmeView;
    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(UtilityOverviewBox.class);

    public UtilityOverviewBox(Utility model) {
        super(model);
        getStyleClass().add("utility-overview-box");
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

    private Node createToolPane(Utility model) {
        //if (ModelObjectTool.isUtilityCanBeUsed(model)) {
            if (StringUtils.equalsIgnoreCase(model.getId(), "pathextractor")) {
                setTitle("SVG Path Extractor");
                setIcon(MaterialDesignS.SHAPE_OUTLINE);
                SVGPathExtractorView view = new SVGPathExtractorView(model);
                view.sizeProperty().bind(sizeProperty());
                return view;
            } else if (StringUtils.equalsIgnoreCase(model.getId(), "cssplayground")) {
                setTitle("Css Playground");
                setIcon(Elusive.CSS);
                CssPlaygroundView cssPlaygroundView = new CssPlaygroundView();
                cssPlaygroundView.sizeProperty().bind(sizeProperty());
                return cssPlaygroundView;
            } else if (StringUtils.equalsIgnoreCase(model.getId(), "gradientdesigner")){
                setTitle("Gradient Designer");
                setIcon(Material2OutlinedAL.COLOR_LENS);
                getStyleClass().add("gradient-designer-overview-box");
                return new GradientDesignerView(sizeProperty());
            }

            return createComingSoonPane();
        //}
        //return createComingSoonPane();
    }

    private Node readmeView(Utility model) {
        String readme = DataRepository2.getInstance().getUtilityReadMe(model);
        CustomMarkdownView markdownView = new CustomMarkdownView(readme);
        markdownView.setDisable(!ModelObjectTool.isUtilityCanBeUsed(model));
        return markdownView;
    }

    private Node createComingSoonPane() {
        Label label = new Label("Coming soon. Stay tuned!", new FontIcon(FileIcons.CAFFE2));
        label.getStyleClass().add("coming-soon-label");
        return label;
    }

}
