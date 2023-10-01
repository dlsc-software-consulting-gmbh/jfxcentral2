package com.dlsc.jfxcentral2.utilities.cssplayground.impl.component;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
public class CombinationTestView extends VBox {

    public CombinationTestView() {
        super(10.0);
        setId("VBox");
        setMaxHeight(Double.MAX_VALUE);
        setMaxWidth(Double.MAX_VALUE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);

        Label label = new Label("X inside Y Combinations");
        label.setPrefWidth(632.0);
        label.setFont(new Font("System Bold", 13.0));

        TilePane tilePane = new TilePane();
        tilePane.setHgap(10.0);
        tilePane.setVgap(10.0);
        tilePane.setPrefColumns(3);
        tilePane.setPrefTileHeight(300.0);
        tilePane.setPrefTileWidth(300.0);

        TabPane tabPane = new TabPane();
        tabPane.setPrefHeight(200.0);
        tabPane.setPrefWidth(200.0);
        tabPane.getStyleClass().add("floating");
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab = new Tab("ScrollPane");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(200.0);
        scrollPane.setPrefWidth(200.0);

        Pane pane = new Pane();
        pane.setPrefHeight(1000.0);
        pane.setPrefWidth(1000.0);
        scrollPane.setContent(pane);
        tab.setContent(scrollPane);

        Tab tab0 = new Tab("Untitled Tab 2");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setId("Content");
        anchorPane.setMinHeight(0.0);
        anchorPane.setMinWidth(0.0);
        anchorPane.setPrefHeight(180.0);
        anchorPane.setPrefWidth(200.0);
        tab0.setContent(anchorPane);

        TabPane tabPane0 = new TabPane();
        tabPane0.setPrefHeight(200.0);
        tabPane0.setPrefWidth(200.0);
        tabPane0.getStyleClass().add("floating");
        tabPane0.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab1 = new Tab("ScrollPane");

        TableView tableView = new TableView();
        tableView.setPrefHeight(200.0);
        tableView.setPrefWidth(200.0);

        TableColumn tableColumn = new TableColumn("Column X");
        tableColumn.setPrefWidth(75.0);

        TableColumn tableColumn0 = new TableColumn("Column X");
        tableColumn0.setPrefWidth(75.0);
        tab1.setContent(tableView);

        Tab tab2 = new Tab("Untitled Tab 2");

        AnchorPane anchorPane0 = new AnchorPane();
        anchorPane0.setId("Content");
        anchorPane0.setMinHeight(0.0);
        anchorPane0.setMinWidth(0.0);
        anchorPane0.setPrefHeight(180.0);
        anchorPane0.setPrefWidth(200.0);
        tab2.setContent(anchorPane0);

        TabPane tabPane1 = new TabPane();
        tabPane1.setPrefHeight(200.0);
        tabPane1.setPrefWidth(200.0);
        tabPane1.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab3 = new Tab("ScrollPane");

        ScrollPane scrollPane0 = new ScrollPane();
        scrollPane0.setContent(null);
        scrollPane0.setPrefHeight(200.0);
        scrollPane0.setPrefWidth(200.0);
        tab3.setContent(scrollPane0);

        Tab tab4 = new Tab("Untitled Tab 2");

        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.setId("Content");
        anchorPane1.setMinHeight(0.0);
        anchorPane1.setMinWidth(0.0);
        anchorPane1.setPrefHeight(180.0);
        anchorPane1.setPrefWidth(200.0);
        tab4.setContent(anchorPane1);

        TabPane tabPane2 = new TabPane();
        tabPane2.setPrefHeight(200.0);
        tabPane2.setPrefWidth(200.0);
        tabPane2.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab5 = new Tab("ListView");

        ListView listView = new ListView();
        listView.setPrefHeight(200.0);
        listView.setPrefWidth(200.0);
        tab5.setContent(listView);

        Tab tab6 = new Tab("Untitled Tab 2");

        AnchorPane anchorPane2 = new AnchorPane();
        anchorPane2.setId("Content");
        anchorPane2.setMinHeight(0.0);
        anchorPane2.setMinWidth(0.0);
        anchorPane2.setPrefHeight(180.0);
        anchorPane2.setPrefWidth(200.0);
        tab6.setContent(anchorPane2);

        TabPane tabPane3 = new TabPane();
        tabPane3.setPrefHeight(200.0);
        tabPane3.setPrefWidth(200.0);
        tabPane3.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab7 = new Tab("TreeView");

        TreeView treeView = new TreeView();
        treeView.setPrefHeight(200.0);
        treeView.setPrefWidth(200.0);
        tab7.setContent(treeView);

        Tab tab8 = new Tab("Untitled Tab 2");

        AnchorPane anchorPane3 = new AnchorPane();
        anchorPane3.setId("Content");
        anchorPane3.setMinHeight(0.0);
        anchorPane3.setMinWidth(0.0);
        anchorPane3.setPrefHeight(180.0);
        anchorPane3.setPrefWidth(200.0);
        tab8.setContent(anchorPane3);

        TabPane tabPane4 = new TabPane();
        tabPane4.setPrefHeight(200.0);
        tabPane4.setPrefWidth(200.0);
        tabPane4.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab9 = new Tab("TableView");

        TableView tableView0 = new TableView();
        tableView0.setPrefHeight(200.0);
        tableView0.setPrefWidth(200.0);

        TableColumn tableColumn1 = new TableColumn("Column X");
        tableColumn1.setPrefWidth(75.0);

        TableColumn tableColumn2 = new TableColumn("Column X");
        tableColumn2.setPrefWidth(75.0);
        tab9.setContent(tableView0);

        Tab tab10 = new Tab("Untitled Tab 2");

        AnchorPane anchorPane4 = new AnchorPane();
        anchorPane4.setId("Content");
        anchorPane4.setMinHeight(0.0);
        anchorPane4.setMinWidth(0.0);
        anchorPane4.setPrefHeight(180.0);
        anchorPane4.setPrefWidth(200.0);
        tab10.setContent(anchorPane4);

        TabPane tabPane5 = new TabPane();
        tabPane5.setPrefHeight(200.0);
        tabPane5.setPrefWidth(200.0);
        tabPane5.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab11 = new Tab("ScrollPane");

        ScrollPane scrollPane1 = new ScrollPane();
        scrollPane1.setPrefHeight(200.0);
        scrollPane1.setPrefWidth(200.0);

        AnchorPane anchorPane5 = new AnchorPane();
        anchorPane5.setId("Content");
        anchorPane5.setMinHeight(0.0);
        anchorPane5.setMinWidth(0.0);
        anchorPane5.setPrefHeight(200.0);
        anchorPane5.setPrefWidth(200.0);
        scrollPane1.setContent(anchorPane5);
        tab11.setContent(scrollPane1);

        Tab tab12 = new Tab("Untitled Tab 2");

        AnchorPane anchorPane6 = new AnchorPane();
        anchorPane6.setId("Content");
        anchorPane6.setMinHeight(0.0);
        anchorPane6.setMinWidth(0.0);
        anchorPane6.setPrefHeight(180.0);
        anchorPane6.setPrefWidth(200.0);
        tab12.setContent(anchorPane6);

        TabPane tabPane6 = new TabPane();
        tabPane6.setPrefHeight(200.0);
        tabPane6.setPrefWidth(200.0);
        tabPane6.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab13 = new Tab("TableView");

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.36577181208053694);
        splitPane.setFocusTraversable(true);
        splitPane.setPrefHeight(160.0);
        splitPane.setPrefWidth(200.0);

