package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.SelectionBox;
import com.dlsc.gemsfx.util.SimpleStringConverter;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.components.gridview.IkonGridView;
import com.dlsc.jfxcentral2.components.gridview.ModelGridView;
import com.dlsc.jfxcentral2.components.tiles.IkonliPackTileView;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.BrowserUrlSync;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;
import com.dlsc.jfxcentral2.utils.QueryParams;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class PacksIconsView extends PaneBase {

    private static final int SEARCH_DELAY = 200;

    private static final String SCOPE_PARAM = "scope";
    private static final String PACK_PARAM = "pack";
    private static final String SORT_PARAM = "sort";
    private static final String SEARCH_PARAM = "search";
    private static final String PACK_SEPARATOR = ",";

    /**
     * Stable value for "no pack selected", so that an empty selection round trips instead of being
     * indistinguishable from "every pack selected", which also omits the parameter.
     */
    private static final String PACK_NONE = "none";

    private final CustomSearchField searchField;
    private final StackPane topWrapper;
    private final HBox sortComboBoxWrapper;
    private final HBox scopeComboBoxWrapper;
    private final StringProperty searchText = new SimpleStringProperty(this, "searchText", "");
    private final SearchService searchService = new SearchService();
    private final IkonGridView ikonGridView;
    private final ComboBox<Scope> scopeComboBox;
    private final HBox packSelectionWrapper;
    private final SelectionBox<IkonliPack> ikonliPackSelection;
    private final ComboBox<Sort> sortComboBox;

    /**
     * Suppresses the debounced search while the search text is being set programmatically.
     */
    private boolean applyingQueryParams;

    /**
     * The path the browser address bar is kept in sync with, null turns the sync off.
     */
    private String canonicalPath;
    private String lastWrittenUrl;

    private enum Scope {
        PACKS, ICONS;

        String paramValue() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    private enum Sort {
        FROM_A_TO_Z("az"), FROM_Z_TO_A("za");

        private final String paramValue;

        Sort(String paramValue) {
            this.paramValue = paramValue;
        }

        String paramValue() {
            return paramValue;
        }
    }

    public PacksIconsView() {
        getStyleClass().addAll("packs-icons-view");

        // top
        searchField = new CustomSearchField(true);
        searchField.getStyleClass().add("filter-search-field");
        searchField.setFocusTraversable(false);
        searchField.textProperty().addListener((ob, ov, str) -> {
            if (applyingQueryParams) {
                return;
            }
            searchService.restart();
        });
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
        ikonliPackSelection = initIkonliPackSelection();
        ikonliPackSelection.getStyleClass().addAll("pack-selection");
        ikonliPackSelection.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(ikonliPackSelection, Priority.ALWAYS);
        packSelectionWrapper = new HBox(new Label("PACKS"), ikonliPackSelection);
        packSelectionWrapper.getStyleClass().addAll("combo-box-wrapper", "pack-selection-wrapper");
        HBox.setHgrow(packSelectionWrapper, Priority.ALWAYS);
        packSelectionWrapper.managedProperty().bind(packSelectionWrapper.visibleProperty());
        packSelectionWrapper.visibleProperty().bind(scopeComboBox.getSelectionModel().selectedItemProperty().map(item -> item == Scope.ICONS));

        sortComboBox = initSortComboBox();
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

        // The controls are created once, so the listeners can be attached here. The address bar
        // follows the debounced search text, so it only changes when the filtering does.
        scopeComboBox.getSelectionModel().selectedItemProperty().addListener(it -> writeUrl());
        sortComboBox.getSelectionModel().selectedItemProperty().addListener(it -> writeUrl());
        ikonliPackSelection.getSelectionModel().getSelectedItems().addListener((ListChangeListener<IkonliPack>) change -> writeUrl());
        searchText.addListener(it -> writeUrl());

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

    /**
     * Applies the query parameters of the current request: "scope" picks packs or icons, "pack"
     * narrows the icon scope to a comma separated list of pack ids, "sort" picks a sort order and
     * "search" fills the search field. The scope is applied first because it clears the search and
     * resets the sort. The pack parameter only applies to the icon scope; in the pack scope the
     * selection is hidden and the icon list is not recomputed, so it is ignored there.
     *
     * @param params the parameters of the request, may be {@code null}
     */
    public void applyQueryParams(QueryParams params) {
        if (params == null || params.isEmpty()) {
            return;
        }

        params.get(SCOPE_PARAM).ifPresent(this::applyScope);
        if (scopeComboBox.getSelectionModel().getSelectedItem() == Scope.ICONS) {
            params.get(PACK_PARAM).ifPresent(this::applyPacks);
        }
        params.get(SORT_PARAM).ifPresent(this::applySort);
        params.get(SEARCH_PARAM).ifPresent(this::applySearchText);
    }

    private void applyScope(String value) {
        String normalized = QueryParams.normalize(value);
        for (Scope scope : Scope.values()) {
            if (QueryParams.normalize(scope.paramValue()).equals(normalized)) {
                scopeComboBox.getSelectionModel().select(scope);
                return;
            }
        }
    }

    private void applyPacks(String value) {
        if (PACK_NONE.equals(QueryParams.normalize(value))) {
            ikonliPackSelection.getSelectionModel().clearSelection();
            return;
        }

        List<IkonliPack> matches = new ArrayList<>();
        for (String id : value.split(PACK_SEPARATOR)) {
            IkonliPack pack = IkonliPackUtil.getInstance().getAggregatedPack(id.trim().toLowerCase(Locale.ROOT));
            if (pack != null && !matches.contains(pack)) {
                matches.add(pack);
            }
        }

        // An empty selection would show nothing at all, so an unusable parameter falls back to the
        // default of having every pack selected.
        if (matches.isEmpty()) {
            ikonliPackSelection.getSelectionModel().selectAll();
            return;
        }

        ikonliPackSelection.getSelectionModel().clearSelection();
        for (IkonliPack pack : matches) {
            ikonliPackSelection.getSelectionModel().select(pack);
        }
    }

    private void applySort(String value) {
        String normalized = QueryParams.normalize(value);
        for (Sort sort : Sort.values()) {
            if (QueryParams.normalize(sort.paramValue()).equals(normalized)) {
                sortComboBox.getSelectionModel().select(sort);
                return;
            }
        }
    }

    private void applySearchText(String text) {
        // Setting the text starts the debounced search service, which would only delay the first
        // filtering. The grid switches on the raw text, the filtering on the debounced one, so both
        // have to be set here.
        applyingQueryParams = true;
        try {
            searchField.setText(text);
        } finally {
            applyingQueryParams = false;
        }
        searchText.set(text);
    }

    /**
     * Sets the path the browser address bar is kept in sync with. While it is set, every later change
     * of the scope, the selected packs, the sort order or the search text rewrites the address bar
     * without reloading the page; {@code null} turns the sync off. The router pushes the history
     * entry only after the page is mounted, so writing on this call would replace the previous
     * page's entry instead.
     *
     * @param canonicalPath the path of the page as registered in the router, or {@code null}
     */
    public void setCanonicalPath(String canonicalPath) {
        this.canonicalPath = canonicalPath;
    }

    /**
     * The current state as query parameters, omitting every dimension that is at its default. The
     * packs are only part of it in the icon scope, matching {@link #applyQueryParams(QueryParams)}.
     *
     * @return the parameters, never {@code null}
     */
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();

        Scope scope = scopeComboBox.getSelectionModel().getSelectedItem();
        if (scope != null && scope != Scope.ICONS) {
            params.put(SCOPE_PARAM, scope.paramValue());
        }

        if (scope == Scope.ICONS) {
            List<IkonliPack> selected = ikonliPackSelection.getSelectionModel().getSelectedItems();
            if (selected.isEmpty()) {
                params.put(PACK_PARAM, PACK_NONE);
            } else if (selected.size() < ikonliPackSelection.getItems().size()) {
                params.put(PACK_PARAM, selected.stream()
                        .map(pack -> IkonliPackUtil.getInstance().getAggregatedId(pack))
                        .collect(Collectors.joining(PACK_SEPARATOR)));
            }
        }

        Sort sort = sortComboBox.getSelectionModel().getSelectedItem();
        if (sort != null && sort != Sort.FROM_A_TO_Z) {
            params.put(SORT_PARAM, sort.paramValue());
        }

        String text = searchText.get();
        if (StringUtils.isNotBlank(text)) {
            params.put(SEARCH_PARAM, text.trim());
        }

        return params;
    }

    private void writeUrl() {
        if (canonicalPath == null) {
            return;
        }
        String url = QueryParams.buildUrl(canonicalPath, toQueryParams());
        if (!url.equals(lastWrittenUrl) && BrowserUrlSync.replace(this, url)) {
            lastWrittenUrl = url;
        }
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

        // packs data (aggregated: one entry per Maven artifact)
        ObservableList<IkonliPack> packs = FXCollections.observableArrayList(IkonliPackUtil.getInstance().getAggregatedPacks());
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
        ObservableList<IkonliPack> packs = FXCollections.observableArrayList(IkonliPackUtil.getInstance().getAggregatedPacks());
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

        selectionBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectionBox.getSelectionModel().selectAll();
        return selectionBox;
    }

}
