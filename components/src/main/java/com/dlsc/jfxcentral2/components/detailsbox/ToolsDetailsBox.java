package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.model.details.ToolsDetailsObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

import java.util.List;
import java.util.function.Consumer;

public class ToolsDetailsBox extends DetailsBoxBase<ToolsDetailsObject> {
    public ToolsDetailsBox() {
        getStyleClass().add("tools-details-box");
        setTitle("TOOLS");
        setIkon(MaterialDesignT.TOOLS);
        setMaxItemsPerPage(3);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getTitle());
        });

        setOnRepository(detailsObject -> {
            System.out.println("Repository: " + detailsObject.getTitle());
        });

        setOnIssues(detailsObject -> {
            System.out.println("Issues: " + detailsObject.getTitle());
        });
    }

    @Override
    protected List<Node> createActionButtons(ToolsDetailsObject model) {
        Button repositoryButton = new Button("REPOSITORY", new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT));
        repositoryButton.managedProperty().bind(repositoryButton.visibleProperty());
        repositoryButton.visibleProperty().bind(onRepositoryProperty().isNotNull());
        repositoryButton.setOnAction(evt -> {
            if (getOnRepository() != null) {
                getOnRepository().accept(model);
            }
        });

        Button issuesButton = new Button("ISSUES", new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT));
        issuesButton.managedProperty().bind(issuesButton.visibleProperty());
        issuesButton.visibleProperty().bind(onIssuesProperty().isNotNull());
        issuesButton.setOnAction(evt -> {
            if (getOnIssues() != null) {
                getOnIssues().accept(model);
            }
        });

        return List.of(createDetailsButton(model), repositoryButton, issuesButton);
    }

    private final ObjectProperty<Consumer<ToolsDetailsObject>> onRepository = new SimpleObjectProperty<>(this, "onRepository");

    public Consumer<ToolsDetailsObject> getOnRepository() {
        return onRepository.get();
    }

    public ObjectProperty<Consumer<ToolsDetailsObject>> onRepositoryProperty() {
        return onRepository;
    }

    public void setOnRepository(Consumer<ToolsDetailsObject> onRepository) {
        this.onRepository.set(onRepository);
    }

    private final ObjectProperty<Consumer<ToolsDetailsObject>> onIssues = new SimpleObjectProperty<>(this, "onIssues");

    public Consumer<ToolsDetailsObject> getOnIssues() {
        return onIssues.get();
    }

    public ObjectProperty<Consumer<ToolsDetailsObject>> onIssuesProperty() {
        return onIssues;
    }

    public void setOnIssues(Consumer<ToolsDetailsObject> onIssues) {
        this.onIssues.set(onIssues);
    }
}
