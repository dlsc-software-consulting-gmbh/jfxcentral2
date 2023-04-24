package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.Sizeable;
import com.dlsc.jfxcentral2.components.Target;
import com.dlsc.jfxcentral2.components.Targetable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class NodeUtil {
    private NodeUtil() {
    }

    public static VBox createVBox(Node... nodes) {
        VBox box = new VBox();
        setAll(box, nodes);
        return box;
    }

    public static HBox createHBox(Node... nodes) {
        HBox box = new HBox();
        setAll(box, nodes);
        return box;
    }

    /**
     * If the node is not empty, add it to the pane
     */
    public static void setAll(Pane pane, Node... nodes) {
        pane.getChildren().clear();
        for (Node node : nodes) {
            if (node != null) {
                pane.getChildren().add(node);
            }
        }
    }

    public static void setHBoxMargin(Node node, Insets insets) {
        if (node != null) {
            HBox.setMargin(node, insets);
        }
    }

    public static void setVBoxMargin(Node node, Insets insets) {
        if (node != null) {
            VBox.setMargin(node, insets);
        }
    }

    public static Label createLabel(String... styleClass) {
        Label label = new Label();
        label.getStyleClass().addAll(styleClass);
        return label;
    }

    public static Label createLabel(String text, Node graphic, String... styleClass) {
        Label label = createLabel(styleClass);
        label.setText(text);
        label.setGraphic(graphic);
        return label;
    }

    public static Label createLabel(String text, Ikon ikon, String... styleClass) {
        Label label = createLabel(styleClass);
        label.setText(text);
        FontIcon fontIcon = new FontIcon();
        fontIcon.setIconCode(ikon);
        label.setGraphic(fontIcon);
        return label;
    }

    private static void activateTargetPseudoClass(Node node) {
        if (node instanceof Targetable template) {
            Target target = template.getTarget();
            node.pseudoClassStateChanged(Targetable.DESKTOP_PSEUDOCLASS_STATE, target.isDesktop());
            node.pseudoClassStateChanged(Targetable.BROWSER_PSEUDOCLASS_STATE, target.isBrowser());
            node.pseudoClassStateChanged(Targetable.MOBILE_PSEUDOCLASS_STATE, target.isMobile());
        }
    }

    private static void activateSizePseudoClass(Node node) {
        if (node instanceof Sizeable template) {
            Size size = template.getSize();
            node.pseudoClassStateChanged(Sizeable.LARGE_PSEUDOCLASS_STATE, size.isLarge());
            node.pseudoClassStateChanged(Sizeable.MEDIUM_PSEUDOCLASS_STATE, size.isMedium());
            node.pseudoClassStateChanged(Sizeable.SMALL_PSEUDOCLASS_STATE, size.isSmall());
            node.pseudoClassStateChanged(Sizeable.SMALL_OR_MEDIUM_PSEUDOCLASS_STATE, size.isSmall() || size.isMedium());
            node.pseudoClassStateChanged(Sizeable.MEDIUM_OR_LARGE_PSEUDOCLASS_STATE, size.isMedium() || size.isLarge());
        }
    }

    public static void initTargetPseudoClass(Node node, Runnable onTargetInvalidate) {
        activateSizePseudoClass(node);
        if (node instanceof Targetable template) {
            template.targetProperty().addListener(it -> {
                activateTargetPseudoClass(node);
                if (onTargetInvalidate != null) {
                    onTargetInvalidate.run();
                }
            });
        }
    }

    public static void initSizePseudoClass(Node node, Runnable onSizeInvalidate) {
        activateTargetPseudoClass(node);
        if (node instanceof Sizeable template) {
            template.sizeProperty().addListener(it -> {
                activateSizePseudoClass(node);
                if (onSizeInvalidate != null) {
                    onSizeInvalidate.run();
                }
            });
        }
    }

    public static void initSizeAndTargetPseudoClass(Node node, Runnable onTargetInvalidate, Runnable onSizeInvalidate) {
        initTargetPseudoClass(node, onTargetInvalidate);
        initSizePseudoClass(node, onSizeInvalidate);
    }

    public static void initSizeAndTargetPseudoClass(Node node) {
        initTargetPseudoClass(node, null);
        initSizePseudoClass(node, null);
    }

}
