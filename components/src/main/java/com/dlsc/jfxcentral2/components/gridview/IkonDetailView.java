package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class IkonDetailView extends DetailView<Ikon> {

    public IkonDetailView(Ikon item) {
        super(item);
        getStyleClass().add("ikon-detail-view");
        if (item.getClass().getSimpleName().equals("PaymentFont")) {
            getStyleClass().add("payment-font-detail-view");
        }
        FontIcon fontIcon = new FontIcon(item);
        StackPane previewPane = new StackPane(fontIcon);
        previewPane.getStyleClass().add("ikon-preview-wrapper");
        HBox.setHgrow(previewPane, Priority.ALWAYS);

        FlowPane flowPane = new FlowPane();
        flowPane.getStyleClass().add("ikon-info-grid-pane");
        HBox.setHgrow(flowPane, Priority.ALWAYS);

        addRow(flowPane, "Icon Literal:", item.getDescription());
        addRow(flowPane, "Css Code:", "-fx-icon-code: " + item.getDescription());
        addRow(flowPane, "Java Code:", item.getClass().getSimpleName() + "." + fontIcon.getIconCode());
        addRow(flowPane, "Unicode:", "\\u" + Integer.toHexString(item.getCode()));
        HBox detailContent = new HBox(previewPane, flowPane);
        detailContent.getStyleClass().add("detail-content");
        getChildren().setAll(detailContent);
    }

    private void addRow(FlowPane flowPane, String title, String contentText) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().addAll("title");
        titleLabel.managedProperty().bind(titleLabel.visibleProperty());

        TextField textField = new TextField(contentText);
        textField.setFocusTraversable(false);
        textField.setEditable(false);
        textField.setContextMenu(null);
        textField.managedProperty().bind(textField.visibleProperty());

        Button button = new Button();
        button.setGraphic(new FontIcon(IkonUtil.copy));
        button.getStyleClass().addAll("fill-button", "copy-button");
        button.managedProperty().bind(button.visibleProperty());

        button.setOnAction(event -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(contentText);
            clipboard.setContent(content);
        });
        HBox box = new HBox(titleLabel, textField, button);
        box.getStyleClass().add("row-box");
        flowPane.getChildren().add(box);
    }
}
