package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.SelectionBox;
import com.dlsc.gemsfx.util.SimpleStringConverter;
import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.components.gridview.IkonGridView;
import com.dlsc.jfxcentral2.components.gridview.ModelGridView;
import com.dlsc.jfxcentral2.components.tiles.IkonliPackTileView;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class PacksIconsView extends PaneBase {

    private static final int SEARCH_DELAY = 200;

    private final CustomSearchField searchField;
    private final StackPane topWrapper;
    private final HBox sortComboBoxWrapper;
    private final HBox scopeComboBoxWrapper;
    private final StringProperty searchText = new SimpleStringProperty(this, "searchText", "");
    private final SearchService searchService = new SearchService();
    private final IkonGridView ikonGridView;
    private final ComboBox<Scope> scopeComboBox;
    private final HBox packSelectionWrapper;

    private enum Scope {
        PACKS, ICONS
    }

    private enum Sort {
        FROM_A_TO_Z, FROM_Z_TO_A
    }

    public PacksIconsView() {
        getStyleClass().addAll("packs-icons-view");

        // top
        searchField = new CustomSearchField(true);
        searchField.getStyleClass().add("filter-search-field");
        searchField.setFocusTraversable(false);
        searchField.textProperty().addListener((ob, ov, str) -> searchService.restart());
        HBox.setHgrow(searchField, Priority.ALWAYS);

        searchService.setOnSucceeded(evt -> searchText.set(searchField.getText()));

        scopeComboBox = initScopeComboBox();
        scopeComboBox.getStyleClass().addAll("scope-combo-box");
        scopeComboBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(scopeComboBox, Priority.ALWAYS);
        scopeComboBoxWrapper = new HBox(new Label("SCOPE"), scopeComboBox);
        scopeComboBoxWrapper.getStyleClass().addAll("combo-box-wrapper", "scope-combo-box-wrapper");
        HBox.setHgrow(scopeComboBoxWrapper, Priority.ALWAYS);

        searchField.promptTextProperty().bind(Bindings.createStringBinding(() -> {
            if (scopeComboBox.getSelectionModel().getSelectedItem() == Scope.PACKS) {
                return "Search for icon packs";
            } else {
                return "Search for icons";
            }
        }, scopeComboBox.getSelectionModel().selectedItemProperty()));

        // pack selection
        SelectionBox<IkonliPack> ikonliPackSelection = initIkonliPackSelection();
        ikonliPackSelection.getStyleClass().addAll("pack-selection");
        ikonliPackSelection.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(ikonliPackSelection, Priority.ALWAYS);
        packSelectionWrapper = new HBox(new Label("PACKS"), ikonliPackSelection);
        packSelectionWrapper.getStyleClass().addAll("combo-box-wrapper", "pack-selection-wrapper");
        HBox.setHgrow(packSelectionWrapper, Priority.ALWAYS);
        packSelectionWrapper.managedProperty().bind(packSelectionWrapper.visibleProperty());
        packSelectionWrapper.visibleProperty().bind(scopeComboBox.getSelectionModel().selectedItemProperty().map(item -> item == Scope.ICONS));

        ComboBox<Sort> sortComboBox = initSortComboBox();
        sortComboBox.getStyleClass().addAll("sort-combo-box");
        sortComboBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(sortComboBox, Priority.ALWAYS);
        sortComboBoxWrapper = new HBox(new Label("SORT"), sortComboBox);
        sortComboBoxWrapper.getStyleClass().addAll("combo-box-wrapper", "sort-combo-box-wrapper");
        HBox.setHgrow(sortComboBoxWrapper, Priority.ALWAYS);

        topWrapper = new StackPane();
        topWrapper.getStyleClass().add("top-wrapper");

        // center
        ikonGridView = createIkonGridView(scopeComboBox, ikonliPackSelection, sortComboBox);

        ModelGridView<IkonliPack> packGridView = createModelGridView(sortComboBox);

        scopeComboBox.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> {
            searchField.setText("");
            sortComboBox.getSelectionModel().select(0);
        });

        ObjectBinding<? extends PaneBase> gridViewNodeBinding = Bindings.createObjectBinding(() -> {
            if (StringUtils.isBlank(searchField.getText()) || (scopeComboBox.getSelectionModel().getSelectedItem() == Scope.PACKS)) {
                getStyleClass().remove("icons");
                if (!getStyleClass().contains("packs")) {
                    getStyleClass().add("packs");
                }
                ikonGridView.setVisible(false);
                packGridView.setVisible(true);
                return packGridView;
            } else {
                getStyleClass().remove("packs");
                if (!getStyleClass().contains("icons")) {
                    getStyleClass().add("icons");
                }
                packGridView.setVisible(false);
                ikonGridView.setVisible(true);
                return ikonGridView;
            }
        }, scopeComboBox.getSelectionModel().selectedItemProperty(), searchField.textProperty());

        BorderPane gridWrapper = new BorderPane();
        gridWrapper.centerProperty().bind(gridViewNodeBinding);
        gridWrapper.getStyleClass().add("grid-wrapper");

        Region spacer = new Region();
        spacer.getStyleClass().add("view-spacer");

        ToggleButton collapsibleButton = new ToggleButton();
        collapsibleButton.getStyleClass().add("collapsible-button");
        collapsibleButton.setMaxWidth(Double.MAX_VALUE);
        Header header = new Header();
        header.setTitle("FILTERS");
        header.setIcon(JFXCentralIcon.CHEVRON_TOP);
        collapsibleButton.setGraphic(header);

        collapsibleButton.setSelected(true);
        topWrapper.managedProperty().bind(topWrapper.visibleProperty());
        topWrapper.visibleProperty().bind(Bindings.createObjectBinding(() -> {
            if (isSmall()) {
                return collapsibleButton.isSelected();
            } else {
                return true;
            }
        }, collapsibleButton.selectedProperty(), sizeProperty()));
        collapsibleButton.managedProperty().bind(collapsibleButton.visibleProperty());
        collapsibleButton.visibleProperty().bind(sizeProperty().map(Size::isSmall));
        VBox contentBoxWrapper = new VBox(collapsibleButton, topWrapper);
        contentBoxWrapper.getStyleClass().add("content-box-wrapper");
        contentBoxWrapper.setMaxWidth(Double.MAX_VALUE);

        VBox contentBox = new VBox(contentBoxWrapper, spacer, gridWrapper);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
        updateUI();
    }

    private class SearchService extends Service<String> {

        @Override
        protected Task<String> createTask() {
            return new Task<>() {
                @Override
                protected String call() throws Exception {
                    Thread.sleep(SEARCH_DELAY);
                    if (!isCancelled()) {
                        return searchField.getText();
                    }
                    return null;
                }
            };
        }
    }

    @Override
    protected void layoutBySize() {
        if (isLgToMdOrMdToLg()) {
            return;
        }
        updateUI();
    }

    private void updateUI() {
        Pane topBox = isSmall() ? new VBox() : new HBox();
        topBox.getStyleClass().addAll("top-box");
        topBox.getChildren().addAll(searchField, scopeComboBoxWrapper, packSelectionWrapper, sortComboBoxWrapper);
        topWrapper.getChildren().setAll(topBox);
    }

    private ModelGridView<IkonliPack> createModelGridView(ComboBox<Sort> sortComboBox) {
        ModelGridView<IkonliPack> packGridView = new ModelGridView<>();
        packGridView.sizeProperty().bind(sizeProperty());
        packGridView.setTileViewProvider(IkonliPackTileView::new);
        packGridView.setColumns(3);
        packGridView.setRows(3);
        packGridView.managedProperty().bind(visibleProperty());

        // packs data
        ObservableList<IkonliPack> packs = FXCollections.observableArrayList(DataRepository.getInstance().getIkonliPacks());
        FilteredList<IkonliPack> filteredPacks = new FilteredList<>(packs);
        filteredPacks.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            String text = searchText.get().trim();
            if (StringUtils.isBlank(text)) {
                return item -> true;
            } else {
                return item -> StringUtils.containsIgnoreCase(item.getName(), text);
            }
        }, searchText));

        // sort
        SortedList<IkonliPack> sortedPacks = new SortedList<>(filteredPacks);
        sortedPacks.comparatorProperty().bind(Bindings.createObjectBinding(() -> {
            Sort sort = sortComboBox.getValue();
            if (sort == Sort.FROM_A_TO_Z) {
                return Comparator.comparing((IkonliPack pack) -> pack.getName().toLowerCase());
            } else {
                return Comparator.comparing((IkonliPack pack) -> pack.getName().toLowerCase()).reversed();
            }
        }, sortComboBox.valueProperty()));
        packGridView.setItems(sortedPacks);
        return packGridView;
    }

    private IkonGridView createIkonGridView(ComboBox<Scope> scopeComboBox, SelectionBox<IkonliPack> ikonliPackSelection, ComboBox<Sort> sortComboBox) {
        IkonGridView ikonGridView = new IkonGridView();
        ikonGridView.sizeProperty().bind(sizeProperty());
        ikonGridView.managedProperty().bind(visibleProperty());
        ikonGridView.paginationModeProperty().bind(Bindings.createObjectBinding(() -> {
            Scope scope = scopeComboBox.getSelectionModel().getSelectedItem();
            if (scope == Scope.PACKS) {
                return IkonGridView.PaginationMode.ADVANCED;
            }
            if (isLarge()) {
                return IkonGridView.PaginationMode.ADVANCED;
            } else {
                return IkonGridView.PaginationMode.SIMPLE;
            }
        }, sizeProperty(), scopeComboBox.valueProperty()));

        // icons (default load all icons)
        ObservableList<Ikon> icons = FXCollections.observableArrayList(IkonliPackUtil.getInstance().getAllIkons());

        ObservableList<IkonliPack> selectedPacks = ikonliPackSelection.getSelectionModel().getSelectedItems();
        selectedPacks.subscribe(() -> {
            if (scopeComboBox.getSelectionModel().getSelectedItem() == Scope.ICONS) {
                if (selectedPacks.size() == ikonliPackSelection.getItems().size()) {
                    icons.setAll(IkonliPackUtil.getInstance().getAllIkons());
                } else {
                    icons.setAll(IkonliPackUtil.getInstance().getIkonsForPacksFiltered(selectedPacks));
                }
            }
        });

        FilteredList<Ikon> filteredIconsList = new FilteredList<>(icons);
        filteredIconsList.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            String text = searchText.get().toLowerCase().trim();
            if (StringUtils.isBlank(text)) {
                return item -> true;
            } else {
                return item -> {
                    String str = item.getDescription().toLowerCase();
                    String[] keys = text.split("\\s+");
                    for (String key : keys) {
                        if (!StringUtils.containsAnyIgnoreCase(str, key)) {
                            return false;
                        }
                    }
                    return true;
                    // return StringUtils.containsAnyIgnoreCase(str, keys);
                };
            }
        }, searchText));

        // sort
        SortedList<Ikon> sortedList = new SortedList<>(filteredIconsList);
        sortedList.comparatorProperty().bind(Bindings.createObjectBinding(() -> {
            Sort sort = sortComboBox.getValue();
            if (sort == Sort.FROM_A_TO_Z) {
                return Comparator.comparing((Ikon ikon) -> ikon.getDescription().toLowerCase());
            } else {
                return Comparator.comparing((Ikon ikon) -> ikon.getDescription().toLowerCase()).reversed();
            }
        }, sortComboBox.valueProperty()));
        ikonGridView.setItems(sortedList);
        return ikonGridView;
    }

    private ComboBox<Sort> initSortComboBox() {
        ComboBox<Sort> comboBox = new ComboBox<>();
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Sort object) {
                return object == Sort.FROM_A_TO_Z ? "A → Z" : "Z → A";
            }

            @Override
            public Sort fromString(String string) {
                return null;
            }
        });
        comboBox.getItems().addAll(Sort.values());
        comboBox.getSelectionModel().select(Sort.FROM_A_TO_Z);
        return comboBox;
    }

    private ComboBox<Scope> initScopeComboBox() {
        ComboBox<Scope> scopeBox = new ComboBox<>();
        scopeBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Scope object) {
                return object.name().toUpperCase();
            }

            @Override
            public Scope fromString(String string) {
                return null;
            }
        });
        scopeBox.getItems().addAll(Scope.values());
        scopeBox.getSelectionModel().select(Scope.ICONS);
        return scopeBox;
    }

    private SelectionBox<IkonliPack> initIkonliPackSelection() {
        ObservableList<IkonliPack> packs = FXCollections.observableArrayList(DataRepository.getInstance().getIkonliPacks());
        SelectionBox<IkonliPack> selectionBox = new SelectionBox<>(packs);
        selectionBox.setPromptText("Select");
        selectionBox.setItemConverter(new SimpleStringConverter<>(IkonliPack::getName));
        selectionBox.setSelectedItemsConverter(new SimpleStringConverter<>(list -> {
            if (list.isEmpty()) {
                return "Select";
            } else if (list.size() == selectionBox.getItems().size()) {
                return "All";
            } else {
                return String.valueOf(list.size());
            }
        }));
        selectionBox.setTop(createExtraButtonsBox(selectionBox));
        selectionBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectionBox.getSelectionModel().selectAll();
        return selectionBox;
    }

    private Node createExtraButtonsBox(SelectionBox<IkonliPack> selectionBox) {
        Button clearButton = selectionBox.createExtraButton("Clear", null);
        clearButton.getStyleClass().add("clear-button");
        clearButton.setOnAction(e -> selectionBox.getSelectionModel().clearSelection());

        Button selectAllButton = selectionBox.createExtraButton("Select All", () -> selectionBox.getSelectionModel().selectAll());
        selectAllButton.managedProperty().bind(selectAllButton.visibleProperty());
        selectAllButton.visibleProperty().bind(selectionBox.currentSelectionModeProperty().isEqualTo(SelectionMode.MULTIPLE));
        selectAllButton.getStyleClass().add("select-all-button");

        Button materialDesignAZ = createSelectMaterialDesignAZButton(selectionBox);

        Button material2Variants = createSelectMaterial2VariantsButton(selectionBox);

        VBox actionBox = new VBox(clearButton, selectAllButton, material2Variants, materialDesignAZ);
        actionBox.managedProperty().bind(actionBox.visibleProperty());
        actionBox.visibleProperty().bind(selectionBox.itemsProperty().emptyProperty().not());

        Label quickSelectLabel = new Label("QUICK SELECT");
        quickSelectLabel.getStyleClass().add("quick-select-label");
        quickSelectLabel.setRotate(-90);

        HBox extraButtonsBox = new HBox(new Group(quickSelectLabel), actionBox);
        extraButtonsBox.getStyleClass().add("extra-buttons-box");

        return extraButtonsBox;
    }

    private Button createSelectMaterial2VariantsButton(SelectionBox<IkonliPack> selectionBox) {
        return selectionBox.createExtraButton("Select Material2 Variants", () -> {
            selectionBox.getSelectionModel().clearSelection();

            List<IkonliPack> items = selectionBox.getItems();
            List<Integer> indices = new ArrayList<>();

            String prefix = "Material2";

            for (int i = 0; i < items.size(); i++) {
                IkonliPack pack = items.get(i);
                String name = Objects.requireNonNullElse(pack.getName(), "");

                if (name.startsWith(prefix)) {
                    indices.add(i);
                }
            }

            if (!indices.isEmpty()) {
                selectionBox.getSelectionModel().selectIndices(
                        indices.get(0),
                        indices.size() > 1
                                ? indices.subList(1, indices.size()).stream().mapToInt(Integer::intValue).toArray()
                                : new int[0]
                );
            }
        });
    }

    private Button createSelectMaterialDesignAZButton(SelectionBox<IkonliPack> selectionBox) {
        return selectionBox.createExtraButton("Select MaterialDesign A–Z", () -> {
            selectionBox.getSelectionModel().clearSelection();

            List<IkonliPack> items = selectionBox.getItems();
            List<Integer> indices = new ArrayList<>();

            String prefix = "MaterialDesign";
            int expectedLength = prefix.length() + 1;

            for (int i = 0; i < items.size(); i++) {
                IkonliPack pack = items.get(i);
                String name = Objects.requireNonNullElse(pack.getName(), "");

                if (name.startsWith(prefix)
                        && name.length() == expectedLength
                        && Character.isUpperCase(name.charAt(prefix.length()))) {
                    indices.add(i);
                }
            }

            if (!indices.isEmpty()) {
                selectionBox.getSelectionModel().selectIndices(
                        indices.get(0),
                        indices.size() > 1
                                ? indices.subList(1, indices.size()).stream().mapToInt(Integer::intValue).toArray()
                                : new int[0]
                );
            }
        });
    }

}
