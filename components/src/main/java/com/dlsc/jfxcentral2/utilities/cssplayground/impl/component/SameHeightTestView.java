package com.dlsc.jfxcentral2.utilities.cssplayground.impl.component;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
public class SameHeightTestView extends AnchorPane {

    private final Button horizFirstButton;
    private final TextField vertFirstTextField;
    private final Region horizBaseLine;
    private final Region vertBaseLine;
    private final Region arrowButtonLeftLine;
    private final Region arrowButtonRightLine;
    private final Region arrowLeftLine;
    private final Region arrowRightLine;
    private final ComboBox editableCombo;
    private final AnchorPane arrowButtonContainer;
    private Node arrowButton;
    private Node arrow;

    public SameHeightTestView() {
        setId("AnchorPane");
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefWidth(-1);
        setPrefHeight(-1);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setId("VBox");
        vBox.setLayoutX(15.0);
        vBox.setLayoutY(19.0);
        vBox.setSpacing(15.0);

        Label label = new Label("All of these controls should be the same height / width.");
        label.setFont(new Font("System Bold", 13.0));

        HBox hBox = new HBox(3.0);
        hBox.setMaxHeight(USE_PREF_SIZE);
        CheckBox checkBox5 = new CheckBox("CheckBox");
        CheckBox checkBox6 = new CheckBox("Selected");
        checkBox6.setSelected(true);
        CheckBox checkBox7 = new CheckBox("Indeterminate");
        checkBox7.setIndeterminate(true);
        hBox.getChildren().addAll(checkBox5, checkBox6, checkBox7);

        Insets insets = new Insets(20, 0, 0, 0);
        HBox hBox0 = new HBox(3.0);
        hBox0.setMaxHeight(USE_PREF_SIZE);
        hBox0.setStyle("-fx-border-color: #f09f9b transparent #f09f9b transparent;");
        CheckBox checkBox = new CheckBox("CheckBox");
        CheckBox checkBox0 = new CheckBox("Selected");
        checkBox0.setSelected(true);
        CheckBox checkBox1 = new CheckBox("Indeterminate");
        checkBox1.setIndeterminate(true);
        hBox0.getChildren().addAll(checkBox, checkBox0, checkBox1);

        VBox vBox0 = new VBox(3.0);
        HBox.setMargin(vBox0, insets);
        vBox0.setMaxHeight(USE_PREF_SIZE);
        vBox0.setStyle("-fx-border-color: transparent #f09f9b transparent #f09f9b;");
        CheckBox checkBox2 = new CheckBox("CheckBox");
        CheckBox checkBox3 = new CheckBox("Selected");
        checkBox3.setSelected(true);
        CheckBox checkBox4 = new CheckBox("Indeterminate");
        checkBox4.setIndeterminate(true);
        vBox0.getChildren().addAll(checkBox2, checkBox3, checkBox4);

        HBox hBox1 = new HBox(3.0);
        hBox1.setMaxHeight(USE_PREF_SIZE);

        TitledPane titledPane = new TitledPane();
        titledPane.setAnimated(false);
        titledPane.setExpanded(false);
        titledPane.setPrefWidth(80.0);
        titledPane.setText("TitledPane");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setId("Content");
        anchorPane.setMinHeight(0.0);
        anchorPane.setMinWidth(0.0);
        anchorPane.setPrefHeight(40.0);
        anchorPane.setPrefWidth(80.0);
        titledPane.setContent(anchorPane);

        Button button = new Button("Button");
        button.setMnemonicParsing(false);

        ToggleButton toggleButton = new ToggleButton("Toggle");
        toggleButton.setMnemonicParsing(false);
        toggleButton.setPrefWidth(90.0);
        toggleButton.setSelected(true);

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleButton.setToggleGroup(toggleGroup);

        MenuButton menuButton = new MenuButton("MenuButton");
        menuButton.setMnemonicParsing(false);
        menuButton.setPrefWidth(100.0);

        MenuItem menuItem = new MenuItem("Action 1");
        menuItem.setMnemonicParsing(false);

        MenuItem menuItem0 = new MenuItem("Action 2");
        menuItem0.setMnemonicParsing(false);

        SplitMenuButton splitMenuButton = new SplitMenuButton();
        splitMenuButton.setMnemonicParsing(false);
        splitMenuButton.setPrefWidth(100.0);
        splitMenuButton.setText("SplitMenuButton");

        MenuItem menuItem1 = new MenuItem("Action 1");
        menuItem1.setMnemonicParsing(false);

        MenuItem menuItem2 = new MenuItem("Action 2");
        menuItem2.setMnemonicParsing(false);

        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.setPrefWidth(60.0);

        ComboBox comboBox = new ComboBox();
        comboBox.setPrefWidth(60.0);

        ComboBox comboBox0 = new ComboBox();
        comboBox0.setEditable(true);
        comboBox0.setPrefWidth(60.0);
        comboBox0.setPromptText("Choose");

        TextField textField = new TextField("TextField");
        textField.setPrefWidth(100.0);
        textField.setPromptText("Textfield");

        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(100.0);
        passwordField.setPromptText("Password");
        passwordField.setText("password");
        VBox.setMargin(hBox1, new Insets(20.0, 0.0, 0.0, 0.0));

        HBox hBox2 = new HBox(3.0);
        VBox.setMargin(hBox2, insets);
        hBox2.setMaxHeight(USE_PREF_SIZE);
        hBox2.setStyle("-fx-border-color: #f09f9b transparent #f09f9b transparent;");

        TitledPane titledPane0 = new TitledPane();
        titledPane0.setAnimated(false);
        titledPane0.setExpanded(false);
        titledPane0.setPrefWidth(80.0);
        titledPane0.setText("TitledPane");

        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setId("Content");
        anchorPane0.setMinHeight(0.0);
        anchorPane0.setMinWidth(0.0);
        anchorPane0.setPrefHeight(40.0);
        anchorPane0.setPrefWidth(80.0);
        titledPane0.setContent(anchorPane0);

        Button button0 = new Button("Button");
        button0.setMnemonicParsing(false);

        ToggleButton toggleButton0 = new ToggleButton("Toggle");
        toggleButton0.setMnemonicParsing(false);
        toggleButton0.setPrefWidth(90.0);
        toggleButton0.setSelected(true);

        ToggleGroup toggleGroup0 = new ToggleGroup();
        toggleButton0.setToggleGroup(toggleGroup0);

        MenuButton menuButton0 = new MenuButton("MenuButton");
        menuButton0.setMnemonicParsing(false);
        menuButton0.setPrefWidth(100.0);

        MenuItem menuItem3 = new MenuItem("Action 1");
        menuItem3.setMnemonicParsing(false);

        MenuItem menuItem4 = new MenuItem("Action 2");
        menuItem4.setMnemonicParsing(false);

        SplitMenuButton splitMenuButton0 = new SplitMenuButton();
        splitMenuButton0.setMnemonicParsing(false);
        splitMenuButton0.setPrefWidth(100.0);
        splitMenuButton0.setText("SplitMenuButton");

        MenuItem menuItem5 = new MenuItem("Action 1");
        menuItem5.setMnemonicParsing(false);

        MenuItem menuItem6 = new MenuItem("Action 2");
        menuItem6.setMnemonicParsing(false);

        ChoiceBox choiceBox0 = new ChoiceBox();
        choiceBox0.setPrefWidth(60.0);

        ComboBox comboBox1 = new ComboBox();
        comboBox1.setPrefWidth(60.0);

        ComboBox comboBox2 = new ComboBox();
        comboBox2.setEditable(true);
        comboBox2.setPrefWidth(60.0);
        comboBox2.setPromptText("Choose");

        TextField textField0 = new TextField("TextField");
        textField0.setPrefWidth(100.0);
        textField0.setPromptText("Textfield");

        PasswordField passwordField0 = new PasswordField();
        passwordField0.setPrefWidth(100.0);
        passwordField0.setPromptText("Password");
        passwordField0.setText("password");
        hBox2.setPadding(new Insets(0.0));

        Label label0 = new Label("All of these controls should be text aligned");
        VBox.setMargin(label0, insets);

        label0.setFont(new Font("System Bold", 13.0));

        HBox hBox3 = new HBox(5.0);
        VBox.setMargin(hBox3, insets);
        hBox3.setAlignment(Pos.TOP_LEFT);
        hBox3.setId("HBox");

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.TOP_LEFT);
        stackPane.setId("StackPane");

