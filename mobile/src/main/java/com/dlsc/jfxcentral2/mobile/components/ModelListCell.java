package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;

public class ModelListCell<T extends ModelObject> extends ListCell<T> {

    private static final String DEFAULT_STYLE_CLASS = "model-list-cell";
    private final AvatarView imageView;
    private final Label titleLabel;
    private final Label summaryLabel;
    private final HBox container;

    public ModelListCell() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        imageView = new AvatarView();
        imageView.setMouseTransparent(true);
        imageView.managedProperty().bind(imageView.visibleProperty());
        imageView.visibleProperty().bind(imageView.imageProperty().isNotNull());

        titleLabel = new Label();
        titleLabel.getStyleClass().add("title-label");

        summaryLabel = new Label();
        summaryLabel.getStyleClass().add("summary-label");
        summaryLabel.setWrapText(true);
        summaryLabel.setMaxWidth(Double.MAX_VALUE);
        summaryLabel.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(summaryLabel, Priority.ALWAYS);

        VBox rightBox = new VBox(titleLabel, summaryLabel);
        rightBox.getStyleClass().add("right-box");

        container = new HBox(imageView, rightBox);
        container.getStyleClass().add("container");

        setPrefWidth(0);
        setOnMouseClicked(event -> {
            T item = getItem();
            if (item != null) {
                MobileLinkUtil.getToPage(ModelObjectTool.getModelLink(item));
            }
        });
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (imageView.imageProperty().isBound()) {
            imageView.imageProperty().unbind();
        }
        if (empty || item == null) {
            titleLabel.setText(null);
            summaryLabel.setText(null);
            imageView.setImage(null);
            setText(null);
            setGraphic(null);
        } else {
            handleImage(item, imageView.imageProperty());
            titleLabel.setText(getTitle(item));
            summaryLabel.setText(getSummary(item));
            setGraphic(container);
        }
    }

    protected void handleImage(T item, ObjectProperty<Image> imageProperty){

    }

    protected String getTitle(T item) {
        return StringUtils.defaultIfEmpty(item.getName(), "No title available");
    }

    protected String getSummary(T item) {
        return StringUtils.defaultIfEmpty(item.getDescription(), "No description available");
    }

}
