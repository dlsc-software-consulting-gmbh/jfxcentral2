package com.dlsc.jfxcentral2.utilities.paintpicker.impl;

import com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel.ColorFormat;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import one.jpro.platform.routing.CopyUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class FXColorInfoPane extends BorderPane {
    private static final String COLOR_REG = "^#([a-fA-F0-9]){6}";

    private final TextField fieldHsl = new TextField();
    private final TextField fieldRgb = new TextField();
    private final TextField fieldHex = new TextField();
    private final TextField fieldHsb = new TextField();

    private final ComboBox<CodeType> comboBoxCodeType = new ComboBox<>(FXCollections.observableArrayList(CodeType.values()));
    private PaintPickerController paintPickerController;
    private Button copyCodeBtn;

    private enum CodeType{
        JAVAFX_CODE("JavaFX Code"),
        JAVAFX_CSS("JavaFX CSS");

        private final String name;
        CodeType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    public FXColorInfoPane() {
        getStyleClass().add("info-root-pane");
        comboBoxCodeType.getStyleClass().add("custom-combobox");
        comboBoxCodeType.getSelectionModel().select(0);
        fieldHsb.setText("0,0,0,1.0");
        fieldHsl.setText("0,0,0,1.0");
        fieldRgb.setText("0,0,0,1.0");
        fieldHex.setText("#000000FF");
        managedProperty().bind(visibleProperty());

        buildUI();
    }

    public void setPaintPickerController(PaintPickerController paintPickerController) {
        this.paintPickerController = paintPickerController;

        paintPickerController.paintProperty().addListener((observableValue, oldValue, newValue) -> {
            refreshData();
            copyFXCodeOnClick();
        });

        paintPickerController.getColorPicker().getHexaTextfield().textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.matches(COLOR_REG)) {
                refreshData();
            }
        });
        copyFXCodeOnClick();
        comboBoxCodeType.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> copyFXCodeOnClick());
        //refreshData();
    }

    public void refreshData() {
        if (paintPickerController == null) {
            return;
        }
        String text = paintPickerController.getColorPicker().getHexaTextfield().getText();
        if (!text.matches(COLOR_REG)) {
            return;
        }
        Color color;
        try {
            color = Color.web(text);
        } catch (Exception e) {
            color = Color.WHITE;
        }
        fieldHex.setText(ColorUtil.colorToWebHex(paintPickerController.getColorPicker().getValue()));
        fieldHsl.setText(ColorUtil.colorToHsl(color).toString());
        fieldHsb.setText(ColorUtil.colorToHsb(color).toString());
        fieldRgb.setText(ColorUtil.formatColorToString(color, ColorFormat.INTEGER));
    }

    private void buildUI() {
        VBox leftPane = new VBox(2, createColorPane("HSB:", fieldHsb), createColorPane("HSL:", fieldHsl));
        VBox rightPane = new VBox(2, createColorPane("RGB:", fieldRgb), createColorPane("Hex:", fieldHex));
        HBox centerBox = new HBox(2, leftPane, rightPane);
        setCenter(centerBox);
        copyCodeBtn = new Button("", new FontIcon(IkonUtil.copy));
        copyCodeBtn.getStyleClass().addAll("fill-button", "copy-button");

        HBox bottomBox = new HBox(2, new Label("Copy:"), comboBoxCodeType, copyCodeBtn);
        comboBoxCodeType.setMaxWidth(Double.MAX_VALUE);
        HBox.setMargin(bottomBox, new Insets(0, 0, 0, 5));
        HBox.setHgrow(comboBoxCodeType, Priority.ALWAYS);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        setBottom(bottomBox);
        setPadding(new Insets(3));
        setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
    }

    private void copyFXCodeOnClick() {
        CodeType type = comboBoxCodeType.getSelectionModel().getSelectedItem();
        if (type != null) {
            Paint fxPaint = paintPickerController.paintProperty().get();
            if (type == CodeType.JAVAFX_CODE) {
                String javaCode = PaintConvertUtil.convertPaintToJavaCode(fxPaint);
                Platform.runLater(() -> CopyUtil.setCopyOnClick(copyCodeBtn, javaCode));
            } else if (type == CodeType.JAVAFX_CSS) {
                String cssCode = PaintConvertUtil.convertPaintToCss(fxPaint);
                Platform.runLater(() -> CopyUtil.setCopyOnClick(copyCodeBtn, cssCode));
            }
        }
    }

    private BorderPane createColorPane(String text, TextField field) {
        BorderPane pane = new BorderPane();
        Label label = new Label(text);
        BorderPane.setAlignment(label, Pos.CENTER);
        pane.setLeft(label);
        BorderPane.setMargin(field, new Insets(2));
        pane.setCenter(field);
        Button copyBtn = new Button("", new FontIcon(IkonUtil.copy));
        copyBtn.getStyleClass().addAll("fill-button", "copy-button");
        pane.setRight(copyBtn);
        Platform.runLater(() -> CopyUtil.setCopyOnClick(copyBtn, field.getText()));
        field.textProperty().addListener((ob, ov, nv) -> Platform.runLater(() -> CopyUtil.setCopyOnClick(copyBtn, field.getText())));
        return pane;
    }

}
