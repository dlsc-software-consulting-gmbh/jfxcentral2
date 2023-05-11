package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.model.details.PersonDetailsObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.List;
import java.util.function.Consumer;

public class PersonsDetailsBox extends DetailsBoxBase<PersonDetailsObject> {

    public PersonsDetailsBox() {
        getStyleClass().add("persons-details-box");
        setTitle("PEOPLE");
        setIkon(MaterialDesign.MDI_ACCOUNT);
        setMaxItemsPerPage(2);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getTitle());
        });

        setOnHomepage(detailsObject -> {
            System.out.println("On Homepage: " + detailsObject.getTitle());
        });
    }

    @Override
    protected List<Node> createActionButtons(PersonDetailsObject model) {
        return List.of(createDetailsButton(model), createHomepageButton(model, onHomepageProperty()));
    }

    private final ObjectProperty<Consumer<PersonDetailsObject>> onHomepage = new SimpleObjectProperty<>(this, "onHomepage");

    public Consumer<PersonDetailsObject> getOnHomepage() {
        return onHomepage.get();
    }

    public ObjectProperty<Consumer<PersonDetailsObject>> onHomepageProperty() {
        return onHomepage;
    }

    public void setOnHomepage(Consumer<PersonDetailsObject> onHomepage) {
        this.onHomepage.set(onHomepage);
    }

}
