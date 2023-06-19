package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class ErrorView extends PaneBase {
    private static final String DEFAULT_MESSAGE = "We could not reach the page you were searching for Error 404: page not found";
    private static final int REPEAT_TIMES = 10;
    private static final DropShadow BLUE_SHADOW = new DropShadow(BlurType.GAUSSIAN, Color.rgb(14, 74, 228, 0.25), 30, 0.2, 0, 0);

    public enum BtnStyle {
        GREEN,
        ORANGE,
        RED
    }

    public record Action(String text, Ikon ikon, Runnable action, BtnStyle btnStyle) {
        public Action(String text, Runnable action) {
            this(text, null, action, BtnStyle.GREEN);
        }

        public Action(String text, Ikon ikon, Runnable action) {
            this(text, ikon, action, BtnStyle.GREEN);
        }

        public Action(String text, Runnable action, BtnStyle btnStyle) {
            this(text, null, action, btnStyle);
        }
    }

    public ErrorView() {
        getStyleClass().add("error-view");
        actionsProperty().addListener((InvalidationListener) it -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        getChildren().clear();

        Pane stackPane = new Pane();
        stackPane.getStyleClass().add("stack-pane");

        BorderPane dialogPane = createDialogPane(false);
        dialogPane.setEffect(isSmall() ? null : BLUE_SHADOW);
        for (int i = 0; i < REPEAT_TIMES; i++) {
            BorderPane mirrorPane = createDialogPane(true);
            mirrorPane.setEffect(isSmall() ? null : BLUE_SHADOW);
            if (!isSmall()) {
                mirrorPane.setLayoutX((isLarge() ? 36 : 30) * (REPEAT_TIMES - i));
            }
            mirrorPane.setLayoutY((isLarge() ? 27 : 18) * (REPEAT_TIMES - i));
            stackPane.getChildren().add(mirrorPane);
        }

        stackPane.getChildren().add(dialogPane);
        stackPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        stackPane.setEffect(isSmall() ? BLUE_SHADOW : null);
        getChildren().setAll(stackPane);
    }

    private BorderPane createDialogPane(boolean isAMirrorImage) {
        BorderPane contentPane = new BorderPane();
        contentPane.getStyleClass().add("dialog-pane");

        Header header = new Header();
        header.setIcon(IkonUtil.close);
        header.titleProperty().bind(titleProperty());

        if (!isAMirrorImage) {
            header.onIconClickActionProperty().bind(onCloseProperty());
        }

        Label message = new Label();
        message.getStyleClass().add("message");
        message.setWrapText(true);
        message.textProperty().bind(messageProperty());
        message.graphicProperty().bind(graphicProperty());

        HBox buttonsBox = new HBox();
        buttonsBox.getStyleClass().add("buttons-box");
        for (int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);
            Button button = new Button(action.text());
            if (action.ikon() != null) {
                FontIcon icon = new FontIcon(action.ikon());
                button.setGraphic(icon);
            }
            button.getStyleClass().addAll("fill-button", "action-btn", "action-btn-" + action.btnStyle().name().toLowerCase(), "action-btn" + i);
            button.getStyleClass().add(action.btnStyle().name().toLowerCase());
            button.setMinWidth(Region.USE_PREF_SIZE);
            if (!isAMirrorImage) {
                button.setOnAction(evt -> {
                    if (action.action() != null) {
                        action.action().run();
                    }
                    evt.consume();
                });
            }
            buttonsBox.getChildren().add(button);
        }
        message.setVisible(!isAMirrorImage);
        buttonsBox.setVisible(!isAMirrorImage);

        VBox centerBox = new VBox(message, buttonsBox);
        centerBox.getStyleClass().add("center-box");

        contentPane.setCenter(centerBox);
        contentPane.setTop(header);
        contentPane.setMaxHeight(Region.USE_PREF_SIZE);
        return contentPane;
    }

    private final StringProperty title = new SimpleStringProperty(this, "title", "WE ARE SORRY");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    private final StringProperty message = new SimpleStringProperty(this, "message", DEFAULT_MESSAGE);

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    private final ListProperty<Action> actions = new SimpleListProperty<>(this, "actions", FXCollections.observableArrayList());

    public ObservableList<Action> getActions() {
        return actions.get();
    }

    public ListProperty<Action> actionsProperty() {
        return actions;
    }

    public void setActions(ObservableList<Action> actions) {
        this.actions.set(actions);
    }

    private final ObjectProperty<Node> graphic = new SimpleObjectProperty<>(this, "graphic");

    public Node getGraphic() {
        return graphic.get();
    }

    public ObjectProperty<Node> graphicProperty() {
        return graphic;
    }

    public void setGraphic(Node graphic) {
        this.graphic.set(graphic);
    }

    private final ObjectProperty<Runnable> onClose = new SimpleObjectProperty<>(this, "onClose");

    public Runnable getOnClose() {
        return onClose.get();
    }

    public ObjectProperty<Runnable> onCloseProperty() {
        return onClose;
    }

    public void setOnClose(Runnable onClose) {
        this.onClose.set(onClose);
    }
}
