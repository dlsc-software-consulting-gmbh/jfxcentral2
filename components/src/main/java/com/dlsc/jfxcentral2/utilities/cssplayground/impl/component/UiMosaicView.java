package com.dlsc.jfxcentral2.utilities.cssplayground.impl.component;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.geometry.VPos;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
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
public class UiMosaicView extends AnchorPane {

    public UiMosaicView() {
        setId("AnchorPane");
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(935.0);
        setPrefWidth(927.0);

        Button button = new Button("Button");
        button.setLayoutX(40.0);
        button.setLayoutY(41.0);
        button.setMnemonicParsing(false);

        Hyperlink hyperlink = new Hyperlink("Hyperlink");
        hyperlink.setLayoutX(134.0);
        hyperlink.setLayoutY(40.0);

        ComboBox comboBox = new ComboBox();
        comboBox.setLayoutX(234.0);
        comboBox.setLayoutY(41.0);
        comboBox.setPrefWidth(160.0);

        SplitMenuButton splitMenuButton = new SplitMenuButton();
        splitMenuButton.setLayoutX(40.0);
        splitMenuButton.setLayoutY(83.0);
        splitMenuButton.setMnemonicParsing(false);
        splitMenuButton.setPrefWidth(153.0);
        splitMenuButton.setText("SplitMenuButton");

        MenuItem menuItem = new MenuItem("Action 1");
        menuItem.setMnemonicParsing(false);

        MenuItem menuItem0 = new MenuItem("Action 2");
        menuItem0.setMnemonicParsing(false);

        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.setLayoutX(234.0);
        choiceBox.setLayoutY(85.0);
        choiceBox.setPrefWidth(160.0);

        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setLayoutX(435.0);
        progressIndicator.setLayoutY(45.0);
        progressIndicator.setPrefHeight(59.0);
        progressIndicator.setPrefWidth(56.0);
        progressIndicator.setProgress(0.58);

        TextField textField = new TextField("Textfield");
        textField.setLayoutX(40.0);
        textField.setLayoutY(149.0);
        textField.setPrefWidth(160.0);
        textField.setPromptText("Textfield");

        TextArea textArea = new TextArea("Text Area with some text spanning over a few lines.");
        textArea.setLayoutX(40.0);
        textArea.setLayoutY(180.0);
        textArea.setPrefHeight(130.0);
        textArea.setPrefWidth(160.0);
        textArea.setPromptText("Text Area");
        textArea.setWrapText(true);

        Label label = new Label("A label");
        label.setLayoutX(242.0);
        label.setLayoutY(153.0);

        Separator separator = new Separator();
        separator.setLayoutX(41.0);
        separator.setLayoutY(129.0);
        separator.setPrefWidth(490.0);

        Separator separator0 = new Separator();
        separator0.setLayoutX(217.0);
        separator0.setLayoutY(151.0);
        separator0.setOrientation(Orientation.VERTICAL);
        separator0.setPrefHeight(158.0);

        Slider slider = new Slider();
        slider.setLayoutX(304.0);
        slider.setLayoutY(154.0);
        slider.setPrefWidth(230.0);

        RadioButton radioButton = new RadioButton("r1");
        radioButton.setLayoutX(240.0);
        radioButton.setLayoutY(199.0);
        radioButton.setMnemonicParsing(false);

        ToggleGroup toggle1 = new ToggleGroup();
        radioButton.setToggleGroup(toggle1);

        RadioButton radioButton0 = new RadioButton("r2");
        radioButton0.setLayoutX(290.0);
        radioButton0.setLayoutY(199.0);
        radioButton0.setMnemonicParsing(false);
        radioButton0.setToggleGroup(toggle1);

        ToggleButton toggleButton = new ToggleButton("Toggle");
        toggleButton.setLayoutX(341.0);
        toggleButton.setLayoutY(196.0);
        toggleButton.setMnemonicParsing(false);
        toggleButton.setPrefWidth(90.0);
        toggleButton.setSelected(true);
        toggleButton.setToggleGroup(toggle1);

        ToggleButton toggleButton0 = new ToggleButton("Toggle");
        toggleButton0.setLayoutX(441.0);
        toggleButton0.setLayoutY(196.0);
        toggleButton0.setMnemonicParsing(false);
        toggleButton0.setPrefWidth(90.0);
        toggleButton0.setSelected(false);
        toggleButton0.setToggleGroup(toggle1);

        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(240.0);
        passwordField.setLayoutY(243.0);
        passwordField.setPrefWidth(138.0);
        passwordField.setPromptText("Password");

        ComboBox comboBox0 = new ComboBox();
        comboBox0.setEditable(true);
        comboBox0.setLayoutX(395.0);
        comboBox0.setLayoutY(243.0);
        comboBox0.setPrefWidth(136.0001220703125);
        comboBox0.setPromptText("Choose");

        CheckBox checkBox = new CheckBox("Check");
        checkBox.setLayoutX(471.0);
        checkBox.setLayoutY(294.0);
        checkBox.setMnemonicParsing(false);
        checkBox.setSelected(true);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setLayoutX(240.0);
        progressBar.setLayoutY(291.0);
        progressBar.setPrefWidth(200.0);
        progressBar.setProgress(0.3);

        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setLayoutX(41.0);
        scrollBar.setLayoutY(329.0);
        scrollBar.setPrefWidth(493.0);

        TitledPane titledPane = new TitledPane();
        titledPane.setAnimated(false);
        titledPane.setLayoutX(656.0);
        titledPane.setLayoutY(287.0);
        titledPane.setPrefHeight(59.0);
        titledPane.setPrefWidth(216.0);
        titledPane.setText("Hello World");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setId("Content");
        anchorPane.setMinHeight(0.0);
        anchorPane.setMinWidth(0.0);
        anchorPane.setPrefHeight(180.0);
        anchorPane.setPrefWidth(200.0);
        titledPane.setContent(anchorPane);

        Accordion accordion = new Accordion();
        accordion.setLayoutX(39.0);
        accordion.setLayoutY(358.0);
        accordion.setMinWidth(345.0);
        accordion.setPrefHeight(554.0);
        accordion.setPrefWidth(857.0);

        TitledPane titledPane0 = new TitledPane();
        titledPane0.setAnimated(true);
        titledPane0.setExpanded(false);
        titledPane0.setId("x2");
        titledPane0.setText("Split Pane Padded");

        StackPane stackPane = new StackPane();
        stackPane.setPrefHeight(150.0);
        stackPane.setPrefWidth(200.0);

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.5185185185185185);
        splitPane.setFocusTraversable(true);
        splitPane.setMaxHeight(USE_PREF_SIZE);
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.setPrefHeight(191.0);
        splitPane.setPrefWidth(447.0);