        VBox vBox1 = new VBox(3.0);

        HBox hBox4 = new HBox(3.0);
        hBox4.setAlignment(Pos.TOP_LEFT);
        hBox4.setMaxHeight(USE_PREF_SIZE);

        horizFirstButton = new Button("Sample");
        horizFirstButton.setMnemonicParsing(false);
        horizFirstButton.setPrefWidth(100.0);

        TextField textField1 = new TextField("Sample");
        textField1.setPrefWidth(80.0);
        textField1.setPromptText("Textfield");

        PasswordField passwordField1 = new PasswordField();
        passwordField1.setPrefWidth(80.0);
        passwordField1.setPromptText("Sample");
        passwordField1.setText("");

        TextArea textArea = new TextArea("Sample");
        textArea.setPrefHeight(50.0);
        textArea.setPrefWidth(80.0);
        textArea.setWrapText(true);

        TitledPane titledPane1 = new TitledPane();
        titledPane1.setAnimated(false);
        titledPane1.setPrefWidth(80.0);
        titledPane1.setText("Sample");

        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.setId("Content");
        anchorPane1.setMinHeight(0.0);
        anchorPane1.setMinWidth(0.0);
        anchorPane1.setPrefHeight(40.0);
        anchorPane1.setPrefWidth(80.0);
        titledPane1.setContent(anchorPane1);

