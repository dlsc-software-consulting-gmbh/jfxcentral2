package com.dlsc.jfxcentral2.components;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.IkonProvider;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

public class IkonliBrowser extends PaneBase {

    private final TextField selection = new TextField();
    private final GridView<Ikon> gridView;
    private final IkonSearchField searchField;
    private final Label statusLabel;

    public IkonliBrowser() {
        getStyleClass().add("ikonli-browser");

        ListView<IkonData> fontsListView = new ListView<>();
        fontsListView.setMinWidth(Region.USE_PREF_SIZE);
        fontsListView.getItems().setAll(resolveIkonData());
        fontsListView.getSelectionModel().getSelectedItems().addListener((Observable it) -> fillGridView(fontsListView.getSelectionModel().getSelectedItems()));
        fontsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        gridView = new GridView<>();
        gridView.setHorizontalCellSpacing(20);
        gridView.setVerticalCellSpacing(20);
        gridView.getStyleClass().add("icon-grid");
        gridView.setCellFactory(view -> new GridCell<>() {

            FontIcon icon = new FontIcon();
            Label nameLabel = new Label();
            VBox wrapper;

            {
                nameLabel.setWrapText(true);
                nameLabel.setMinHeight(Region.USE_PREF_SIZE);
                nameLabel.setTextAlignment(TextAlignment.CENTER);

                wrapper = new VBox(icon, nameLabel);
                wrapper.getStyleClass().add("wrapper");
                wrapper.setAlignment(Pos.TOP_CENTER);

                selectedIkonProperty().addListener(it -> updateWrapperStyleClass());
                wrapper.setOnMouseClicked(me -> {
                    if (me.isStillSincePress() && me.getButton().equals(MouseButton.PRIMARY)) {
                        setSelectedIkon(getItem());
                    }
                });

                setGraphic(wrapper);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setMinWidth(80);
                wrapper.visibleProperty().bind(emptyProperty().not());
            }

            private void updateWrapperStyleClass() {
                wrapper.getStyleClass().remove("selected");
                Ikon item = getItem();
                if (item != null) {
                    if (item.equals(getSelectedIkon())) {
                        wrapper.getStyleClass().add("selected");
                    }
                }
            }

            @Override
            protected void updateItem(Ikon item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null && !empty) {
                    icon.setIconCode(item);
                    if (fontsListView.getSelectionModel().getSelectedItems().size() > 1) {
                        nameLabel.setText(item.getDescription() + "\n(" + DATA_MAP.get(item).name + ")");
                    } else {
                        nameLabel.setText(item.getDescription());
                    }
                    updateWrapperStyleClass();
                }
            }
        });

        Label fontLabel = new Label("Icon Font:");
        fontLabel.getStyleClass().add("box-label");

        Label selectionLabel = new Label("Selection:");
        selection.setEditable(false);

        Button copy = new Button();
        copy.setGraphic(FontIcon.of(MaterialDesign.MDI_CONTENT_COPY, Color.WHITE));
        copy.disableProperty().bind(selection.textProperty().isEmpty());
        copy.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(selection.getText());
            clipboard.setContent(content);
        });

        searchField = new IkonSearchField();
        searchField.getSuggestions().addListener((Observable it) -> fillGridView(fontsListView.getSelectionModel().getSelectedItems()));
        searchField.setPromptText("Search by name ...");

        fontsListView.getSelectionModel().selectedItemProperty().addListener(it -> {
            searchField.setText("");
            selection.setText("");
        });

        HBox header = new HBox(selectionLabel, selection, copy, searchField);
        header.setAlignment(Pos.CENTER_RIGHT);
        header.getStyleClass().add("header");

        statusLabel = new Label();
        statusLabel.getStyleClass().add("status-label");

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("container");
        borderPane.setTop(header);
        borderPane.setLeft(fontsListView);
        borderPane.setCenter(gridView);
        borderPane.setBottom(statusLabel);

        getChildren().add(borderPane);

        fontsListView.getSelectionModel().select(0);

        setPrefHeight(0);
        VBox.setVgrow(this, Priority.ALWAYS);

        selectedIkon.addListener(it -> selection.setText(getSelectedIkon() != null ? getSelectedIkon().getDescription() : ""));
    }

    private final ObjectProperty<Ikon> selectedIkon = new SimpleObjectProperty<>(this, "selectedItem");

    public Ikon getSelectedIkon() {
        return selectedIkon.get();
    }

    public ObjectProperty<Ikon> selectedIkonProperty() {
        return selectedIkon;
    }

    public void setSelectedIkon(Ikon selectedIkon) {
        this.selectedIkon.set(selectedIkon);
    }

    private void fillGridView(ObservableList<IkonData> selection) {
        Platform.runLater(() -> {
            ObservableList<? extends Ikon> icons = FXCollections.observableArrayList();
            selection.forEach(data -> {
                IkonProvider ikonProvider = data.getIkonProvider();
                EnumSet enumSet = EnumSet.allOf(ikonProvider.getIkon());
                icons.addAll(enumSet);
            });

            searchField.setIcons(icons);
            statusLabel.setText("Number of icons: " + icons.size());

            FilteredList<? extends Ikon> filteredList = new FilteredList<>(icons);
            filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> (Predicate<Ikon>) ikon -> {
                if (StringUtils.isBlank(searchField.getText())) {
                    return true;
                }
                return searchField.getSuggestions().contains(ikon);
            }, searchField.getSuggestions(), searchField.textProperty()));
            gridView.getItems().setAll(filteredList);
        });
    }

    // yes, this can be static and shared across JPro sessions
    private static Map<Ikon, IkonData> DATA_MAP = new HashMap<>();

    private Set<IkonData> resolveIkonData() {
        Set<IkonData> ikons = new TreeSet<>();
        if (null != IkonProvider.class.getModule().getLayer()) {
            for (IkonProvider provider : ServiceLoader.load(IkonProvider.class.getModule().getLayer(), IkonProvider.class)) {
                ikons.add(IkonData.of(provider));
            }
        } else {
            for (IkonProvider provider : ServiceLoader.load(IkonProvider.class)) {
                ikons.add(IkonData.of(provider));
            }
        }

        ikons.forEach(data -> {
            IkonProvider ikonProvider = data.getIkonProvider();
            EnumSet enumSet = EnumSet.allOf(ikonProvider.getIkon());
            enumSet.forEach(icon -> DATA_MAP.put((Ikon) icon, data));
        });

        System.out.println("size: " + DATA_MAP.size());

        return ikons;
    }

    private static class IkonData implements Comparable<IkonData> {
        private String name;
        private IkonProvider ikonProvider;

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int compareTo(IkonData o) {
            return name.compareTo(o.name);
        }

        public IkonProvider getIkonProvider() {
            return ikonProvider;
        }

        static IkonData of(IkonProvider ikonProvider) {
            IkonData ikonData = new IkonData();
            ikonData.name = ikonProvider.getIkon().getSimpleName();
            ikonData.ikonProvider = ikonProvider;
            return ikonData;
        }
    }
}
