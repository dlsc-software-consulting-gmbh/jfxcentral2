package com.dlsc.jfxcentral2.components.gridview;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class IkonCellView extends CellView<Ikon> {

    public IkonCellView(Ikon item) {
        super(item);

        getStyleClass().add("ikon-cell-view");

        FontIcon fontIcon = new FontIcon(item);
        fontIcon.getStyleClass().add("preview-icon");

        Label ikonLabel = new Label(item.getDescription());
        ikonLabel.managedProperty().bind(ikonLabel.visibleProperty());
        ikonLabel.getStyleClass().add("ikon-label");
        ikonLabel.setWrapText(true);

        VBox contentBox = new VBox(fontIcon, ikonLabel);
        contentBox.getStyleClass().add("content-box");
        ikonLabel.setMaxHeight(Double.MAX_VALUE);
        getChildren().setAll(contentBox);
    }
}
