package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class IkonDetailView extends DetailView<Ikon> {
    public IkonDetailView(Ikon item) {
        super(item);
        getStyleClass().add("ikon-detail-view");

        FontIcon fontIcon = new FontIcon(item);
        StackPane previewPane = new StackPane(fontIcon);
        previewPane.getStyleClass().add("ikon-preview-wrapper");

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("ikon-info-grid-pane");

        for (int i = 0; i < 2; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setValignment(VPos.CENTER);
            gridPane.getRowConstraints().addAll(rowConstraints);
        }

        for (int i = 0; i < 6; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHalignment(HPos.LEFT);
        }

        addRow(gridPane, 0, "Icon Literal:", item.getDescription(), true);
        addRow(gridPane, 0, "Css Code:", "-fx-icon-code: " + item.getDescription(), false);
        addRow(gridPane, 1, "Java Code:", item.getClass().getSimpleName() + "." + fontIcon.getIconCode(), true);
        addRow(gridPane, 1, "Unicode:", "\\u" + Integer.toHexString(item.getCode()), false);
        HBox detailContent = new HBox(previewPane, gridPane);
        detailContent.getStyleClass().add("detail-content");
        getChildren().setAll(detailContent);
    }

    private void addRow(GridPane gridPane, int row, String title, String contentText, boolean isLeft) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add(isLeft ? "left" : "right");
        titleLabel.getStyleClass().addAll("title", isLeft ? "title-1" : "title-2");
        titleLabel.managedProperty().bind(titleLabel.visibleProperty());

        TextField textField = new TextField(contentText);
        textField.getStyleClass().add(isLeft ? "left" : "right");
        textField.setEditable(false);
        textField.setContextMenu(null);
        textField.managedProperty().bind(textField.visibleProperty());

        Button button = new Button();
        button.setGraphic(new FontIcon(IkonUtil.copy));
        button.getStyleClass().addAll("fill-button", "copy-button");
        button.getStyleClass().add(isLeft ? "left" : "right");
        button.managedProperty().bind(button.visibleProperty());

        button.setOnAction(event -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(contentText);
            clipboard.setContent(content);
        });

        gridPane.add(titleLabel, isLeft ? 0 : 3, row);
        gridPane.add(textField, isLeft ? 1 : 4, row);
        gridPane.add(button, isLeft ? 2 : 5, row);
    }
}
