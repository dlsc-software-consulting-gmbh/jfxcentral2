package com.dlsc.jfxcentral2.utilities.cssplayground.impl.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
/**
 * This class is derived from the ShowcaseFX project, available at
 * <a href="https://github.com/dlsc-software-consulting-gmbh/ShowcaseFX">https://github.com/dlsc-software-consulting-gmbh/ShowcaseFX</a>.
 *
 * Modifications have been made to the original code. For specific licensing information,
 * please refer to the licensing section of the original
 * <a href="https://github.com/dlsc-software-consulting-gmbh/ShowcaseFX">ShowcaseFX project</a>.
 *
 * If you are interested in learning more about ShowcaseFX, it is recommended to visit the
 * original repository rather than relying on the modified version contained here.
 *
 */
public class SimpleWindowView extends VBox {

    public SimpleWindowView() {
        setPrefHeight(230.0);
        setPrefWidth(300.0);

        MenuBar menuBar = new MenuBar();
        menuBar.setId("MenuBar");
        menuBar.setPrefWidth(200.0);

        Menu fileMenu = new Menu("File");
        fileMenu.setMnemonicParsing(false);

        MenuItem closeMenuItem = new MenuItem("Close");
        closeMenuItem.setMnemonicParsing(false);

        Menu editMenu = new Menu("Edit");
        editMenu.setMnemonicParsing(false);

        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setMnemonicParsing(false);

        Menu helpMenu = new Menu("Help");
        helpMenu.setMnemonicParsing(false);

        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setMnemonicParsing(false);

        ToolBar toolBar = new ToolBar();
        toolBar.setPrefWidth(200.0);

        Button newButton = new Button("New");
        newButton.setMnemonicParsing(false);

        Button deleteButton = new Button("Delete");
        deleteButton.setId("DeleteButton");
        deleteButton.setMnemonicParsing(false);

        Button saveButton = new Button("Save");
        saveButton.setMnemonicParsing(false);

        Button exitButton = new Button("Exit");
        exitButton.setMnemonicParsing(false);

        TabPane tabPane = new TabPane();
        VBox.setVgrow(tabPane, Priority.ALWAYS);
        tabPane.setPrefHeight(130.0);
        tabPane.setPrefWidth(200.0);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab = new Tab("Example Controls");

        GridPane gridPane = new GridPane();
        gridPane.setId("GridPane");
        gridPane.setHgap(10.0);
        gridPane.setVgap(10.0);

        RadioButton radioButton = new RadioButton("RadioButton 1");
        radioButton.setId("RadioButton1");
        radioButton.setMnemonicParsing(false);
        GridPane.setColumnIndex(radioButton, 0);
        GridPane.setRowIndex(radioButton, 0);

        ToggleGroup toggle2 = new ToggleGroup();
        radioButton.setToggleGroup(toggle2);

        RadioButton radioButton0 = new RadioButton("RadioButton 2");
        radioButton0.setId("RadioButton2");
        radioButton0.setMnemonicParsing(false);
        radioButton0.setSelected(true);
        radioButton0.setToggleGroup(toggle2);
        GridPane.setColumnIndex(radioButton0, 0);
        GridPane.setRowIndex(radioButton0, 1);

        CheckBox checkBox = new CheckBox("CheckBox");
        checkBox.setMnemonicParsing(false);
        checkBox.setSelected(true);
        GridPane.setColumnIndex(checkBox, 0);
        GridPane.setRowIndex(checkBox, 2);

        CheckBox checkBox0 = new CheckBox("CheckBox");
        GridPane.setValignment(checkBox0, VPos.TOP);
        checkBox0.setMnemonicParsing(false);
        GridPane.setColumnIndex(checkBox0, 0);
        GridPane.setRowIndex(checkBox0, 3);

        HBox hBox = new HBox(5.0);
        GridPane.setColumnIndex(hBox, 0);
        GridPane.setColumnSpan(hBox, 2);
        GridPane.setRowIndex(hBox, 4);
        hBox.setAlignment(Pos.CENTER);
        hBox.setId("HBox");

        ProgressBar progressBar = new ProgressBar();
        HBox.setHgrow(progressBar, Priority.ALWAYS);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressBar.setPrefWidth(-1.0);
        progressBar.setProgress(0.68);
        HBox.setMargin(progressBar, new Insets(0.0, 5.0, 0.0, 0.0));

        Button button3 = new Button("Cancel");
        button3.setMnemonicParsing(false);

        Button button4 = new Button("Save...");
        button4.setDefaultButton(true);
        button4.setMnemonicParsing(false);

        TextArea textArea = new TextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent vulputate sem id velit sagittis quis imperdiet nunc imperdiet. Etiam consequat purus nec nibh viverra aliquam. Donec urna odio, posuere quis placerat non, tristique a augue. In hac habitasse platea dictumst. Cras eget turpis dui. Pellentesque vel leo arcu, et blandit quam. Integer congue tempus purus, ac rhoncus neque semper nec. Integer elit mauris, dictum non sodales sed, semper in urna. Mauris rutrum faucibus faucibus. Praesent id dui dignissim massa mollis tempus in ut mi. Curabitur augue turpis, lacinia ac vehicula id, porta et nisl. Quisque id porttitor eros. Aenean eget fringilla nisi. Etiam posuere cursus pharetra. Aenean aliquam nisl nec sapien viverra id eleifend velit elementum. In et arcu in ligula vestibulum rhoncus. Integer nec odio odio. Nulla scelerisque faucibus urna, sit amet viverra arcu congue eget. Aliquam ut sapien justo. Mauris eu velit massa. Pellentesque tristique aliquet porttitor. Vestibulum rhoncus tristique arcu, sed consectetur ante molestie nec. Nunc egestas posuere fermentum. Sed est urna, tempus sed tempor ac, pulvinar vel dui. Morbi tortor mi, semper non aliquam at, consectetur eu quam.");
        GridPane.setColumnIndex(textArea, 1);
        GridPane.setColumnSpan(textArea, 1);
        GridPane.setRowIndex(textArea, 0);
        GridPane.setRowSpan(textArea, 4);
        textArea.setMaxHeight(Double.MAX_VALUE);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setPrefHeight(70.0);
        textArea.setPrefWidth(120.0);
        textArea.setWrapText(true);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.SOMETIMES);
        columnConstraints.setMinWidth(10.0);

