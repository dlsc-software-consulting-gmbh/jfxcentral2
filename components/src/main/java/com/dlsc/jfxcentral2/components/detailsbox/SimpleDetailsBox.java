package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.CustomImageView;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.util.List;

/**
 * SimpleDetailsBox for Companies and Tips.
 */
public abstract class SimpleDetailsBox<T extends ModelObject> extends DetailsBoxBase<T> {

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
            CustomImageView imageView = createImageView(model);
            if (isSmall()) {
                StackPane.setAlignment(imageView, Pos.CENTER_LEFT);
            }
            StackPane imageWrapper = new StackPane(imageView);
            imageWrapper.getStyleClass().add("main-preview-wrapper");
            imageWrapper.managedProperty().bind(imageWrapper.visibleProperty());
            imageWrapper.getChildren().add(detailsButton);
            StackPane.setAlignment(detailsButton, Pos.TOP_RIGHT);
            return imageWrapper;
        }
    }

    protected abstract CustomImageView createImageView(T model);

}