        ToggleButton toggleButton1 = new ToggleButton("Toggle");
        toggleButton1.setMnemonicParsing(false);
        toggleButton1.setPrefWidth(90.0);
        toggleButton1.setSelected(true);

        ToggleGroup toggleGroup1 = new ToggleGroup();
        toggleButton1.setToggleGroup(toggleGroup1);

        MenuButton menuButton1 = new MenuButton("MenuButton");
        menuButton1.setMaxWidth(-1.0);
        menuButton1.setMnemonicParsing(false);
        menuButton1.setPrefWidth(90.0);

        MenuItem menuItem7 = new MenuItem("Sample MenuButton Item 1");
        menuItem7.setMnemonicParsing(false);

        MenuItem menuItem8 = new MenuItem("Sample MenuButton Item 2");
        menuItem8.setMnemonicParsing(false);

        SplitMenuButton splitMenuButton1 = new SplitMenuButton();
        splitMenuButton1.setMnemonicParsing(false);
        splitMenuButton1.setPrefWidth(90.0);
        splitMenuButton1.setText("SplitMenuButton");

        MenuItem menuItem9 = new MenuItem("Sample SplitMenuButton Item 1");
        menuItem9.setMnemonicParsing(false);

        MenuItem menuItem10 = new MenuItem("Sample SplitMenuButton Item 2");
        menuItem10.setMnemonicParsing(false);

        ChoiceBox choiceBox1 = new ChoiceBox();
        choiceBox1.setPrefWidth(60.0);

        ComboBox comboBox3 = new ComboBox();
        comboBox3.setPrefWidth(60.0);

        ComboBox comboBox4 = new ComboBox();
        comboBox4.setEditable(true);
        comboBox4.setPrefWidth(60.0);
        comboBox4.setPromptText("Choose");

        vertFirstTextField = new TextField("Sample");
        vertFirstTextField.setMaxWidth(100.0);
        vertFirstTextField.setPrefWidth(100.0);

        PasswordField passwordField2 = new PasswordField();
        passwordField2.setMaxWidth(100.0);
        passwordField2.setPrefWidth(100.0);
        passwordField2.setPromptText("Sample");
        passwordField2.setText("");

