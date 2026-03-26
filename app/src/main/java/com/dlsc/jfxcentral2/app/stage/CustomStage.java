package com.dlsc.jfxcentral2.app.stage;

import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.OSUtil;
import com.jpro.webapi.WebAPI;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import one.jpro.platform.routing.sessionmanager.SessionManager;

import java.util.Objects;

/**
 * A simple BorderPane wrapper used as the scene root for the browser (JPro) rendering path.
 * For the desktop path, the native EXTENDED stage style is used together with a {@link javafx.scene.layout.HeaderBar}.
 */
public class CustomStage extends BorderPane {

    public CustomStage(Stage stage, Node node, SessionManager sessionManager, ObjectProperty<Size> size) {
        setContent(node);
        node.getStyleClass().add("stage-content");

        getStylesheets().add(Objects.requireNonNull(CustomStage.class.getResource("stage.css")).toExternalForm());
        getStyleClass().add("custom-stage");

        if (OSUtil.isAndroidOrIOS()) {
            getStyleClass().add("native");
        }

        size.addListener(it -> updateStyleBasedOnSize(size.get()));
        updateStyleBasedOnSize(size.get());

        centerProperty().bind(contentProperty());

        if (WebAPI.isBrowser()) {
            pseudoClassStateChanged(PseudoClass.getPseudoClass("web"), true);
        }
    }

    private void updateStyleBasedOnSize(Size size) {
        getStyleClass().removeAll("lg", "md", "sm");
        if (size.equals(Size.LARGE)) {
            getStyleClass().add("lg");
        } else if (size.equals(Size.MEDIUM)) {
            getStyleClass().add("md");
        } else {
            getStyleClass().add("sm");
        }
    }

    private final ObjectProperty<Node> content = new SimpleObjectProperty<>(this, "content");

    public Node getContent() {
        return content.get();
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    public void setContent(Node content) {
        this.content.set(content);
    }
}
