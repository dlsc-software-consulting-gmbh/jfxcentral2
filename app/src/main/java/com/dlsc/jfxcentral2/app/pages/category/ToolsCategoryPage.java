package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.CategoryContentPane;
import com.dlsc.jfxcentral2.components.filters.ToolsFilterView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class ToolsCategoryPage extends CategoryPageBase<Person> {

    public ToolsCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Tools";
    }

    @Override
    public String description() {
        return "A curated list of tools for developing JavaFX applications.";
    }

    @Override
    public Node content() {
        // header
        CategoryHeader header = createCategoryHeader("Tools", MaterialDesign.MDI_TOOLTIP);

        // filter
        ToolsFilterView filterView = new ToolsFilterView();
        filterView.sizeProperty().bind(sizeProperty());

        // details
        CategoryContentPane contentPane = createCategoryContentPane();
        contentPane.getNodes().add(filterView);

        return wrapContent(header, contentPane);
    }
}