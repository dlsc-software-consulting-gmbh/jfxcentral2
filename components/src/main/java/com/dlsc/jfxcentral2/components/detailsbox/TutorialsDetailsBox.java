package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.function.Consumer;

public class TutorialsDetailsBox extends DetailsBoxBase<Tutorial> {

    public TutorialsDetailsBox() {
        getStyleClass().add("tutorials-details-box");
        setTitle("TUTORIALS");
        setIkon(IkonUtil.tutorial);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getName());
        });

        setOnVisitTutorial(detailsObject -> {
            System.out.println("On Visit Blog: " + detailsObject.getName());
        });
    }

    @Override
    protected List<Node> createActionButtons(Tutorial model) {
        Button visitBlogButton = new Button("VISIT TUTORIAL", new FontIcon(IkonUtil.link));
        visitBlogButton.managedProperty().bind(visitBlogButton.visibleProperty());
        visitBlogButton.visibleProperty().bind(onVisitTutorialProperty().isNotNull());
        visitBlogButton.setOnAction(evt -> {
            if (getOnVisitTutorial() != null) {
                getOnVisitTutorial().accept(model);
            }
        });

        return List.of(createDetailsButton(model), visitBlogButton);
    }

    private final ObjectProperty<Consumer<Tutorial>> onVisitTutorial = new SimpleObjectProperty<>(this, "onVisitTutorial");

    public Consumer<Tutorial> getOnVisitTutorial() {
        return onVisitTutorial.get();
    }

    public ObjectProperty<Consumer<Tutorial>> onVisitTutorialProperty() {
        return onVisitTutorial;
    }

    public void setOnVisitTutorial(Consumer<Tutorial> onVisitTutorial) {
        this.onVisitTutorial.set(onVisitTutorial);
    }

}
