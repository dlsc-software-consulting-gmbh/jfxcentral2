package com.dlsc.jfxcentral2.mobile.utils;

import javafx.scene.Node;

/**
 * This interface should be implemented by any view that needs to provide a node
 * which should be preferred to gain focus when the view is displayed.
 * <p>
 * The main purpose of this interface is to ensure that a specific node within the
 * view gains focus first when the view is displayed. Classes implementing this interface
 * should return the node that needs to be focused in the {@link #getPreferredFocusedNode()} method.
 * </p>
 */
public interface PreferredFocusedNodeProvider {

    /**
     * Returns the node that should be preferred to gain focus when the view is displayed.
     * <p>
     * Implementing classes should ensure that the returned node requests focus
     * during its initialization.
     * </p>
     *
     * @return the node that should be preferred to gain focus
     */
    Node getPreferredFocusedNode();
}