        TextArea textArea0 = new TextArea("Sample");
        textArea0.setMaxWidth(100.0);
        textArea0.setPrefHeight(50.0);
        textArea0.setPrefRowCount(10);
        textArea0.setPrefWidth(100.0);
        textArea0.setWrapText(true);

        TitledPane titledPane2 = new TitledPane();
        titledPane2.setAnimated(false);
        titledPane2.setCollapsible(false);
        titledPane2.setMaxWidth(100.0);
        titledPane2.setPrefWidth(100.0);
        titledPane2.setText("Sample");

        AnchorPane anchorPane2 = new AnchorPane();
        anchorPane2.setId("Content");
        anchorPane2.setMaxWidth(100.0);
        anchorPane2.setMinHeight(0.0);
        anchorPane2.setMinWidth(0.0);
        anchorPane2.setPrefHeight(40.0);
        anchorPane2.setPrefWidth(100.0);
        titledPane2.setContent(anchorPane2);

        TitledPane titledPane3 = new TitledPane();
        titledPane3.setAnimated(false);
        titledPane3.setMaxWidth(100.0);
        titledPane3.setPrefWidth(100.0);
        titledPane3.setText("Sample");

        AnchorPane anchorPane3 = new AnchorPane();
        anchorPane3.setId("Content");
        anchorPane3.setMaxWidth(100.0);
        anchorPane3.setMinHeight(0.0);
        anchorPane3.setMinWidth(0.0);
        anchorPane3.setPrefHeight(40.0);
        anchorPane3.setPrefWidth(100.0);
        titledPane3.setContent(anchorPane3);

        MenuButton menuButton2 = new MenuButton("Sample");
        menuButton2.setMaxWidth(100.0);
        menuButton2.setMnemonicParsing(false);
        menuButton2.setPrefWidth(100.0);

        MenuItem menuItem11 = new MenuItem("Sample MenuButton Item 1");
        menuItem11.setMnemonicParsing(false);

        MenuItem menuItem12 = new MenuItem("Sample MenuButton Item 2");
        menuItem12.setMnemonicParsing(false);

        SplitMenuButton splitMenuButton2 = new SplitMenuButton();
        splitMenuButton2.setMaxWidth(100.0);
        splitMenuButton2.setMnemonicParsing(false);
        splitMenuButton2.setPrefWidth(100.0);
        splitMenuButton2.setText("Sample");

        MenuItem menuItem13 = new MenuItem("Sample SplitMenuButton Item 1");
        menuItem13.setMnemonicParsing(false);

        MenuItem menuItem14 = new MenuItem("Sample SplitMenuButton Item 2");
        menuItem14.setMnemonicParsing(false);

        ChoiceBox choiceBox2 = new ChoiceBox();
        choiceBox2.setMaxWidth(100.0);
        choiceBox2.setPrefWidth(100.0);

        ComboBox comboBox5 = new ComboBox();
        comboBox5.setMaxWidth(100.0);
        comboBox5.setPrefWidth(100.0);

        ComboBox comboBox6 = new ComboBox();
        comboBox6.setEditable(true);
        comboBox6.setMaxWidth(100.0);
        comboBox6.setPrefWidth(100.0);
        comboBox6.setPromptText("Sample");

        ListView listView = new ListView();
        listView.setId("library-listview");
        listView.setMaxWidth(150.0);
        listView.setMinWidth(-1.0);
        listView.setPrefHeight(71.0);
        listView.setPrefWidth(150.0);

        VBox.setMargin(listView, new Insets(0.0, 0.0, 20.0, 0.0));

        horizBaseLine = new Region();
        horizBaseLine.setMaxHeight(1.0);
        horizBaseLine.setMaxWidth(-1.0);
        horizBaseLine.setOpacity(0.4);
        horizBaseLine.setStyle("-fx-background-color: red;");
        horizBaseLine.setVisible(true);
        StackPane.setMargin(horizBaseLine, new Insets(15.0, 0.0, 0.0, 0.0));

