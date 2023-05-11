package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.model.details.TutorialsDetailsObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.List;
import java.util.function.Consumer;

public class TutorialsDetailsBox extends DetailsBoxBase<TutorialsDetailsObject> {

    public TutorialsDetailsBox() {
        getStyleClass().add("tutorials-details-box");
        setTitle("TUTORIALS");
        setIkon(MaterialDesign.MDI_PROJECTOR_SCREEN);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getTitle());
        });

        setOnVisitTutorial(detailsObject -> {
            System.out.println("On Visit Blog: " + detailsObject.getTitle());
        });
    }

    @Override
    protected List<Node> createActionButtons(TutorialsDetailsObject model) {
        Button visitBlogButton = new Button("VISIT TUTORIAL", new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT));
        visitBlogButton.managedProperty().bind(visitBlogButton.visibleProperty());
        visitBlogButton.visibleProperty().bind(onVisitTutorialProperty().isNotNull());
        visitBlogButton.setOnAction(evt -> {
            if (getOnVisitTutorial() != null) {
                getOnVisitTutorial().accept(model);
            }
        });

        return List.of(createDetailsButton(model), visitBlogButton);
    }

    private final ObjectProperty<Consumer<TutorialsDetailsObject>> onVisitTutorial = new SimpleObjectProperty<>(this, "onVisitTutorial");

    public Consumer<TutorialsDetailsObject> getOnVisitTutorial() {
        return onVisitTutorial.get();
    }

    public ObjectProperty<Consumer<TutorialsDetailsObject>> onVisitTutorialProperty() {
        return onVisitTutorial;
    }

    public void setOnVisitTutorial(Consumer<TutorialsDetailsObject> onVisitTutorial) {
        this.onVisitTutorial.set(onVisitTutorial);
    }

}
