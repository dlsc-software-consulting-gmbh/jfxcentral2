package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.CustomTab;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CustomTabPane extends PaneBase {
    public CustomTabPane() {
        getStyleClass().add("custom-tab-pane");

        tabsProperty().addListener((ob, ov, nv) -> layoutBySize());

    }

    @Override
    protected void layoutBySize() {
        int size = getTabs().size();
        ToggleGroup group = new ToggleGroup();

        if (isSmall()) {
            VBox contentBox = new VBox();
            contentBox.getStyleClass().add("content-box");
            for (int i = 0; i < size; i++) {
                CustomTab customTab = getTabs().get(i);
                ToggleButton toggleButton = createToggleButton(group, customTab,false);

                Node content = customTab.getContent();
                content.managedProperty().bind(content.visibleProperty());
                content.visibleProperty().bind(toggleButton.selectedProperty());

                VBox tabContentBox = new VBox(toggleButton, content);
                tabContentBox.getStyleClass().add("tab-content-box");
                contentBox.getChildren().add(tabContentBox);

                if (i == 0) {
                    toggleButton.setSelected(true);
                }
            }
            getChildren().setAll(contentBox);
        } else {
            HBox contentBox = new HBox();
            contentBox.getStyleClass().add("content-box");
            VBox tabsBox = new VBox();
            tabsBox.getStyleClass().add("tabs-box");

            StackPane contentPane = new StackPane();
            contentPane.getStyleClass().add("content-pane");

            for (int i = 0; i < size; i++) {
                CustomTab customTab = getTabs().get(i);
                ToggleButton toggleButton = createToggleButton(group, customTab,true);
                tabsBox.getChildren().add(toggleButton);

                Node content = customTab.getContent();
                content.managedProperty().bind(content.visibleProperty());
                content.visibleProperty().bind(toggleButton.selectedProperty());
                contentPane.getChildren().add(content);

                if (i == 0) {
                    toggleButton.setSelected(true);
                }
            }
            contentBox.getChildren().addAll(tabsBox, contentPane);
            getChildren().setAll(contentBox);
        }
    }

    private ToggleButton createToggleButton(ToggleGroup group, CustomTab customTab, boolean isCustomToggleButton) {
        Header header = new Header();
        header.setTitle(customTab.getTitle());
        header.setIcon(customTab.getIcon());
        ToggleButton toggleButton = isCustomToggleButton? new CustomToggleButton() : new ToggleButton();
        toggleButton.setPrefWidth(Double.MAX_VALUE);
        toggleButton.setGraphic(header);
        toggleButton.setToggleGroup(group);
        return toggleButton;
    }

    private final ListProperty<CustomTab> tabs = new SimpleListProperty<>(this, "tabs", FXCollections.observableArrayList());

    public ObservableList<CustomTab> getTabs() {
        return tabs.get();
    }

    public ListProperty<CustomTab> tabsProperty() {
        return tabs;
    }

    public void setTabs(ObservableList<CustomTab> tabs) {
        this.tabs.set(tabs);
    }

}
