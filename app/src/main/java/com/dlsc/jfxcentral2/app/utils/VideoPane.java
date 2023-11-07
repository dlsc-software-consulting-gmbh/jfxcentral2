package com.dlsc.jfxcentral2.app.utils;

import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class VideoPane extends StackPane {

    public VideoPane(ObjectProperty<Size> size) {
        this.size.bind(size);

        sizeProperty().addListener(it -> updateStyleClassBasedOnSize(this));

        setMouseTransparent(false);

        getStyleClass().add("video-pane");
        setOnMouseClicked(evt -> setVisible(false));

        Button closeGlassPaneButton = new Button();
        StackPane.setMargin(closeGlassPaneButton, new Insets(100, 20, 0, 0));
        closeGlassPaneButton.getStyleClass().add("close-button");
        closeGlassPaneButton.setGraphic(new FontIcon(MaterialDesign.MDI_CLOSE_CIRCLE_OUTLINE));
        closeGlassPaneButton.visibleProperty().bind(onClose.isNotNull());
        closeGlassPaneButton.setOnAction(evt -> {
            getOnClose().run();
            setOnClose(null);
        });

        StackPane.setAlignment(closeGlassPaneButton, Pos.TOP_RIGHT);
        getChildren().add(closeGlassPaneButton);
    }

    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(Size.LARGE);

    public ObjectProperty<Size> sizeProperty() {
        return size;
    }

    public Size getSize() {
        return sizeProperty().get();
    }

    private final ObjectProperty<Runnable> onClose = new SimpleObjectProperty<>(this, "onCloseGlassPane");

    public Runnable getOnClose() {
        return onClose.get();
    }

    public ObjectProperty<Runnable> onCloseProperty() {
        return onClose;
    }

    public void setOnClose(Runnable onCloseGlasspane) {
        this.onClose.set(onCloseGlasspane);
    }

    private void updateStyleClassBasedOnSize(Node node) {
        node.getStyleClass().removeAll("lg", "md", "sm");
        switch (getSize()) {
            case SMALL -> node.getStyleClass().add("sm");
            case MEDIUM -> node.getStyleClass().add("md");
            case LARGE -> node.getStyleClass().add("lg");
        }
    }
}
