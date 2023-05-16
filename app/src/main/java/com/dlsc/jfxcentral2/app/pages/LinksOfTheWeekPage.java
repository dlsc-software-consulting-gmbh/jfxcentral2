package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.MenuView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.List;

public class LinksOfTheWeekPage extends CategoryPageBase<LinksOfTheWeek> {

    public LinksOfTheWeekPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Links of the Week";
    }

    @Override
    public String description() {
        return "Miscellaneous stuff found on the web that is related to JavaFX.";
    }

    @Override
    public Node content() {

        // header
        CategoryHeader header = createCategoryHeader("Links of the Week", MaterialDesign.MDI_LINK);

        // this is a category page but we still need to use the details content pane for layout purposes
        DetailsContentPane detailsContentPane = new DetailsContentPane();
        detailsContentPane.sizeProperty().bind(sizeProperty());
        detailsContentPane.getFeaturesContainer().getFeatures().setAll(createFeatures());
        detailsContentPane.getMenuView().getItems().setAll(createMenuItems());

        return wrapContent(header, detailsContentPane);
    }

    protected List<MenuView.Item> createMenuItems() {
        return FXCollections.observableArrayList(
                new MenuView.Item("16. JAN - 23. JAN", null, null),
                new MenuView.Item("24. JAN - 31. JAN", null, null)
        );
    }
}