        ColumnConstraints columnConstraints0 = new ColumnConstraints();
        columnConstraints0.setHgrow(Priority.ALWAYS);
        columnConstraints0.setMinWidth(10.0);
        gridPane.setPadding(new Insets(10.0));

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMaxHeight(14.0);
        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(14.0);
        rowConstraints.setVgrow(Priority.SOMETIMES);

        RowConstraints rowConstraints0 = new RowConstraints();
        rowConstraints0.setMaxHeight(15.0);
        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(15.0);
        rowConstraints0.setVgrow(Priority.SOMETIMES);

        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setMaxHeight(15.0);
        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(15.0);
        rowConstraints1.setVgrow(Priority.SOMETIMES);

        RowConstraints rowConstraints2 = new RowConstraints();
        rowConstraints2.setMaxHeight(Double.MAX_VALUE);
        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setPrefHeight(15.0);
        rowConstraints2.setVgrow(Priority.ALWAYS);

        RowConstraints rowConstraints3 = new RowConstraints();
        rowConstraints3.setMaxHeight(20.0);
        rowConstraints3.setMinHeight(10.0);
        rowConstraints3.setPrefHeight(20.0);
        rowConstraints3.setVgrow(Priority.NEVER);
        tab.setContent(gridPane);

        Tab tab2 = new Tab("Tab 2");
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setId("Content");
        anchorPane.setMinHeight(0.0);
        anchorPane.setMinWidth(0.0);
        anchorPane.setPrefHeight(180.0);
        anchorPane.setPrefWidth(200.0);
        tab2.setContent(anchorPane);

        Tab tab3 = new Tab("Tab 3");
        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setId("Content");
        anchorPane0.setMinHeight(0.0);
        anchorPane0.setMinWidth(0.0);
        anchorPane0.setPrefHeight(180.0);
        anchorPane0.setPrefWidth(200.0);
        tab3.setContent(anchorPane0);

        // MenuBar setup
        fileMenu.getItems().add(closeMenuItem);
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
        editMenu.getItems().add(deleteMenuItem);
        helpMenu.getItems().add(aboutMenuItem);
        getChildren().add(menuBar);

        // ToolBar setup
        toolBar.getItems().addAll(newButton, deleteButton, saveButton, exitButton);
        getChildren().add(toolBar);

        // GridPane setup
        gridPane.getChildren().addAll(radioButton, radioButton0, checkBox, checkBox0, hBox, textArea);
        hBox.getChildren().addAll(progressBar, button3, button4);
        gridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints0);
        gridPane.getRowConstraints().addAll(rowConstraints, rowConstraints0, rowConstraints1, rowConstraints2, rowConstraints3);

        // TabPane setup
        tabPane.getTabs().addAll(tab, tab2, tab3);
        getChildren().add(tabPane);

    }
}
