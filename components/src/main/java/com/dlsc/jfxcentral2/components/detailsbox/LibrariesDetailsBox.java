package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.model.details.LibrariesDetailsObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import org.kordamp.ikonli.material2.Material2OutlinedAL;

import java.util.List;
import java.util.function.Consumer;

public class LibrariesDetailsBox extends DetailsBoxBase<LibrariesDetailsObject> {

    public LibrariesDetailsBox() {
        getStyleClass().add("libraries-details-box");
        setTitle("LIBRARIES");
        setIkon(Material2OutlinedAL.LIBRARY_BOOKS);
        setMaxItemsPerPage(3);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getTitle());
        });

        setOnHomepage(detailsObject -> {
            System.out.println("On Homepage: " + detailsObject.getTitle());
        });

    }

    @Override
    protected List<Node> createActionButtons(LibrariesDetailsObject model) {
        return List.of(createDetailsButton(model), createHomepageButton(model, onHomepageProperty()));
    }

    private final ObjectProperty<Consumer<LibrariesDetailsObject>> onHomepage = new SimpleObjectProperty<>(this, "onHomepage");

    public Consumer<LibrariesDetailsObject> getOnHomepage() {
        return onHomepage.get();
    }

    public ObjectProperty<Consumer<LibrariesDetailsObject>> onHomepageProperty() {
        return onHomepage;
    }

    public void setOnHomepage(Consumer<LibrariesDetailsObject> onHomepage) {
        this.onHomepage.set(onHomepage);
    }

}