        SplitPane splitPane0 = new SplitPane();
        splitPane0.setDividerPositions(0.4969097651421508);
        splitPane0.setFocusTraversable(true);
        splitPane0.setPrefHeight(172.0);
        splitPane0.setPrefWidth(490.0);

        TreeView treeView = new TreeView();
        treeView.setPrefHeight(130.0);
        treeView.setPrefWidth(179.0);
        treeView.setShowRoot(false);

        TreeItem treeItem = new TreeItem("Family");

        TreeItem treeItem0 = new TreeItem("Grandparent A");

        TreeItem treeItem1 = new TreeItem("Parent A");

        TreeItem treeItem2 = new TreeItem("Child A");

        TreeItem treeItem3 = new TreeItem("Grandparent B");

        TreeItem treeItem4 = new TreeItem("Parent B");

        TreeItem treeItem5 = new TreeItem("Child B");

        TreeItem treeItem6 = new TreeItem("Grandparent C");

        TreeItem treeItem7 = new TreeItem("Parent C");

        TreeItem treeItem8 = new TreeItem("Child C");

        TableView tableView = new TableView();
        tableView.setPrefHeight(130.0);
        tableView.setPrefWidth(241.0);

        TableColumn tableColumn = new TableColumn("Column X");
        tableColumn.setPrefWidth(75.0);

        TableColumn tableColumn0 = new TableColumn("Column X");
        tableColumn0.setPrefWidth(75.0);

        ListView listView = new ListView();
        listView.setPrefHeight(130.0);
        listView.setPrefWidth(200.0);
        stackPane.setPadding(new Insets(20.0));
        titledPane0.setContent(stackPane);

