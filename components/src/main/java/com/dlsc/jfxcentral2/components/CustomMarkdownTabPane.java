package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.MarkdownTab;
import com.sandec.mdfx.MarkdownView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    ToggleGroup group;

    public CustomMarkdownTabPane() {
        getStyleClass().add("custom-markdown-tab-pane");
        group = new ToggleGroup();

        markdownView = new MarkdownView();
        markdownView.getStyleClass().add("md-view");

        contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
        setMaxHeight(Region.USE_PREF_SIZE);

        tabsProperty().addListener((observable, oldValue, newValue) -> layoutBySize());

        selectedIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            int index = newIndex.intValue();
            if (index >= 0 && index < getTabs().size()) {
                ToggleButton toggleButton = (ToggleButton) group.getToggles().get(index);
                if (toggleButton != null) {
                    toggleButton.setSelected(true);
                }
            }
        });

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int index = group.getToggles().indexOf(newValue);
                setSelectedIndex(index);
                MarkdownTab selectedTab = getTabs().get(index);
                markdownView.setMdString(selectedTab.getMdString());
                if (isSmall()) {
                    contentBox.getChildren().remove(markdownView);
                    contentBox.getChildren().add(index + 1, markdownView);
                }
            } else {
                if (isSmall()) {
                    setSelectedIndex(-1);
                    markdownView.setMdString("");
                    contentBox.getChildren().remove(markdownView);
                }
            }
        });
    }

    @Override
    protected void layoutBySize() {
        contentBox.getChildren().clear();
        group.getToggles().clear();
        if (isLarge() || isMedium()) {
            VBox.setMargin(markdownView, new Insets(0));
            GridPane gridPane = new GridPane();
            gridPane.getStyleClass().addAll("grid-pane", "header");
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setValignment(VPos.CENTER);
            gridPane.getRowConstraints().add(rowConstraints);
            ObservableList<MarkdownTab> markdownTabs = getTabs();
            for (int i = 0; i < markdownTabs.size(); i++) {
                MarkdownTab tab = markdownTabs.get(i);
                CustomToggleButton toggleButton = new CustomToggleButton(tab.getTitle());
                toggleButton.getStyleClass().add("tab-button");
                if (tab.getGraphic() != null) {
                    toggleButton.setGraphic(tab.getGraphic());
                }
                toggleButton.setToggleGroup(group);
                toggleButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                gridPane.add(toggleButton, i, 0);
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setHgrow(Priority.ALWAYS);
                columnConstraints.setHalignment(HPos.CENTER);
                gridPane.getColumnConstraints().add(columnConstraints);
                if (i == getSelectedIndex()) {
                    toggleButton.setSelected(true);
                }
            }
            contentBox.getChildren().setAll(gridPane, markdownView);
        } else {
            VBox.setMargin(markdownView, new Insets(-20, 0, 0, 0));
            ObservableList<MarkdownTab> markdownTabs = getTabs();
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
                contentBox.getChildren().add(toggleButton);
                if (i == getSelectedIndex()) {
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

    private final IntegerProperty selectedIndex = new SimpleIntegerProperty(this, "selectedTabIndex", 0);

    public int getSelectedIndex() {
        return selectedIndex.get();
    }

    public IntegerProperty selectedIndexProperty() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex.set(selectedIndex);
    }
}