        AnchorPane anchorPane7 = new AnchorPane();
        anchorPane7.setMinHeight(0.0);
        anchorPane7.setMinWidth(0.0);
        anchorPane7.setPrefHeight(160.0);
        anchorPane7.setPrefWidth(100.0);

        AnchorPane anchorPane8 = new AnchorPane();
        anchorPane8.setMinHeight(0.0);
        anchorPane8.setMinWidth(0.0);
        anchorPane8.setPrefHeight(160.0);
        anchorPane8.setPrefWidth(100.0);
        tab13.setContent(splitPane);

        Tab tab14 = new Tab("Untitled Tab 2");

        AnchorPane anchorPane9 = new AnchorPane();
        anchorPane9.setId("Content");
        anchorPane9.setMinHeight(0.0);
        anchorPane9.setMinWidth(0.0);
        anchorPane9.setPrefHeight(180.0);
        anchorPane9.setPrefWidth(200.0);
        tab14.setContent(anchorPane9);

        TabPane tabPane7 = new TabPane();
        tabPane7.setPrefHeight(200.0);
        tabPane7.setPrefWidth(200.0);
        tabPane7.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab15 = new Tab("Untitled Tab 2");

        AnchorPane anchorPane10 = new AnchorPane();
        anchorPane10.setId("Content");
        anchorPane10.setMinHeight(0.0);
        anchorPane10.setMinWidth(0.0);
        anchorPane10.setPrefHeight(180.0);
        anchorPane10.setPrefWidth(200.0);
        tab15.setContent(anchorPane10);

