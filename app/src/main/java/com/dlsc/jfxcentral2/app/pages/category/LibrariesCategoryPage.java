package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.CategoryContentPane;
import com.dlsc.jfxcentral2.components.filters.LibrariesFilterView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class LibrariesCategoryPage extends CategoryPageBase<Person> {

    public LibrariesCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Libraries";
    }

    @Override
    public String description() {
        return "A curated list of libraries available for JavaFX.";
    }

    @Override
    public Node content() {
        // header
        CategoryHeader header = createCategoryHeader("Libraries", IkonUtil.getModelIkon(Library.class));

        //
        LibrariesFilterView filterView = new LibrariesFilterView();
        filterView.sizeProperty().bind(sizeProperty());

        // details
        CategoryContentPane contentPane = createCategoryContentPane();
        contentPane.getNodes().add(filterView);

        return wrapContent(header, contentPane);
    }
}
