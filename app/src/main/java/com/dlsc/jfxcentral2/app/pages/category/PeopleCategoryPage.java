package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class PeopleCategoryPage extends CategoryPageBase<Person> {

    public PeopleCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - People";
    }

    @Override
    public String description() {
        return "A curated list of people connected to JavaFX. They develop libraries, applications, tools or they present at conferences and evangelise JavaFX.";
    }

    @Override
    public Node content() {
        // header
        CategoryHeader header = createCategoryHeader("People", MaterialDesign.MDI_ACCOUNT);

        // details
        DetailsContentPane detailsContentPane = new DetailsContentPane();
        detailsContentPane.sizeProperty().bind(sizeProperty());
        detailsContentPane.getFeaturesContainer().getFeatures().setAll(createFeatures());
        detailsContentPane.getMenuView().getItems().setAll(createMenuItems());

        return wrapContent(header, detailsContentPane);    }
}
