package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.jpro.webapi.WebAPI;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;

import java.util.Objects;

public class DetailHeader<T extends ModelObject> extends CategoryHeader<T> {

    private T model;

    public DetailHeader(T model) {
        this.model = Objects.requireNonNull(model);

        getStyleClass().add("detail-header");

        BorderPane contentPane = new BorderPane();
        contentPane.getStyleClass().add("content-pane");
        contentPane.centerProperty().bind(centerProperty());
        contentPane.setBottom(createBottomBox());

        contentProperty().bind(Bindings.createObjectBinding(() -> contentPane));
    }

    public T getModel() {
        return model;
    }

    private HBox createBottomBox() {
        Button backButton = new Button("BACK", new FontIcon(Material.ARROW_BACK_IOS));
        backButton.getStyleClass().addAll("back-button");
        backButton.setOnAction(event -> WebAPI.getWebAPI(getScene()).executeScript("history.back()"));

        Button shareButton = new Button("SHARE", new FontIcon(JFXCentralIcon.SHARE));
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
