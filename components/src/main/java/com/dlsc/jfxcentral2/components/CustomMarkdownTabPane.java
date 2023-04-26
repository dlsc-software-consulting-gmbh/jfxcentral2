package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.MarkdownTab;
import com.sandec.mdfx.MarkdownView;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class CustomMarkdownTabPane extends PaneBase {

    private final MarkdownView markdownView;
    private final VBox contentBox;

    public CustomMarkdownTabPane() {
        getStyleClass().add("custom-markdown-tab-pane");
        markdownView = new MarkdownView();
        markdownView.getStyleClass().add("md-view");

        contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
        setMaxHeight(Region.USE_PREF_SIZE);

        tabsProperty().addListener((observable, oldValue, newValue) -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        contentBox.getChildren().clear();

        if (isLarge() || isMedium()) {
            VBox.setMargin(markdownView, new Insets(0));

            GridPane gridPane = new GridPane();
            gridPane.getStyleClass().addAll("grid-pane", "header");
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setValignment(VPos.CENTER);
            gridPane.getRowConstraints().add(rowConstraints);

            ToggleGroup group = new ToggleGroup();
            group.selectedToggleProperty().addListener((ob, ov, nv) -> {
                CustomToggleButton button = (CustomToggleButton) nv;
                MarkdownTab selectedTab = (MarkdownTab) button.getUserData();
                markdownView.setMdString(selectedTab.getMdString());
            });

            ObservableList<MarkdownTab> markdownTabs = getTabs();
            for (int i = 0; i < markdownTabs.size(); i++) {
                MarkdownTab tab = markdownTabs.get(i);
                CustomToggleButton toggleButton = new CustomToggleButton(tab.getTitle());
                toggleButton.getStyleClass().add("tab-button");
                if (tab.getGraphic() != null) {
                    toggleButton.setGraphic(tab.getGraphic());
                }
                toggleButton.setToggleGroup(group);
                toggleButton.setUserData(tab);

                toggleButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                gridPane.add(toggleButton, i, 0);
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setHgrow(Priority.ALWAYS);
                columnConstraints.setHalignment(HPos.CENTER);
                gridPane.getColumnConstraints().add(columnConstraints);
                if (i == 0) {
                    toggleButton.setSelected(true);
                }
            }
            contentBox.getChildren().setAll(gridPane, markdownView);
        } else {
            VBox.setMargin(markdownView, new Insets(-20, 0, 0, 0));

            ObservableList<MarkdownTab> markdownTabs = getTabs();
            ToggleGroup group = new ToggleGroup();
            group.selectedToggleProperty().addListener((ob, ov, nv) -> {
                if (nv != null) {
                    ToggleButton button = (ToggleButton) nv;
                    MarkdownTab selectedTab = (MarkdownTab) button.getUserData();
                    int i = getTabs().indexOf(selectedTab);
                    markdownView.setMdString(selectedTab.getMdString());
                    contentBox.getChildren().remove(markdownView);
                    contentBox.getChildren().add(i + 1, markdownView);
                } else {
                    contentBox.getChildren().remove(markdownView);
                }
            });

            for (int i = 0; i < markdownTabs.size(); i++) {
                MarkdownTab tab = markdownTabs.get(i);
                ToggleButton toggleButton = new ToggleButton();
                toggleButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                toggleButton.getStyleClass().add("tab-button");

                Label titleLabel = new Label(tab.getTitle());
                titleLabel.getStyleClass().add("title");
                HBox tabGraphicBox = new HBox(titleLabel, new Spacer(Orientation.HORIZONTAL), tab.getGraphic());
                tabGraphicBox.getStyleClass().add("tab-graphic-box");
                toggleButton.setGraphic(tabGraphicBox);
                toggleButton.setToggleGroup(group);
                toggleButton.setUserData(tab);
                contentBox.getChildren().add(toggleButton);
                if (i == 0) {
                    contentBox.getChildren().add(markdownView);
                    toggleButton.setSelected(true);
                }
            }
        }
    }

    private final ListProperty<MarkdownTab> tabs = new SimpleListProperty<>(this, "tabs", FXCollections.observableArrayList());

    public ObservableList<MarkdownTab> getTabs() {
        return tabs.get();
    }

    public ListProperty<MarkdownTab> tabsProperty() {
        return tabs;
    }

    public void setTabs(ObservableList<MarkdownTab> tabs) {
        this.tabs.set(tabs);
    }
}