        TabPane tabPane8 = new TabPane();
        tabPane8.setPrefHeight(200.0);
        tabPane8.setPrefWidth(200.0);
        tabPane8.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab16 = new Tab("TextArea");

        TextArea textArea = new TextArea();
        textArea.setPrefHeight(50.0);
        textArea.setPrefWidth(50.0);
        textArea.setText("Text Area with Lots of text\n" +
                "\n" +
                "\n" +
                "s\n" +
                "a\n" +
                "d\n" +
                "s\n" +
                "d\n" +
                "sd\n" +
                "\n" +
                "s\n" +
                "d\n" +
                "s\n" +
                "d\n" +
                "\n" +
                "s");
        textArea.setWrapText(true);
        tab16.setContent(textArea);

        Tab tab17 = new Tab("Untitled Tab 2");

        AnchorPane anchorPane11 = new AnchorPane();
        anchorPane11.setId("Content");
        anchorPane11.setMinHeight(0.0);
        anchorPane11.setMinWidth(0.0);
        anchorPane11.setPrefHeight(180.0);
        anchorPane11.setPrefWidth(200.0);
        tab17.setContent(anchorPane11);

        SplitPane splitPane0 = new SplitPane();
        splitPane0.setDividerPositions(0.5);
        splitPane0.setFocusTraversable(true);
        splitPane0.setPrefHeight(160.0);
        splitPane0.setPrefWidth(200.0);

        TabPane tabPane9 = new TabPane();
        tabPane9.setPrefHeight(200.0);
        tabPane9.setPrefWidth(200.0);
        tabPane9.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tab18 = new Tab("Untitled Tab 1");

        AnchorPane anchorPane12 = new AnchorPane();
        anchorPane12.setId("Content");
        anchorPane12.setMinHeight(0.0);
        anchorPane12.setMinWidth(0.0);
        anchorPane12.setPrefHeight(180.0);
        anchorPane12.setPrefWidth(200.0);
        tab18.setContent(anchorPane12);

        Tab tab19 = new Tab("Untitled Tab 2");

        AnchorPane anchorPane13 = new AnchorPane();
        anchorPane13.setId("Content");
        anchorPane13.setMinHeight(0.0);
        anchorPane13.setMinWidth(0.0);
        anchorPane13.setPrefHeight(180.0);
        anchorPane13.setPrefWidth(200.0);
        tab19.setContent(anchorPane13);

        TableView tableView1 = new TableView();
        tableView1.setPrefHeight(200.0);
        tableView1.setPrefWidth(200.0);

        TableColumn tableColumn3 = new TableColumn("Column X");
        tableColumn3.setPrefWidth(75.0);

        TableColumn tableColumn4 = new TableColumn("Column X");
        tableColumn4.setPrefWidth(75.0);

        SplitPane splitPane1 = new SplitPane();
        splitPane1.setDividerPositions(0.5167785234899329);
        splitPane1.setFocusTraversable(true);
        splitPane1.setPrefHeight(50.0);
        splitPane1.setPrefWidth(50.0);

