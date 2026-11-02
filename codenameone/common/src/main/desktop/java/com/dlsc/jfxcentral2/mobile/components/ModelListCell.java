package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral2.utils.CellGraphics;
import com.dlsc.jfxcentral2.utils.WrapLabel;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.AvatarView;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import com.dlsc.jfxcentral.data.util.StringUtils;

public class ModelListCell<T extends ModelObject> extends ListCell<T> {

    private static final String DEFAULT_STYLE_CLASS = "model-list-cell";
    private final AvatarView imageView;
    private final Label titleLabel;
    private final WrapLabel summaryLabel;
    private final HBox container;

    public ModelListCell() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        imageView = new AvatarView();
        // mobile.css asks for 55px (-fx-avatar-size), which Codename One
        // 7.0.280 does not pass to the control.
        imageView.setAvatarSize(55);
        imageView.setType(AvatarView.Type.SQUARE);
        // The page decides the shape (mobile.css: .rounded-preview-page,
        // .blogs-category-page), so it is looked up once the cell is shown.
        sceneProperty().addListener((obs, o, n) -> {
            if (n != null) {
                imageView.setType(pageAvatarType());
            }
        });
        imageView.setMouseTransparent(true);
        imageView.managedProperty().bind(imageView.visibleProperty());
        imageView.visibleProperty().bind(imageView.imageProperty().isNotNull());

        titleLabel = new Label();
        titleLabel.getStyleClass().add("title-label");

        summaryLabel = new WrapLabel();
        summaryLabel.getStyleClass().add("summary-label");
        // A row is 80px tall (-fx-cell-size), room for the title and two lines.
        summaryLabel.setMaxLines(2);
        summaryLabel.setMaxWidth(Double.MAX_VALUE);
        summaryLabel.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(summaryLabel, Priority.ALWAYS);

        VBox rightBox = new VBox(titleLabel, summaryLabel);
        rightBox.getStyleClass().add("right-box");

        container = new HBox(imageView, rightBox);
        container.getStyleClass().add("container");
        CellGraphics.fitToCell(this, container);

        setPrefWidth(0);
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

    private AvatarView.Type pageAvatarType() {
        for (Parent p = getParent(); p != null; p = p.getParent()) {
            if (p.getStyleClass().contains("rounded-preview-page")) {
                return AvatarView.Type.CIRCLE;
            }
            if (p.getStyleClass().contains("blogs-category-page")) {
                return AvatarView.Type.PLAIN;
            }
        }
        return AvatarView.Type.SQUARE;
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
