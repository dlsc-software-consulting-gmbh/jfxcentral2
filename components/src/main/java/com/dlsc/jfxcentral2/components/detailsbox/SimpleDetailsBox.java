package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.model.details.DetailsObject;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class SimpleDetailsBox<T extends DetailsObject> extends DetailsBoxBase<T> {

    private Button detailsButton;

    public SimpleDetailsBox() {
        getStyleClass().addAll("simple-details-box");
        setMaxItemsPerPage(3);
    }

    @Override
    protected List<Node> createActionButtons(T model) {
        detailsButton = createDetailsButton(model);
        detailsButton.getStyleClass().add("transparent-button");
        detailsButton.setText(null);
        if (isSmall()) {
            return null;
        }
        return List.of(detailsButton);
    }

    @Override
    protected Node createMainPreView(T model) {
        if (!isSmall()) {
            return super.createMainPreView(model);
        } else {
            Image mainPreview = model.getMainPreview();
            StackPane mainPreviewPane = null;
            if (mainPreview != null) {
                mainPreviewPane = createImageWrapper(mainPreview, true);
                mainPreviewPane.getStyleClass().add("main-preview-wrapper");
                mainPreviewPane.managedProperty().bind(mainPreviewPane.visibleProperty());
                if (model.getMainPreviewDescription() != null) {
                    Label mainPreviewDesc = new Label(model.getMainPreviewDescription(), new FontIcon());
                    mainPreviewDesc.getStyleClass().add("main-preview-desc");
                    mainPreviewPane.getChildren().add(mainPreviewDesc);
                    StackPane.setAlignment(mainPreviewDesc, Pos.TOP_RIGHT);
                }
                mainPreviewPane.getChildren().add(detailsButton);
                StackPane.setAlignment(detailsButton, Pos.TOP_RIGHT);
            }
            return mainPreviewPane;
        }
    }
}