        vertBaseLine = new Region();
        vertBaseLine.setMaxHeight(-1.0);
        vertBaseLine.setMaxWidth(1.0);
        vertBaseLine.setOpacity(0.4);
        vertBaseLine.setStyle("-fx-background-color: red;");
        vertBaseLine.setVisible(true);
        StackPane.setMargin(vertBaseLine, new Insets(0.0, 0.0, 0.0, 9.0));
        hBox3.setPadding(new Insets(0.0));

        arrowButtonContainer = new AnchorPane();
        arrowButtonContainer.setLayoutX(284.0);
        arrowButtonContainer.setLayoutY(500.0);
        arrowButtonContainer.setPrefHeight(300);
        arrowButtonContainer.setPrefWidth(322.0);
        arrowButtonContainer.setStyle("-fx-border-color: #cccccc;");

        VBox vBox2 = new VBox(10.0);
        AnchorPane.setBottomAnchor(vBox2, 10.0);
        AnchorPane.setLeftAnchor(vBox2, 10.0);
        AnchorPane.setRightAnchor(vBox2, 10.0);
        AnchorPane.setTopAnchor(vBox2, 10.0);
        vBox2.setAlignment(Pos.TOP_RIGHT);
        vBox2.setMinHeight(-1.0);
        vBox2.setPrefWidth(216.0);

        HBox hBox5 = new HBox(10.0);
        hBox5.setAlignment(Pos.CENTER_RIGHT);
        hBox5.setId("HBox");

        Label label1 = new Label("MenuButton:");

        MenuButton menuButton3 = new MenuButton("Sample");
        menuButton3.setMnemonicParsing(false);
        menuButton3.setPrefWidth(150.0);

        MenuItem menuItem15 = new MenuItem("Action 1");
        menuItem15.setMnemonicParsing(false);

        MenuItem menuItem16 = new MenuItem("Action 2");
        menuItem16.setMnemonicParsing(false);

        HBox hBox6 = new HBox(10.0);
        hBox6.setAlignment(Pos.CENTER_RIGHT);
        hBox6.setId("HBox");

        Label label2 = new Label("SplitMenuButton");

        SplitMenuButton splitMenuButton3 = new SplitMenuButton();
        splitMenuButton3.setMnemonicParsing(false);
        splitMenuButton3.setPrefWidth(150.0);
        splitMenuButton3.setText("Sample");

        MenuItem menuItem17 = new MenuItem("Action 1");
        menuItem17.setMnemonicParsing(false);

        MenuItem menuItem18 = new MenuItem("Action 2");
        menuItem18.setMnemonicParsing(false);

        HBox hBox7 = new HBox(10.0);
        hBox7.setAlignment(Pos.CENTER_RIGHT);
        hBox7.setId("HBox");

        Label label3 = new Label("ChoiceBox");

        ChoiceBox choiceBox3 = new ChoiceBox();
        choiceBox3.setPrefWidth(150.0);

        HBox hBox8 = new HBox(10.0);
        hBox8.setAlignment(Pos.CENTER_RIGHT);
        hBox8.setId("HBox");

        Label label4 = new Label("ComboBox");

        ComboBox comboBox7 = new ComboBox();
        comboBox7.setPrefWidth(150.0);

        HBox hBox9 = new HBox(10.0);
        hBox9.setAlignment(Pos.CENTER_RIGHT);
        hBox9.setId("HBox");

        Label label5 = new Label("ComboBox (editable)");

        editableCombo = new ComboBox();
        editableCombo.setEditable(true);
        editableCombo.setPrefWidth(150.0);
        editableCombo.setPromptText("Sample");

        HBox hBox10 = new HBox(10.0);
        hBox10.setAlignment(Pos.CENTER_RIGHT);
        hBox10.setId("HBox");

        Label label6 = new Label("ColorPicker");

        ColorPicker colorPicker2 = new ColorPicker();
        colorPicker2.setPrefWidth(150.0);

        HBox hBox11 = new HBox(10.0);
        hBox11.setAlignment(Pos.CENTER_RIGHT);
        hBox11.setId("HBox");

