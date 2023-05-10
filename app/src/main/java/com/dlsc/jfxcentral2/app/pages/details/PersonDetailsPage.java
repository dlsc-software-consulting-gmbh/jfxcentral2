package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;

public class PersonDetailsPage extends DetailsPageBase<Person> {

    public PersonDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Person.class, itemId);
    }
}
