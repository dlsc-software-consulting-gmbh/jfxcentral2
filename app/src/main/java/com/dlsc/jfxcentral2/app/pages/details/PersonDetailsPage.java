package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.PersonDetailHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class PersonDetailsPage extends DetailsPageBase<Person> {

    public PersonDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Person.class, itemId);
    }

    @Override
    public Node content() {

        // header
        PersonDetailHeader personDetailHeader = new PersonDetailHeader(getItem());
        personDetailHeader.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = new DetailsContentPane();
        detailsContentPane.sizeProperty().bind(sizeProperty());
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());
        detailsContentPane.getFeaturesContainer().getFeatures().setAll(createFeatures());
        detailsContentPane.getMenuView().getItems().setAll(createMenuItems());

        return wrapContent(personDetailHeader, detailsContentPane);
    }
}
