package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

import java.util.List;
import java.util.function.Consumer;

public class AppsDetailsBox extends DetailsBoxBase<RealWorldApp> {

    public AppsDetailsBox() {
        getStyleClass().add("apps-details-box");
        setTitle("APPS");
        setIkon(IkonUtil.app);
        setMaxItemsPerPage(5);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getName());
        });

        setOnHomepage(detailsObject -> {
            System.out.println("On Homepage: " + detailsObject.getName());
        });

    }

    @Override
    protected List<Node> createActionButtons(RealWorldApp model) {
        return List.of(createDetailsButton(model), createHomepageButton(model, onHomepageProperty()));
    }

    private final ObjectProperty<Consumer<RealWorldApp>> onHomepage = new SimpleObjectProperty<>(this, "onHomepage");

    public Consumer<RealWorldApp> getOnHomepage() {
        return onHomepage.get();
    }

    public ObjectProperty<Consumer<RealWorldApp>> onHomepageProperty() {
        return onHomepage;
    }

    public void setOnHomepage(Consumer<RealWorldApp> onHomepage) {
        this.onHomepage.set(onHomepage);
    }

}
