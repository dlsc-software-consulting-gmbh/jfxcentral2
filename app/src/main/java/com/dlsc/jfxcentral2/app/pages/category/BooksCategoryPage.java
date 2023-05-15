package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class BooksCategoryPage extends CategoryPageBase<Person> {

    public BooksCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Books";
    }

    @Override
    public String description() {
        return "A curated list of books covering JavaFX or topics related to JavaFX.";
    }

    @Override
    public Node content() {
        // header
        CategoryHeader header = createCategoryHeader("Books", MaterialDesign.MDI_BOOK);

        // details
        DetailsContentPane detailsContentPane = new DetailsContentPane();
        detailsContentPane.sizeProperty().bind(sizeProperty());
        detailsContentPane.getFeaturesContainer().getFeatures().setAll(createFeatures());
        detailsContentPane.getMenuView().getItems().setAll(createMenuItems());

        return wrapContent(header, detailsContentPane);
    }
}
