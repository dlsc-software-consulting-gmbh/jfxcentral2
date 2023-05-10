package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.model.AppsDetailsObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.List;
import java.util.function.Consumer;

public class AppsDetailsBox extends DetailsBoxBase<AppsDetailsObject> {
    public AppsDetailsBox() {
        getStyleClass().add("apps-details-box");
        setTitle("APPS");
        setIkon(MaterialDesign.MDI_APPS);
        setMaxItemsPerPage(2);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getTitle());
        });

        setOnHomepage(detailsObject -> {
            System.out.println("On Homepage: " + detailsObject.getTitle());
        });

    }

    @Override
    protected List<Node> createActionButtons(AppsDetailsObject model) {
        return List.of(createDetailsButton(model), createHomepageButton(model, onHomepageProperty()));
    }

    private final ObjectProperty<Consumer<AppsDetailsObject>> onHomepage = new SimpleObjectProperty<>(this, "onHomepage");

    public Consumer<AppsDetailsObject> getOnHomepage() {
        return onHomepage.get();
    }

    public ObjectProperty<Consumer<AppsDetailsObject>> onHomepageProperty() {
        return onHomepage;
    }

    public void setOnHomepage(Consumer<AppsDetailsObject> onHomepage) {
        this.onHomepage.set(onHomepage);
    }

}
