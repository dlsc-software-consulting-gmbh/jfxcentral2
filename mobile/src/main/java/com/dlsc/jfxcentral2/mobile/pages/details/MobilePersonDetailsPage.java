package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.PersonDetailHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.util.List;

public class MobilePersonDetailsPage extends MobileDetailsPageBase<Person> {

    public MobilePersonDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Person.class, itemId);
    }

    @Override
    public List<Node> content() {
        Person person = getItem();

        // header
        PersonDetailHeader personDetailHeader = new PersonDetailHeader(person);
        personDetailHeader.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return List.of(personDetailHeader, detailsContentPane);
    }
}