        TreeView treeView0 = new TreeView();
        ListView listView0 = new ListView();

        SplitPane splitPane2 = new SplitPane();
        splitPane2.setDividerPositions(0.5335570469798657);
        splitPane2.setFocusTraversable(true);

        ScrollPane scrollPane2 = new ScrollPane();

        AnchorPane anchorPane14 = new AnchorPane();
        anchorPane14.setId("Content");
        anchorPane14.setMinHeight(0.0);
        anchorPane14.setMinWidth(0.0);
        anchorPane14.setPrefHeight(200.0);
        anchorPane14.setPrefWidth(200.0);
        scrollPane2.setContent(anchorPane14);

        SplitPane splitPane3 = new SplitPane();
        splitPane3.setDividerPositions(0.5);
        splitPane3.setFocusTraversable(true);
        splitPane3.setOrientation(Orientation.VERTICAL);

        AnchorPane anchorPane15 = new AnchorPane();
        anchorPane15.setMinHeight(0.0);
        anchorPane15.setMinWidth(0.0);
        anchorPane15.setPrefHeight(100.0);
        anchorPane15.setPrefWidth(160.0);

        AnchorPane anchorPane16 = new AnchorPane();
        anchorPane16.setMinHeight(0.0);
        anchorPane16.setMinWidth(0.0);
        anchorPane16.setPrefHeight(100.0);
        anchorPane16.setPrefWidth(160.0);

        SplitPane splitPane4 = new SplitPane();
        splitPane4.setDividerPositions(0.5);
        splitPane4.setFocusTraversable(true);
        splitPane4.setPrefHeight(160.0);
        splitPane4.setPrefWidth(200.0);

        TextArea textArea0 = new TextArea();
        textArea0.setMinWidth(20.0);
        textArea0.setPrefHeight(30.0);
        textArea0.setPrefWidth(30.0);
        textArea0.setText("Text Area with Lots of text\n" +
                "\n" +
                "\n" +
                "s\n" +
                "a\n" +
                "d\n" +
                "s\n" +
                "d\n" +
                "sd\n" +
                "\n" +
                "s\n" +
                "d\n" +
                "s\n" +
                "d\n" +
                "\n" +
                "s");
        textArea0.setWrapText(true);
        setPadding(new Insets(10.0));

        // Adding to tabPanes
        tabPane.getTabs().addAll(tab, tab0);
        tabPane0.getTabs().addAll(tab1, tab2);
        tabPane1.getTabs().addAll(tab3, tab4);
        tabPane2.getTabs().addAll(tab5, tab6);
        tabPane3.getTabs().addAll(tab7, tab8);
        tabPane4.getTabs().addAll(tab9, tab10);
        tabPane5.getTabs().addAll(tab11, tab12);
        tabPane6.getTabs().addAll(tab13, tab14);
        tabPane7.getTabs().add(tab15);
        tabPane8.getTabs().addAll(tab16, tab17);
        tabPane9.getTabs().addAll(tab18, tab19);

        // Adding to tableViews
        tableView.getColumns().addAll(tableColumn, tableColumn0);
        tableView0.getColumns().addAll(tableColumn1, tableColumn2);
        tableView1.getColumns().addAll(tableColumn3, tableColumn4);

        // Adding to splitPanes
        splitPane.getItems().addAll(anchorPane7, anchorPane8);
        splitPane0.getItems().addAll(tabPane9, tableView1);
        splitPane1.getItems().addAll(treeView0, listView0);
        splitPane2.getItems().addAll(scrollPane2, splitPane3);
        splitPane3.getItems().addAll(anchorPane15, anchorPane16);
        splitPane4.getItems().add(textArea0);

        // Adding to tilePane
        tilePane.getChildren().addAll(tabPane, tabPane0, tabPane1, tabPane2, tabPane3, tabPane4,
                tabPane5, tabPane6, tabPane7, tabPane8, splitPane0,
                splitPane1, splitPane2, splitPane4);

        getChildren().addAll(label, tilePane);
    }
}