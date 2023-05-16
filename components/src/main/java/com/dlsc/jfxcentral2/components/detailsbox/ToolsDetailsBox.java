package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.function.Consumer;

public class ToolsDetailsBox extends DetailsBoxBase<Tool> {
    public ToolsDetailsBox() {
        getStyleClass().add("tools-details-box");
        setTitle("TOOLS");
        setIkon(IkonUtil.tool);
        setMaxItemsPerPage(3);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getName());
        });

        setOnRepository(detailsObject -> {
            System.out.println("Repository: " + detailsObject.getName());
        });
    }

    @Override
    protected List<Node> createActionButtons(Tool model) {
        Button repositoryButton = new Button("REPOSITORY", new FontIcon(IkonUtil.link));
        repositoryButton.managedProperty().bind(repositoryButton.visibleProperty());
        repositoryButton.visibleProperty().bind(onRepositoryProperty().isNotNull());
        repositoryButton.setOnAction(evt -> {
            if (getOnRepository() != null) {
                getOnRepository().accept(model);
            }
        });
        return List.of(createDetailsButton(model), repositoryButton);
    }

    private final ObjectProperty<Consumer<Tool>> onRepository = new SimpleObjectProperty<>(this, "onRepository");

    public Consumer<Tool> getOnRepository() {
        return onRepository.get();
    }

    public ObjectProperty<Consumer<Tool>> onRepositoryProperty() {
        return onRepository;
    }

    public void setOnRepository(Consumer<Tool> onRepository) {
        this.onRepository.set(onRepository);
    }

}
