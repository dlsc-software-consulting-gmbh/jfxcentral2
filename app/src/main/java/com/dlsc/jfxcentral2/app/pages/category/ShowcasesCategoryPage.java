package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.CategoryContentPane;
import com.dlsc.jfxcentral2.components.filters.ShowcaseFilterView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class ShowcasesCategoryPage extends CategoryPageBase<Person> {

    public ShowcasesCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Showcases";
    }

    @Override
    public String description() {
        return "A curated list of applications written in JavaFX.";
    }

    @Override
    public Node content() {
        // header
        CategoryHeader header = createCategoryHeader("Showcases", IkonUtil.getModelIkon(RealWorldApp.class));

        // filter
        ShowcaseFilterView filterView = new ShowcaseFilterView();
        filterView.sizeProperty().bind(sizeProperty());

        // details
        CategoryContentPane contentPane = createCategoryContentPane();
        contentPane.getNodes().add(filterView);

        return wrapContent(header, contentPane);
    }
}
