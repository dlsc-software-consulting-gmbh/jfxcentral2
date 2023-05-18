package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.CategoryContentPane;
import com.dlsc.jfxcentral2.components.filters.TutorialsFilterView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class TutorialsCategoryPage extends CategoryPageBase<Person> {

    public TutorialsCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Tutorials";
    }

    @Override
    public String description() {
        return "A curated list of online tutorials covering JavaFX topics.";
    }

    @Override
    public Node content() {
        // header
        CategoryHeader header = createCategoryHeader("Tutorials", IkonUtil.getModelIkon(Tutorial.class));

        // filter
        TutorialsFilterView filterView = new TutorialsFilterView();
        filterView.sizeProperty().bind(sizeProperty());

        // details
        CategoryContentPane contentPane = createCategoryContentPane();
        contentPane.getNodes().add(filterView);

        return wrapContent(header, contentPane);
    }
}
