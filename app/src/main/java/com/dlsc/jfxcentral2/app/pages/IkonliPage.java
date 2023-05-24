package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.FeaturesContainer;
import com.dlsc.jfxcentral2.components.IkonliBrowser;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class IkonliPage extends PageBase {


    public IkonliPage(ObjectProperty<Size> size) {
        super(size, TopMenuBar.Mode.DARK);
    }

    @Override
    public String title() {
        return "JFXCentral - Ikonli Browser";
    }

    @Override
    public String description() {
        return "A browser for all ikonli icon fonts that supports searching based on icon name.";
    }

    @Override
    public Node content() {

        // category header
        CategoryHeader header = new CategoryHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setTitle("Icons");
        header.setIkon(IkonUtil.champion);

        // ikonli browser
        IkonliBrowser browser = new IkonliBrowser();
        browser.sizeProperty().bind(sizeProperty());

        // features container
        FeaturesContainer featuresContainer = new FeaturesContainer();
        featuresContainer.sizeProperty().bind(sizeProperty());

        // strip view wrapper
        StripView stripView = new StripView(browser, featuresContainer);
        stripView.sizeProperty().bind(sizeProperty());

        return wrapContent(header, stripView);
    }
}
