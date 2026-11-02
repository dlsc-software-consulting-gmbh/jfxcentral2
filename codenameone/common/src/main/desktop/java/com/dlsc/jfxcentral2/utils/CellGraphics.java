package com.dlsc.jfxcentral2.utils;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Cell;
import javafx.scene.layout.Region;

/**
 * Holds a cell's graphic to the width of the cell.
 *
 * <p>Codename One 7.0.280 lays a cell's graphic out at its preferred width,
 * which for a summary on one line is far wider than the list, so the text ran
 * past the edge instead of wrapping. JavaFX gives the graphic the cell's
 * width at most; this binds the graphic's preferred width to that. Once the
 * layer does it itself, the calls can go.</p>
 */
public final class CellGraphics {

    private CellGraphics() {
    }

    public static void fitToCell(Cell<?> cell, Region graphic) {
        // The layer places the graphic past the native theme's padding as
        // well as the cell's, so the room is measured from where the graphic
        // really starts; that does not depend on the width, so it settles.
        graphic.prefWidthProperty().bind(Bindings.createDoubleBinding(
                () -> Math.max(0, cell.getWidth() - Math.max(graphic.getLayoutX(), cell.getInsets().getLeft())
                        - cell.getInsets().getRight()),
                cell.widthProperty(), cell.paddingProperty(), graphic.layoutXProperty()));
    }
}
