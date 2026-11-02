package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;

/**
 * Provides size-based pseudo-class support to {@link Node} instances, typically used with subclasses of {@link javafx.scene.control.Control}
 * or {@link javafx.scene.layout.Pane}. This class allows nodes to dynamically change their styling based on the size property
 * by adding size-specific pseudo-classes to the node.
 *
 * <p>This class manages three size categories represented as pseudo-classes:
 * <ul>
 * <li>'sm' for small size</li>
 * <li>'md' for medium size</li>
 * <li>'lg' for large size</li>
 * </ul>
 * Additionally, it manages combinations of sizes:
 * <ul>
 * <li>'sm-md' for small or medium size</li>
 * <li>'md-lg' for medium or large size</li>
 * </ul>
 *
 * <p>Usage involves creating an instance of this class and attaching it to a node. The size state of the node can then
 * be updated by changing the {@code size} property of this class, which in turn updates the pseudo-classes applied to the node
 * based on the defined size thresholds.
 *
 * @see PseudoClass
 * @see Node
 */
public class SizeSupport {

    private static final PseudoClass SMALL_PSEUDO_CLASS = PseudoClass.getPseudoClass("sm");
    private static final PseudoClass MEDIUM_PSEUDO_CLASS = PseudoClass.getPseudoClass("md");
    private static final PseudoClass LARGE_PSEUDO_CLASS = PseudoClass.getPseudoClass("lg");

    public SizeSupport(Node node) {
        sizeStateChanged(node, getSize());
        sizeProperty().addListener((ob, ov, newSize) -> sizeStateChanged(node, newSize));
    }

    /**
     * sm-md = small or medium
     */
    private static final PseudoClass SMALL_OR_MEDIUM_PSEUDO_CLASS = PseudoClass.getPseudoClass("sm-md");

    /**
     * md-lg = medium or large
     */
    private static final PseudoClass MEDIUM_OR_LARGE_PSEUDO_CLASS = PseudoClass.getPseudoClass("md-lg");


    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(Size.LARGE);

    public final Size getSize() {
        return size.get();
    }

    public final void setSize(Size size) {
        sizeProperty().set(size);
    }

    public final ObjectProperty<Size> sizeProperty() {
        return size;
    }

    private void sizeStateChanged(Node node, Size newTrend) {
        node.pseudoClassStateChanged(SMALL_PSEUDO_CLASS, newTrend != null && newTrend.isSmall());
        node.pseudoClassStateChanged(MEDIUM_PSEUDO_CLASS, newTrend != null && newTrend.isMedium());
        node.pseudoClassStateChanged(LARGE_PSEUDO_CLASS, newTrend != null && newTrend.isLarge());

        // sm-md = small or medium
        node.pseudoClassStateChanged(SMALL_OR_MEDIUM_PSEUDO_CLASS, newTrend != null && (newTrend.isSmall() || newTrend.isMedium()));
        // md-lg = medium or large
        node.pseudoClassStateChanged(MEDIUM_OR_LARGE_PSEUDO_CLASS, newTrend != null && (newTrend.isMedium() || newTrend.isLarge()));
    }

}