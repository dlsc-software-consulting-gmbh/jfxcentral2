package com.dlsc.jfxcentral2.app.pages.devtools;

import com.dlsc.jfxcentral2.devtools.pathextractor.SVGPathExtractorView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class SvgPathExtractToolPage extends DevelopToolsPage {

    public SvgPathExtractToolPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - SVG Path Extractor";
    }

    @Override
    public String description() {
        return "JFXCentral - Extract the path in the SVG file.";
    }

    protected Node getToolView(ObjectProperty<Size> sizeProperty) {
        SVGPathExtractorView svgPathExtractorView = new SVGPathExtractorView();
        svgPathExtractorView.sizeProperty().bind(sizeProperty);
        return svgPathExtractorView;
    }

}