        Label label7 = new Label("Split ColorPicker");

        ColorPicker colorPicker3 = new ColorPicker();
        colorPicker3.setPrefWidth(150.0);
        colorPicker3.getStyleClass().add("split-button");

        arrowButtonLeftLine = new Region();
        arrowButtonLeftLine.setLayoutX(286.5);
        arrowButtonLeftLine.setLayoutY(0.0);
        arrowButtonLeftLine.setMaxHeight(-1.0);
        arrowButtonLeftLine.setMaxWidth(1.0);
        arrowButtonLeftLine.setOpacity(0.4);
        arrowButtonLeftLine.setPrefHeight(442);
        arrowButtonLeftLine.setPrefWidth(1.0);
        arrowButtonLeftLine.setStyle("-fx-border-color: transparent transparent transparent red; -fx-border-style: dashed; ");
        arrowButtonLeftLine.setVisible(true);

        arrowButtonRightLine = new Region();
        arrowButtonRightLine.setId("arrowButtonLeftLine");
        arrowButtonRightLine.setLayoutX(311.5);
        arrowButtonRightLine.setLayoutY(0.0);
        arrowButtonRightLine.setMaxHeight(-1.0);
        arrowButtonRightLine.setMaxWidth(1.0);
        arrowButtonRightLine.setOpacity(0.4);
        arrowButtonRightLine.setPrefHeight(442);
        arrowButtonRightLine.setPrefWidth(1.0);
        arrowButtonRightLine.setStyle("-fx-border-color: transparent transparent transparent red; -fx-border-style: dashed; ");
        arrowButtonRightLine.setVisible(true);

        arrowLeftLine = new Region();
        arrowLeftLine.setId("arrowButtonLeftLine");
        arrowLeftLine.setLayoutX(294.0);
        arrowLeftLine.setLayoutY(0.0);
        arrowLeftLine.setMaxHeight(-1.0);
        arrowLeftLine.setMaxWidth(1.0);
        arrowLeftLine.setMinWidth(1.0);
        arrowLeftLine.setOpacity(0.4);
        arrowLeftLine.setPrefHeight(442);
        arrowLeftLine.setPrefWidth(1.0);
        arrowLeftLine.setStyle("-fx-border-color: transparent transparent transparent rgba(0,0,255,0.5); -fx-border-style: dashed; ");
        arrowLeftLine.setVisible(true);

        arrowRightLine = new Region();
        arrowRightLine.setId("arrowButtonLeftLine");
        arrowRightLine.setLayoutX(303.0);
        arrowRightLine.setLayoutY(0.0);
        arrowRightLine.setMaxHeight(-1.0);
        arrowRightLine.setMaxWidth(1.0);
        arrowRightLine.setMinWidth(1.0);
        arrowRightLine.setOpacity(0.4);
        arrowRightLine.setPrefHeight(443);
        arrowRightLine.setPrefWidth(1.0);
        arrowRightLine.setStyle("-fx-border-color: transparent transparent transparent rgba(0,0,255,0.5); -fx-border-style: dashed; ");
        arrowRightLine.setVisible(true);

