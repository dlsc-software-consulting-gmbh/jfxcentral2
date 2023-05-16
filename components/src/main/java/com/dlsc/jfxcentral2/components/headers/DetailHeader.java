package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.Spacer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;

public class DetailHeader<T extends ModelObject> extends CategoryHeader<ModelObject> {

    public DetailHeader() {
        getStyleClass().add("detail-header");

        BorderPane contentPane = new BorderPane();
        contentPane.getStyleClass().add("content-pane");
        contentPane.centerProperty().bind(centerProperty());
        contentPane.bottomProperty().bind(Bindings.createObjectBinding(this::createBottomBox, onBackProperty(), onShareProperty()));

        contentProperty().bind(Bindings.createObjectBinding(() -> contentPane));
    }

    private HBox createBottomBox() {
        //Bottom
        Button backButton = new Button("BACK", new FontIcon(Material.ARROW_BACK_IOS));
        backButton.getStyleClass().addAll("back-button");
        backButton.managedProperty().bind(backButton.visibleProperty());
        backButton.visibleProperty().bind(onBackProperty().isNotNull());
        backButton.setOnAction(event -> {
            if (getOnBack() != null) {
                getOnBack().run();
            }
        });

        Button shareButton = new Button("SHARE", new FontIcon(Material.SHARE));
        shareButton.getStyleClass().add("share-button");
        shareButton.managedProperty().bind(shareButton.visibleProperty());
        shareButton.visibleProperty().bind(onShareProperty().isNotNull());
        shareButton.setOnAction(event -> {
            if (getOnShare() != null) {
                getOnShare().run();
            }
        });

        HBox bottomBox = new HBox(backButton, new Spacer(), shareButton);
        bottomBox.getStyleClass().add("bottom-box");
        return bottomBox;
    }

    private final ObjectProperty<Node> center = new SimpleObjectProperty<>(this, "center");

    public Node getCenter() {
        return center.get();
    }

    public ObjectProperty<Node> centerProperty() {
        return center;
    }

    public void setCenter(Node center) {
        this.center.set(center);
    }

    private final ObjectProperty<Runnable> onBack = new SimpleObjectProperty<>(this, "onBack");

    public Runnable getOnBack() {
        return onBack.get();
    }

    public ObjectProperty<Runnable> onBackProperty() {
        return onBack;
    }

    public void setOnBack(Runnable onBack) {
        this.onBack.set(onBack);
    }

    private final ObjectProperty<Runnable> onShare = new SimpleObjectProperty<>(this, "onShare");

    public Runnable getOnShare() {
        return onShare.get();
    }

    public ObjectProperty<Runnable> onShareProperty() {
        return onShare;
    }

    public void setOnShare(Runnable onShare) {
        this.onShare.set(onShare);
    }
}
