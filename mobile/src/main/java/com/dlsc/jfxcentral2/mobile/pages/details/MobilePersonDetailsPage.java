package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.mobile.components.MobileCategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobilePersonDetailsPage extends MobileDetailsPageBase<Person> {

    public MobilePersonDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Person.class, itemId);
    }

    @Override
    public List<Node> content() {
        Person person = getItem();

        // header
        MobileCategoryHeader header = new MobileCategoryHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(person.getName());

        // details
        VBox detailsContentPane = new VBox();
        detailsContentPane.getChildren().addAll(createDetailBoxes());

        PrettyScrollPane scrollPane = new PrettyScrollPane(new StackPane(detailsContentPane));
        scrollPane.getStyleClass().add("mobile");
        scrollPane.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return List.of(header, scrollPane);
    }
}
