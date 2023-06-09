package com.dlsc.jfxcentral2.app;

import com.jpro.webapi.WebAPI;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import one.jpro.routing.sessionmanager.SessionManager;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class CustomStage extends BorderPane {

    private static final int OFFSET = 5;
    public static final double MIN_STAGE_WIDTH = 850;
    public static final double MIN_STAGE_HEIGHT = 600;

    private double startX;
    private double startY;

    private enum Operation {
        NONE,
        MOVE,
        RESIZE_N,
        RESIZE_S,
        RESIZE_W,
        RESIZE_E,
        RESIZE_NW,
        RESIZE_NE,
        RESIZE_SW,
        RESIZE_SE
    }

    public CustomStage(Stage stage, Node node, SessionManager sessionManager) {
        setContent(node);
        getStylesheets().add(CustomStage.class.getResource("stage.css").toExternalForm());
        getStyleClass().add("custom-stage");

        stage.focusedProperty().addListener(it -> updateStyleBasedOnStageFocus(stage));

        TitleBar titleBar = new TitleBar(sessionManager);
        titleBar.getLabel().setText("JFXCentral");

        if (!WebAPI.isBrowser()) {
            VBox vBox = new VBox(titleBar);
            vBox.setPadding(new Insets(0, 0, 2, 0));
            vBox.setAlignment(Pos.CENTER_RIGHT);
            setTop(vBox);
        }

        centerProperty().bind(contentProperty());

        if (!WebAPI.isBrowser()) {

            EventHandler<MouseEvent> mouseMovedHandler = evt -> {
                double x = evt.getX();
                double y = evt.getY();

                if (x < OFFSET) {
                    if (y < OFFSET) {
                        setCursor(Cursor.NW_RESIZE);
                    } else if (y > getHeight() - OFFSET) {
                        setCursor(Cursor.SW_RESIZE);
                    } else {
                        setCursor(Cursor.W_RESIZE);
                    }
                } else if (x > getWidth() - OFFSET) {
                    if (y < OFFSET) {
                        setCursor(Cursor.NE_RESIZE);
                    } else if (y > getHeight() - OFFSET) {
                        setCursor(Cursor.SE_RESIZE);
                    } else {
                        setCursor(Cursor.E_RESIZE);
                    }
                } else if (y < OFFSET) {
                    setCursor(Cursor.N_RESIZE);
                } else if (y > getHeight() - OFFSET) {
                    setCursor(Cursor.S_RESIZE);
                } else if (y < titleBar.getHeight()) {
                    setCursor(Cursor.DEFAULT);
                } else {
                    setCursor(Cursor.DEFAULT);
                }
            };

            addEventFilter(MouseEvent.MOUSE_MOVED, mouseMovedHandler);
            addEventFilter(MouseEvent.MOUSE_ENTERED, mouseMovedHandler);
            addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, mouseMovedHandler);

            EventHandler<MouseEvent> mousePressedHandler = evt -> {
                startX = evt.getScreenX();
                startY = evt.getScreenY();

                double x = evt.getX();
                double y = evt.getY();

                Operation operation;

                if (x < OFFSET) {
                    if (y < OFFSET) {
                        operation = Operation.RESIZE_NW;
                    } else if (y > getHeight() - OFFSET) {
                        operation = Operation.RESIZE_SW;
                    } else {
                        operation = Operation.RESIZE_W;
                    }
                } else if (x > getWidth() - OFFSET) {
                    if (y < OFFSET) {
                        operation = Operation.RESIZE_NE;
                    } else if (y > getHeight() - OFFSET) {
                        operation = Operation.RESIZE_SE;
                    } else {
                        operation = Operation.RESIZE_E;
                    }
                } else if (y < OFFSET) {
                    operation = Operation.RESIZE_N;
                } else if (y > getHeight() - OFFSET) {
                    operation = Operation.RESIZE_S;
                } else if (y < titleBar.getHeight()) {
                    operation = Operation.MOVE;
                } else {
                    operation = Operation.NONE;
                }

                setOperation(operation);
            };

            addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
            addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> setOperation(Operation.NONE));
            addEventFilter(MouseEvent.MOUSE_DRAGGED, evt -> {
                double x = evt.getScreenX();
                double y = evt.getScreenY();

                Window window = getScene().getWindow();

                double deltaX = evt.getScreenX() - startX;
                double deltaY = evt.getScreenY() - startY;

                switch (getOperation()) {

                    case NONE:
                        break;
                    case RESIZE_N:
                        if (window.getHeight() - deltaY > MIN_STAGE_HEIGHT) {
                            window.setY(y);
                            window.setHeight(Math.max(MIN_STAGE_HEIGHT, window.getHeight() - deltaY));
                            startX = x;
                            startY = y;
                        }
                        evt.consume();
                        break;
                    case RESIZE_S:
                        if (window.getHeight() + deltaY > MIN_STAGE_HEIGHT) {
                            window.setHeight(Math.max(MIN_STAGE_HEIGHT, window.getHeight() + deltaY));
                            startX = x;
                            startY = y;
                        }
                        evt.consume();
                        break;
                    case RESIZE_W:
                        if (window.getWidth() - deltaX > MIN_STAGE_WIDTH) {
                            window.setX(x);
                            window.setWidth(Math.max(MIN_STAGE_WIDTH, window.getWidth() - deltaX));
                            startX = x;
                            startY = y;
                        }
                        evt.consume();
                        break;
                    case RESIZE_E:
                        if (window.getWidth() + deltaX > MIN_STAGE_WIDTH) {
                            window.setWidth(Math.max(MIN_STAGE_WIDTH, window.getWidth() + deltaX));
                            startX = x;
                            startY = y;
                        }
                        evt.consume();
                        break;
                    case RESIZE_NW:
                        if (window.getWidth() - deltaX > MIN_STAGE_WIDTH) {
                            window.setX(x);
                            window.setWidth(Math.max(MIN_STAGE_WIDTH, window.getWidth() - deltaX));
                            startX = x;
                        }
                        if (window.getHeight() - deltaY > MIN_STAGE_HEIGHT) {
                            window.setY(y);
                            window.setHeight(Math.max(MIN_STAGE_HEIGHT, window.getHeight() - deltaY));
                            startY = y;
                        }
                        evt.consume();
                        break;
                    case RESIZE_NE:
                        if (window.getWidth() + deltaX > MIN_STAGE_WIDTH) {
                            window.setWidth(Math.max(MIN_STAGE_WIDTH, window.getWidth() + deltaX));
                            startX = x;
                        }
                        if (window.getHeight() - deltaY > MIN_STAGE_HEIGHT) {
                            window.setY(y);
                            window.setHeight(Math.max(MIN_STAGE_HEIGHT, window.getHeight() - deltaY));
                            startY = y;
                        }

                        evt.consume();
                        break;
                    case RESIZE_SW:
                        if (window.getWidth() - deltaX > MIN_STAGE_WIDTH) {
                            window.setX(x);
                            window.setWidth(Math.max(MIN_STAGE_WIDTH, window.getWidth() - deltaX));
                            startX = x;
                        }
                        if (window.getHeight() + deltaY > MIN_STAGE_HEIGHT) {
                            window.setHeight(Math.max(MIN_STAGE_HEIGHT, window.getHeight() + deltaY));
                            startY = y;
                        }

                        evt.consume();
                        break;
                    case RESIZE_SE:

                        if (window.getWidth() + deltaX > MIN_STAGE_WIDTH) {
                            window.setWidth(Math.max(MIN_STAGE_WIDTH, window.getWidth() + deltaX));
                            startX = x;
                        }
                        if (window.getHeight() + deltaY > MIN_STAGE_HEIGHT) {
                            window.setHeight(Math.max(MIN_STAGE_HEIGHT, window.getHeight() + deltaY));
                            startY = y;
                        }

                        evt.consume();
                        break;
                    case MOVE:
                        getScene().getWindow().setX(getScene().getWindow().getX() + deltaX);
                        getScene().getWindow().setY(getScene().getWindow().getY() + deltaY);
                        startX = x;
                        startY = y;
                        break;
                }
            });
        } else {
            pseudoClassStateChanged(PseudoClass.getPseudoClass("web"), true);
        }

        updateStyleBasedOnStageFocus(stage);
    }

    private final ObjectProperty<Runnable> closeHandler = new SimpleObjectProperty<>(this, "closeHandler");

    public Runnable getCloseHandler() {
        return closeHandler.get();
    }

    public ObjectProperty<Runnable> closeHandlerProperty() {
        return closeHandler;
    }

    public void setCloseHandler(Runnable closeHandler) {
        this.closeHandler.set(closeHandler);
    }

    private final ObjectProperty<Operation> operation = new SimpleObjectProperty<>(this, "operation", Operation.NONE);

    private Operation getOperation() {
        return operation.get();
    }

    private void setOperation(Operation operation) {
        this.operation.set(operation);
    }

    private void updateStyleBasedOnStageFocus(Stage stage) {
        pseudoClassStateChanged(PseudoClass.getPseudoClass("stage-focused"), stage.isFocused());
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

    class TitleBar extends HBox {

        private final Label label;

        private double lastX;
        private double lastY;
        private double lastWidth;
        private double lastHeight;

        TitleBar(SessionManager sessionManager) {
            getStyleClass().add("title-bar");

            label = new Label();
            label.getStyleClass().add("title");
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            label.setAlignment(Pos.CENTER);
            label.setMouseTransparent(true);
            HBox.setHgrow(label, Priority.ALWAYS);

            FontIcon maxIcon = new FontIcon(MaterialDesign.MDI_WINDOW_MAXIMIZE);
            FontIcon restoreIcon = new FontIcon(MaterialDesign.MDI_WINDOW_RESTORE);
            FontIcon minIcon = new FontIcon(MaterialDesign.MDI_WINDOW_MINIMIZE);
            FontIcon closeIcon = new FontIcon(MaterialDesign.MDI_CLOSE);

            ToggleButton maxButton = new ToggleButton();
            maxButton.setGraphic(maxIcon);
            maxButton.setFocusTraversable(false);
            maxButton.selectedProperty().addListener(it -> maxButton.setGraphic(maxButton.isSelected() ? restoreIcon : maxIcon));
            maxButton.setOnAction(evt -> {
                Stage stage = (Stage) getScene().getWindow();

                if (maxButton.isSelected()) {
                    // store current bounds
                    lastX = stage.getX();
                    lastY = stage.getY();
                    lastWidth = stage.getWidth();
                    lastHeight = stage.getHeight();

                    // Get current screen of the stage
                    ObservableList<Screen> screens = Screen.getScreensForRectangle(new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()));

                    // Change stage properties
                    Rectangle2D bounds = screens.get(0).getVisualBounds();
                    stage.setX(bounds.getMinX());
                    stage.setY(bounds.getMinY());
                    stage.setWidth(bounds.getWidth());
                    stage.setHeight(bounds.getHeight());
                } else {
                    stage.setX(lastX);
                    stage.setY(lastY);
                    stage.setWidth(lastWidth);
                    stage.setHeight(lastHeight);
                }
            });

            setOnMouseClicked(evt -> {
                if (evt.getButton() == MouseButton.PRIMARY && evt.getClickCount() == 2) {
                    maxButton.fire();
                }
            });

            Button minButton = new Button();
            minButton.setFocusTraversable(false);
            minButton.setGraphic(minIcon);
            minButton.setOnAction(evt -> {
                Stage stage = (Stage) getScene().getWindow();
                stage.setIconified(true);
            });

            Button closeButton = new Button();
            closeButton.disableProperty().bind(closeHandlerProperty().isNull());
            closeButton.setFocusTraversable(false);
            closeButton.setGraphic(closeIcon);
            closeButton.setOnAction(evt -> getCloseHandler().run());

            NavigationView navigationView = new NavigationView(sessionManager);

            getChildren().addAll(closeButton, label, navigationView, minButton, maxButton);
        }

        public Label getLabel() {
            return label;
        }
    }
}
