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
        Person person = getItem();

        // header
        PersonDetailHeader personDetailHeader = new PersonDetailHeader(person);
        personDetailHeader.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(personDetailHeader, detailsContentPane);
    }
}