        TitledPane x2 = new TitledPane();
        x2.setAnimated(true);
        x2.setExpanded(false);
        x2.setText("Form");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10.0);
        gridPane.setId("GridPane");
        gridPane.setPrefHeight(139.0);
        gridPane.setPrefWidth(288.0);
        gridPane.setVgap(10.0);

        Button submintButton = new Button("Submit");
        submintButton.setMnemonicParsing(false);
        GridPane.setColumnIndex(submintButton, 1);
        GridPane.setRowIndex(submintButton, 2);

        TextField textField0 = new TextField("");
        textField0.setPrefWidth(160.0);
        textField0.setPromptText("Name");
        GridPane.setColumnIndex(textField0, 1);
        GridPane.setRowIndex(textField0, 0);

        TextArea textArea0 = new TextArea("");
        textArea0.setPrefHeight(75.0);
        textArea0.setPrefWidth(160.0);
        textArea0.setPromptText("Address");
        textArea0.setWrapText(true);
        GridPane.setColumnIndex(textArea0, 1);
        GridPane.setRowIndex(textArea0, 1);

        Label label0 = new Label("Name");
        GridPane.setColumnIndex(label0, 0);
        GridPane.setRowIndex(label0, 0);

        AnchorPane anchorPane0 = new AnchorPane();
        GridPane.setColumnIndex(anchorPane0, 0);
        GridPane.setRowIndex(anchorPane0, 1);
        GridPane.setValignment(anchorPane0, VPos.TOP);
        anchorPane0.setId("AnchorPane");
        anchorPane0.setMinHeight(USE_PREF_SIZE);
        anchorPane0.setMinWidth(USE_PREF_SIZE);
        anchorPane0.setPrefHeight(-1.0);
        anchorPane0.setPrefWidth(-1.0);

        Label label1 = new Label("Address");
        label1.setLayoutX(55.0);
        label1.setLayoutY(5.0);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.RIGHT);
        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMaxWidth(111.0);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(65.0);

        ColumnConstraints columnConstraints0 = new ColumnConstraints();
        columnConstraints0.setHalignment(HPos.RIGHT);
        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMaxWidth(216.0);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(213.0);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(10.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        RowConstraints rowConstraints0 = new RowConstraints();
        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        RowConstraints rowConstraints2 = new RowConstraints();
        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
        x2.setContent(gridPane);

        TitledPane titledPane1 = new TitledPane();
        titledPane1.setAnimated(true);
        titledPane1.setExpanded(false);
        titledPane1.setText("Chart");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(200.0);
        scrollPane.setPrefWidth(200.0);

        NumberAxis numberAxis = new NumberAxis();
        numberAxis.setSide(Side.BOTTOM);

        NumberAxis numberAxis0 = new NumberAxis();
        numberAxis0.setSide(Side.LEFT);

        BubbleChart bubbleChart = new BubbleChart(numberAxis, numberAxis0);
        bubbleChart.setId("BubbleChart");
        bubbleChart.setTitle("My Bubble Chart");
        bubbleChart.setTitleSide(Side.TOP);
        scrollPane.setContent(bubbleChart);
        titledPane1.setContent(scrollPane);

        TitledPane titledPane2 = new TitledPane();
        titledPane2.setAnimated(false);
        titledPane2.setExpanded(false);
        titledPane2.setText("Table");

        TableView tableView0 = new TableView();
        tableView0.setPrefHeight(200.0);
        tableView0.setPrefWidth(200.0);

        TableColumn tableColumn1 = new TableColumn("Column X");
        tableColumn1.setPrefWidth(75.0);

        TableColumn tableColumn2 = new TableColumn("Column X");
        tableColumn2.setPrefWidth(75.0);
        titledPane2.setContent(tableView0);

        TitledPane x1 = new TitledPane();
        x1.setAnimated(true);
        x1.setExpanded(false);
        x1.setText("Tree");

        TreeView treeView0 = new TreeView();
        treeView0.setPrefHeight(200.0);
        treeView0.setPrefWidth(200.0);
        x1.setContent(treeView0);

        TitledPane titledPane3 = new TitledPane();
        titledPane3.setAnimated(true);
        titledPane3.setExpanded(false);
        titledPane3.setId("x1");
        titledPane3.setText("List");

        ListView listView0 = new ListView();
        listView0.setPrefHeight(200.0);
        listView0.setPrefWidth(200.0);
        titledPane3.setContent(listView0);

        TitledPane titledPane4 = new TitledPane();
        titledPane4.setAnimated(true);
        titledPane4.setExpanded(false);
        titledPane4.setId("x1");
        titledPane4.setText("Text Area");

        TextArea textArea1 = new TextArea();
        textArea1.setPrefWidth(200.0);
        textArea1.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse semper varius lobortis. Donec porttitor, diam ut adipiscing sollicitudin, massa mi cursus orci, sed faucibus libero lectus id augue. Cras tristique odio tincidunt massa vehicula sed pellentesque orci pretium. Donec placerat ullamcorper magna cursus volutpat. Nulla facilisi. In faucibus ullamcorper tincidunt. Nam ante ante, consequat vitae egestas non, tincidunt eu mauris. Vivamus aliquam nibh justo, tincidunt vehicula metus. Suspendisse ut dui quis ligula aliquet venenatis. Nullam lacinia lectus non ipsum sagittis at eleifend mauris pretium. Vestibulum nisi metus, rhoncus vitae condimentum a, condimentum fermentum urna. Quisque ut nisi massa. Sed auctor euismod urna eu tincidunt. Mauris facilisis tempor molestie. In in quam in mauris placerat malesuada id a magna.\n" +
                "\n" +
                "Donec ligula velit, ornare nec semper a, aliquet at nibh. Morbi pulvinar sollicitudin ultricies. Donec quis eros eu turpis facilisis placerat. Aenean non neque libero. Vestibulum orci magna, auctor et tempus sit amet, dictum et libero. Praesent dapibus justo eget elit ultrices bibendum. In eget nisl augue, id imperdiet magna. Suspendisse at augue vitae dolor consectetur mollis at et orci. Nam sit amet mi in diam pulvinar ornare. Fusce vitae neque eget urna interdum rhoncus rhoncus id lectus. Vestibulum eget leo non nunc porttitor bibendum. Nullam justo nisi, mattis id adipiscing quis, egestas a lorem. Donec mi ligula, dictum id mattis sed, vehicula vitae metus.\n" +
                "\n" +
                "Etiam id felis in velit blandit dignissim vel non libero. Aenean tristique euismod libero, at faucibus purus aliquam ut. Duis placerat lectus sit amet odio ultrices vestibulum. Aliquam erat volutpat. Praesent odio lorem, commodo eget tristique et, placerat at lorem. Curabitur blandit condimentum magna, at pretium nulla interdum a. Sed magna nulla, malesuada quis dignissim sit amet, eleifend at nisl. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Donec vehicula vehicula molestie. Phasellus ultricies volutpat cursus. Praesent in leo sit amet arcu accumsan tincidunt. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum dignissim hendrerit erat, nec bibendum metus sodales eget. Integer congue felis sit amet dolor sollicitudin nec dapibus nisi tempus. Cras malesuada, mi vitae gravida iaculis, mi dui pharetra risus, ac condimentum urna ipsum blandit elit.\n" +
                "\n" +
                "Aenean pharetra aliquam velit in porta. Phasellus leo erat, iaculis et pharetra non, dignissim eu velit. Sed rutrum tortor vel purus adipiscing vitae ultrices erat sollicitudin. Vestibulum tempus consectetur est id porttitor. Proin hendrerit dictum dapibus. Sed viverra, erat at condimentum molestie, orci purus blandit eros, nec scelerisque nulla justo vitae neque. Sed tempor massa venenatis tortor condimentum viverra. Duis a egestas mauris. Curabitur egestas tincidunt sodales. Sed ornare, nulla at adipiscing ullamcorper, quam orci facilisis erat, non hendrerit nisl enim et lacus.\n" +
                "\n" +
                "Phasellus ac lacus gravida mauris malesuada accumsan id eleifend quam. Pellentesque quis mi urna. Mauris a pulvinar enim. Duis molestie lacinia vehicula. Vivamus semper consequat mauris a placerat. Suspendisse felis massa, suscipit vel aliquet at, pellentesque eget tellus. Sed aliquam tortor felis.");
        textArea1.setWrapText(true);
        titledPane4.setContent(textArea1);

        TitledPane titledPane5 = new TitledPane();
        titledPane5.setAnimated(true);
        titledPane5.setExpanded(false);
        titledPane5.setId("x1");
        titledPane5.setText("Empty");

        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.setId("Content");
        anchorPane1.setMinHeight(0.0);
        anchorPane1.setMinWidth(0.0);
        anchorPane1.setPrefHeight(180.0);
        anchorPane1.setPrefWidth(200.0);
        anchorPane1.setPadding(new Insets(10.0, 0.0, 0.0, 0.0));
        titledPane5.setContent(anchorPane1);

        TitledPane titledPane6 = new TitledPane();
        titledPane6.setAnimated(true);
        titledPane6.setExpanded(false);
        titledPane6.setId("x2");
        titledPane6.setText("Split Pane No Padding");

        SplitPane splitPane1 = new SplitPane();
        splitPane1.setDividerPositions(0.5065359477124183);
        splitPane1.setFocusTraversable(true);
        splitPane1.setMaxHeight(Double.MAX_VALUE);
        splitPane1.setOrientation(Orientation.VERTICAL);
        splitPane1.setPrefHeight(-1.0);
        splitPane1.setPrefWidth(-1.0);

        SplitPane splitPane2 = new SplitPane();
        splitPane2.setDividerPositions(0.49705535924617195);
        splitPane2.setFocusTraversable(true);
        splitPane2.setPrefHeight(172.0);
        splitPane2.setPrefWidth(490.0);

        TreeView treeView1 = new TreeView();
        treeView1.setPrefHeight(130.0);
        treeView1.setPrefWidth(179.0);
        treeView1.setShowRoot(false);

        TreeItem treeItem9 = new TreeItem("Family");

        TreeItem treeItem10 = new TreeItem("Grandparent A");

        TreeItem treeItem11 = new TreeItem("Parent A");

        TreeItem treeItem12 = new TreeItem("Child A");

        TreeItem treeItem13 = new TreeItem("Grandparent B");

        TreeItem treeItem14 = new TreeItem("Parent B");

        TreeItem treeItem15 = new TreeItem("Child B");

        TreeItem treeItem16 = new TreeItem("Grandparent C");

        TreeItem treeItem17 = new TreeItem("Parent C");

        TreeItem treeItem18 = new TreeItem("Child C");

        TableView tableView1 = new TableView();
        tableView1.setPrefHeight(130.0);
        tableView1.setPrefWidth(241.0);

        TableColumn tableColumn3 = new TableColumn("Column X");
        tableColumn3.setPrefWidth(75.0);

        TableColumn tableColumn4 = new TableColumn("Column X");
        tableColumn4.setPrefWidth(75.0);

        ListView listView1 = new ListView();
        listView1.setPrefHeight(130.0);
        listView1.setPrefWidth(200.0);
        titledPane6.setContent(splitPane1);

        Slider slider0 = new Slider();
        slider0.setLayoutX(541.0);
        slider0.setLayoutY(34.0);
        slider0.setOrientation(Orientation.VERTICAL);
        slider0.setPrefHeight(240.0);
        slider0.setShowTickMarks(true);

        ProgressIndicator progressIndicator0 = new ProgressIndicator();
        progressIndicator0.setLayoutX(582.0);
        progressIndicator0.setLayoutY(287.0);
        progressIndicator0.setPrefHeight(52.0);
        progressIndicator0.setPrefWidth(46.0);
        progressIndicator0.setProgress(-1.0);

        VBox vBox = new VBox();
        vBox.setLayoutX(582.0);
        vBox.setLayoutY(39.0);
        vBox.setPrefHeight(235.0);
        vBox.setPrefWidth(315.0);
        vBox.setStyle("-fx-border-color: black; -fx-border-width: 3; -fx-border-insets: -3;");

        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(200.0);

        Menu fileMenu = new Menu("File");
        fileMenu.setMnemonicParsing(false);

        MenuItem closeMenuItem = new MenuItem("Close");
        closeMenuItem.setMnemonicParsing(false);

        Menu menu0 = new Menu("Edit");
        menu0.setMnemonicParsing(false);

        MenuItem menuItem2 = new MenuItem("Delete");
        menuItem2.setMnemonicParsing(false);

        Menu menu1 = new Menu("ABCqypgj");
        menu1.setMnemonicParsing(false);

        MenuItem menuItem3 = new MenuItem("ABCqypgj");
        menuItem3.setMnemonicParsing(false);

        Menu menu2 = new Menu("Help");
        menu2.setMnemonicParsing(false);

        MenuItem menuItem4 = new MenuItem("About");
        menuItem4.setMnemonicParsing(false);

        ToolBar toolBar = new ToolBar();
        toolBar.setPrefWidth(200.0);

        Button button1 = new Button("New");
        button1.setMnemonicParsing(false);

        Button button2 = new Button("Delete");
        button2.setMnemonicParsing(false);

        Button button3 = new Button("Save");
        button3.setMnemonicParsing(false);

        Button button4 = new Button("Exit");
        button4.setMnemonicParsing(false);

        TabPane tabPane = new TabPane();
        VBox.setVgrow(tabPane, javafx.scene.layout.Priority.ALWAYS);
        tabPane.setPrefHeight(130.0);
        tabPane.setPrefWidth(200.0);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab = new Tab("Tab 1");

        VBox vBox0 = new VBox(6.0);
        vBox0.setPrefHeight(200.0);
        vBox0.setPrefWidth(100.0);

        RadioButton radioButton1 = new RadioButton("RadioButton 1");
        radioButton1.setMnemonicParsing(false);

        ToggleGroup toggle2 = new ToggleGroup();
        radioButton1.setToggleGroup(toggle2);

        RadioButton radioButton2 = new RadioButton("RadioButton 2");
        radioButton2.setMnemonicParsing(false);
        radioButton2.setToggleGroup(toggle2);

        CheckBox checkBox0 = new CheckBox("CheckBox");
        checkBox0.setMnemonicParsing(false);
        checkBox0.setSelected(true);

        CheckBox checkBox1 = new CheckBox("CheckBox");
        checkBox1.setMnemonicParsing(false);
        vBox0.setPadding(new Insets(10.0));
        tab.setContent(vBox0);

        Tab tab0 = new Tab("Tab 2");

        AnchorPane anchorPane2 = new AnchorPane();
        anchorPane2.setId("Content");
        anchorPane2.setMinHeight(0.0);
        anchorPane2.setMinWidth(0.0);
        anchorPane2.setPrefHeight(180.0);
        anchorPane2.setPrefWidth(200.0);
        tab0.setContent(anchorPane2);

        Tab tab1 = new Tab("Tab 3");

        AnchorPane anchorPane3 = new AnchorPane();
        anchorPane3.setId("Content");
        anchorPane3.setMinHeight(0.0);
        anchorPane3.setMinWidth(0.0);
        anchorPane3.setPrefHeight(180.0);
        anchorPane3.setPrefWidth(200.0);
        tab1.setContent(anchorPane3);

        getChildren().add(button);
        getChildren().add(hyperlink);
        comboBox.getItems().addAll(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
        getChildren().add(comboBox);
        splitMenuButton.getItems().add(menuItem);
        splitMenuButton.getItems().add(menuItem0);
        getChildren().add(splitMenuButton);
        choiceBox.getItems().addAll(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
        getChildren().add(choiceBox);
        getChildren().add(progressIndicator);
        getChildren().add(textField);
        getChildren().add(textArea);
        getChildren().add(label);
        getChildren().add(separator);
        getChildren().add(separator0);
        getChildren().add(slider);
        getChildren().add(radioButton);
        getChildren().add(radioButton0);
        getChildren().add(toggleButton);
        getChildren().add(toggleButton0);
        getChildren().add(passwordField);
        comboBox0.getItems().addAll(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));
        getChildren().add(comboBox0);
        getChildren().add(checkBox);
        getChildren().add(progressBar);
        getChildren().add(scrollBar);
        getChildren().add(titledPane);
        treeItem1.getChildren().add(treeItem2);
        treeItem0.getChildren().add(treeItem1);
        treeItem.getChildren().add(treeItem0);
        treeItem4.getChildren().add(treeItem5);
        treeItem3.getChildren().add(treeItem4);
        treeItem.getChildren().add(treeItem3);
        treeItem7.getChildren().add(treeItem8);
        treeItem6.getChildren().add(treeItem7);
        treeItem.getChildren().add(treeItem6);
        treeView.setRoot(treeItem);
        splitPane0.getItems().add(treeView);
        tableView.getColumns().add(tableColumn);
        tableView.getColumns().add(tableColumn0);
        splitPane0.getItems().add(tableView);
        splitPane.getItems().add(splitPane0);
        splitPane.getItems().add(listView);
        stackPane.getChildren().add(splitPane);
        accordion.getPanes().add(titledPane0);
        gridPane.getChildren().add(submintButton);
        gridPane.getChildren().add(textField0);
        gridPane.getChildren().add(textArea0);
        gridPane.getChildren().add(label0);
        anchorPane0.getChildren().add(label1);
        gridPane.getChildren().add(anchorPane0);
        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        gridPane.getRowConstraints().add(rowConstraints2);
        accordion.getPanes().add(x2);
        accordion.getPanes().add(titledPane1);
        tableView0.getColumns().add(tableColumn1);
        tableView0.getColumns().add(tableColumn2);
        accordion.getPanes().add(titledPane2);
        accordion.getPanes().add(x1);
        accordion.getPanes().add(titledPane3);
        accordion.getPanes().add(titledPane4);
        accordion.getPanes().add(titledPane5);
        treeItem11.getChildren().add(treeItem12);
        treeItem10.getChildren().add(treeItem11);
        treeItem9.getChildren().add(treeItem10);
        treeItem14.getChildren().add(treeItem15);
        treeItem13.getChildren().add(treeItem14);
        treeItem9.getChildren().add(treeItem13);
        treeItem17.getChildren().add(treeItem18);
        treeItem16.getChildren().add(treeItem17);
        treeItem9.getChildren().add(treeItem16);
        treeView1.setRoot(treeItem9);
        splitPane2.getItems().add(treeView1);
        tableView1.getColumns().add(tableColumn3);
        tableView1.getColumns().add(tableColumn4);
        splitPane2.getItems().add(tableView1);
        splitPane1.getItems().add(splitPane2);
        splitPane1.getItems().add(listView1);
        accordion.getPanes().add(titledPane6);

        TitledPane titledPane7 = new TitledPane();
        titledPane7.setText("VBox");
        titledPane7.setAnimated(true);
        titledPane7.setExpanded(true);
        VBox vBox1 = new VBox();
        vBox1.setPrefHeight(200.0);
        vBox1.setPrefWidth(100.0);
        Button button5 = new Button("Button");
        button5.setMnemonicParsing(false);

        Button button6 = new Button("Button");
        button6.setMnemonicParsing(false);

        vBox1.getChildren().addAll(button5, button6);
        titledPane7.setContent(vBox1);

        accordion.getPanes().add(titledPane7);
        accordion.setExpandedPane(titledPane7);

        getChildren().add(accordion);
        getChildren().add(slider0);
        getChildren().add(progressIndicator0);
        fileMenu.getItems().add(closeMenuItem);
        menuBar.getMenus().add(fileMenu);
        menu0.getItems().add(menuItem2);
        menuBar.getMenus().add(menu0);
        menu1.getItems().add(menuItem3);
        menuBar.getMenus().add(menu1);
        menu2.getItems().add(menuItem4);
        menuBar.getMenus().add(menu2);
        vBox.getChildren().add(menuBar);
        toolBar.getItems().add(button1);
        toolBar.getItems().add(button2);
        toolBar.getItems().add(button3);
        toolBar.getItems().add(button4);
        vBox.getChildren().add(toolBar);
        vBox0.getChildren().add(radioButton1);
        vBox0.getChildren().add(radioButton2);
        vBox0.getChildren().add(checkBox0);
        vBox0.getChildren().add(checkBox1);
        tabPane.getTabs().add(tab);
        tabPane.getTabs().add(tab0);
        tabPane.getTabs().add(tab1);
        vBox.getChildren().add(tabPane);
        getChildren().add(vBox);

    }
}