        vBox.getChildren().add(label);
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(hBox0);
        vBox.getChildren().add(vBox0);
        hBox1.getChildren().add(titledPane);
        hBox1.getChildren().add(button);
        hBox1.getChildren().add(toggleButton);
        menuButton.getItems().add(menuItem);
        menuButton.getItems().add(menuItem0);
        hBox1.getChildren().add(menuButton);
        splitMenuButton.getItems().add(menuItem1);
        splitMenuButton.getItems().add(menuItem2);
        hBox1.getChildren().add(splitMenuButton);
        choiceBox.getItems().addAll(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
        hBox1.getChildren().add(choiceBox);
        comboBox.getItems().addAll(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
        hBox1.getChildren().add(comboBox);
        comboBox0.getItems().addAll(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
        hBox1.getChildren().add(comboBox0);
        hBox1.getChildren().add(new ColorPicker());
        hBox1.getChildren().add(textField);
        hBox1.getChildren().add(passwordField);
        vBox.getChildren().add(hBox1);
        hBox2.getChildren().add(titledPane0);
        hBox2.getChildren().add(button0);
        hBox2.getChildren().add(toggleButton0);
        menuButton0.getItems().add(menuItem3);
        menuButton0.getItems().add(menuItem4);
        hBox2.getChildren().add(menuButton0);
        splitMenuButton0.getItems().add(menuItem5);
        splitMenuButton0.getItems().add(menuItem6);
        hBox2.getChildren().add(splitMenuButton0);
        choiceBox0.getItems().addAll(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
        hBox2.getChildren().add(choiceBox0);
        comboBox1.getItems().addAll(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
        hBox2.getChildren().add(comboBox1);
        comboBox2.getItems().addAll(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
        hBox2.getChildren().add(comboBox2);
        hBox2.getChildren().add(new ColorPicker());
        hBox2.getChildren().add(textField0);
        hBox2.getChildren().add(passwordField0);
        vBox.getChildren().add(hBox2);
        vBox.getChildren().add(label0);
        hBox4.getChildren().add(horizFirstButton);
        hBox4.getChildren().add(textField1);
        hBox4.getChildren().add(passwordField1);
        hBox4.getChildren().add(textArea);
        hBox4.getChildren().add(titledPane1);
        hBox4.getChildren().add(toggleButton1);
        menuButton1.getItems().add(menuItem7);
        menuButton1.getItems().add(menuItem8);
        hBox4.getChildren().add(menuButton1);
        splitMenuButton1.getItems().add(menuItem9);
        splitMenuButton1.getItems().add(menuItem10);
        hBox4.getChildren().add(splitMenuButton1);
        choiceBox1.getItems().addAll(FXCollections.observableArrayList("Sample ChoiceBox Item 1", "Sample ChoiceBox Item 2"));
        hBox4.getChildren().add(choiceBox1);
        comboBox3.getItems().addAll(FXCollections.observableArrayList("Sample ComboBox Item 1", "Sample ComboBox Item 2"));
        hBox4.getChildren().add(comboBox3);
        comboBox4.getItems().addAll(FXCollections.observableArrayList("Sample ComboBox Item 1", "Sample ComboBox Item 2"));
        hBox4.getChildren().add(comboBox4);
        hBox4.getChildren().add(new ColorPicker());
        vBox1.getChildren().add(hBox4);
        vBox1.getChildren().add(vertFirstTextField);
        vBox1.getChildren().add(passwordField2);
        vBox1.getChildren().add(textArea0);
        vBox1.getChildren().add(titledPane2);
        vBox1.getChildren().add(titledPane3);
        menuButton2.getItems().add(menuItem11);
        menuButton2.getItems().add(menuItem12);
        vBox1.getChildren().add(menuButton2);
        splitMenuButton2.getItems().add(menuItem13);
        splitMenuButton2.getItems().add(menuItem14);
        vBox1.getChildren().add(splitMenuButton2);
        choiceBox2.getItems().addAll(FXCollections.observableArrayList("Sample ChoiceBox Item 1", "Sample ChoiceBox Item 2"));
        vBox1.getChildren().add(choiceBox2);
        comboBox5.getItems().addAll(FXCollections.observableArrayList("Sample ComboBox Item 1", "Sample ComboBox Item 2"));
        vBox1.getChildren().add(comboBox5);
        comboBox6.getItems().addAll(FXCollections.observableArrayList("Sample ComboBox Item 1", "Sample ComboBox Item 2"));
        vBox1.getChildren().add(comboBox6);
        listView.getItems().addAll(FXCollections.observableArrayList("Sample 1", "Sample 2", "Sample 3", "Sample 4"));
        vBox1.getChildren().add(listView);
        stackPane.getChildren().add(vBox1);
        stackPane.getChildren().add(horizBaseLine);
        stackPane.getChildren().add(vertBaseLine);
        hBox3.getChildren().add(stackPane);
        vBox.getChildren().add(hBox3);
        getChildren().add(vBox);
        hBox5.getChildren().add(label1);
        menuButton3.getItems().add(menuItem15);
        menuButton3.getItems().add(menuItem16);
        hBox5.getChildren().add(menuButton3);
        vBox2.getChildren().add(hBox5);
        hBox6.getChildren().add(label2);
        splitMenuButton3.getItems().add(menuItem17);
        splitMenuButton3.getItems().add(menuItem18);
        hBox6.getChildren().add(splitMenuButton3);
        vBox2.getChildren().add(hBox6);
        hBox7.getChildren().add(label3);
        choiceBox3.getItems().addAll(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
        hBox7.getChildren().add(choiceBox3);
        vBox2.getChildren().add(hBox7);
        hBox8.getChildren().add(label4);
        comboBox7.getItems().addAll(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
        hBox8.getChildren().add(comboBox7);
        vBox2.getChildren().add(hBox8);
        hBox9.getChildren().add(label5);
        editableCombo.getItems().addAll(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
        hBox9.getChildren().add(editableCombo);
        vBox2.getChildren().add(hBox9);
        hBox10.getChildren().add(label6);
        hBox10.getChildren().add(colorPicker2);
        vBox2.getChildren().add(hBox10);
        hBox11.getChildren().add(label7);
        hBox11.getChildren().add(colorPicker3);
        vBox2.getChildren().add(hBox11);
        arrowButtonContainer.getChildren().add(vBox2);
        arrowButtonContainer.getChildren().add(arrowButtonLeftLine);
        arrowButtonContainer.getChildren().add(arrowButtonRightLine);
        arrowButtonContainer.getChildren().add(arrowLeftLine);
        arrowButtonContainer.getChildren().add(arrowRightLine);
        getChildren().add(arrowButtonContainer);

        initialize();
    }

    private void initialize() {
        Platform.runLater(() -> {
            Text buttonTextNode = (Text) horizFirstButton.lookup(".text");
            buttonTextNode.layoutYProperty().addListener((ov, t, t1) -> StackPane.setMargin(horizBaseLine, new Insets(t1.doubleValue(), 0, 0, 0)));
            Text textFieldTextNode = (Text) vertFirstTextField.lookup(".text");
            textFieldTextNode.layoutXProperty().addListener((ov, t, t1) -> StackPane.setMargin(vertBaseLine, new Insets(0, 0, 0, t1.doubleValue())));
            arrowButton = editableCombo.lookup(".arrow-button");
            arrow = editableCombo.lookup(".arrow");
            ChangeListener updater = (ov, t, t1) -> updateArrowLinePositions();
            arrow.layoutBoundsProperty().addListener(updater);
            arrowButton.layoutBoundsProperty().addListener(updater);
            editableCombo.layoutBoundsProperty().addListener(updater);
            arrowButtonContainer.layoutBoundsProperty().addListener(updater);
            updateArrowLinePositions();
        });
    }

    private void updateArrowLinePositions() {
        double left = arrowButton.localToScene(0, 0).getX() - arrowButtonContainer.localToScene(0, 0).getX();
        arrowButtonLeftLine.setLayoutX(left - 1);
        arrowButtonLeftLine.setPrefHeight(arrowButtonContainer.getLayoutBounds().getHeight());
        arrowButtonRightLine.setLayoutX(left + arrowButton.getLayoutBounds().getWidth());
        arrowButtonRightLine.setPrefHeight(arrowButtonContainer.getLayoutBounds().getHeight());
        double arrowLeft = arrow.localToScene(0, 0).getX() - arrowButtonContainer.localToScene(0, 0).getX();
        arrowLeftLine.setLayoutX(arrowLeft - 1);
        arrowLeftLine.setPrefHeight(arrowButtonContainer.getLayoutBounds().getHeight());
        arrowRightLine.setLayoutX(arrowLeft + arrow.getLayoutBounds().getWidth());
        arrowRightLine.setPrefHeight(arrowButtonContainer.getLayoutBounds().getHeight());
    }
}
