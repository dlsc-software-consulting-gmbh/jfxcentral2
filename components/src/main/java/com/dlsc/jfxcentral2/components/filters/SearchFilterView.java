package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral2.components.CustomSearchField;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.BrowserUrlSync;
import com.dlsc.jfxcentral2.utils.QueryParams;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.EnumConverter;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SearchFilterView<T> extends PaneBase {
    private static final String DEFAULT_STYLE_CLASS = "search-filter-view";
    private static final Orientation FILTER_BOX_DEFAULT_ORIENTATION = Orientation.HORIZONTAL;
    private static final String WITH_SEARCH_FIELD = "with-search-field";

    /**
     * Reserved query parameter names, see {@link #applyQueryParams(QueryParams)}.
     */
    private static final String SEARCH_PARAM = "search";
    private static final String SORT_PARAM = "sort";

    /**
     * delayed search interval 200 ms
     */
    private static final int SEARCH_DELAY = 200;

    /**
     * delayed search text
     */
    private final StringProperty searchText = new SimpleStringProperty(this, "searchText", "");

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setName("Search Filter Thread");
        thread.setDaemon(true);
        return thread;
    });

    private ScheduledFuture<?> future;
    private final CustomSearchField searchField = new CustomSearchField(true);

    /**
     * The current selection per filter group, keyed by parameter name. The combo boxes are recreated
     * on every layout change, so the selection has to survive outside of them. The applied flag of
     * the filter items must not be used for this: those lists are static and shared by all sessions.
     */
    private final Map<String, String> selectedFilterNames = new LinkedHashMap<>();
    private String selectedSortParamValue;

    /**
     * The combo boxes of the current layout, so that query parameters take effect immediately
     * instead of only after the next rebuild.
     */
    private final Map<String, ComboBox<FilterItem<T>>> comboBoxByParam = new LinkedHashMap<>();
    private ComboBox<SortItem<T>> sortComboBox;

    /**
     * Suppresses the debounced search while the search text is being set programmatically.
     */
    private boolean applyingQueryParams;

    /**
     * The path the browser address bar is kept in sync with. Null turns the sync off, which is the
     * case for pages that were not created by a registered route.
     */
    private String canonicalPath;
    private String lastWrittenUrl;
    private BiPredicate<Node, String> urlWriter = BrowserUrlSync::replace;

    private final StringConverter<FilterItem<T>> predicateItemStringConverter = new StringConverter<>() {
        @Override
        public String toString(FilterItem<T> object) {
            return object == null ? "" : object.name;
        }

        @Override
        public FilterItem<T> fromString(String string) {
            return null;
        }
    };

    public record FilterItem<T>(String name, Predicate<T> predicate, boolean isApplied) {
        public FilterItem(String name, Predicate<T> predicate) {
            this(name, predicate, false);
        }
    }

    /**
     * A group of mutually exclusive filters, rendered as a single combo box.
     *
     * @param paramName the query parameter this group is addressed by, derived from the title
     */
    public record FilterGroup<T>(String title, List<FilterItem<T>> filterItems, String paramName) {
        public FilterGroup(String title, List<FilterItem<T>> filterItems) {
            this(title, filterItems, QueryParams.toSlug(title));
        }
    }

    /**
     * A sort order offered by a {@link SortGroup}.
     *
     * @param paramValue the stable value used in the "sort" query parameter. It is deliberately not
     *                   derived from the display name, so that rewording a label does not break
     *                   links that were already shared.
     */
    public record SortItem<T>(String name, Comparator<T> comparator, boolean isApplied, String paramValue) {
        public SortItem(String name, Comparator<T> comparator) {
            this(name, comparator, false, QueryParams.toSlug(name));
        }

        public SortItem(String name, Comparator<T> comparator, String paramValue) {
            this(name, comparator, false, paramValue);
        }

        public SortItem(String name, Comparator<T> comparator, boolean isApplied) {
            this(name, comparator, isApplied, QueryParams.toSlug(name));
        }
    }

    public record SortGroup<T>(String title, List<SortItem<T>> sortItems) {
    }

    public SearchFilterView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        searchField.setFocusTraversable(false);
        searchField.getStyleClass().add("filter-search-field");
        searchField.promptTextProperty().bind(searchPromptTextProperty());
        searchField.managedProperty().bind(searchField.visibleProperty());
        searchField.visibleProperty().bind(onSearchProperty().isNotNull());
        searchField.textProperty().addListener((ob, ov, str) -> {
            if (applyingQueryParams) {
                return;
            }
            if (future != null) {
                future.cancel(false);
            }
            future = executorService.schedule(() -> {
                if (StringUtils.equalsIgnoreCase(str, searchField.getText())) {
                    Platform.runLater(() -> searchText.set(str));
                }
            }, SEARCH_DELAY, TimeUnit.MILLISECONDS);
        });

        // The address bar follows the debounced text, so it only changes when the filtering does.
        searchText.addListener(it -> writeUrl());

        filterBoxOrientationProperty().addListener(it -> layoutBySize());
        filterGroupsProperty().addListener((InvalidationListener) it -> layoutBySize());
        sortGroupProperty().addListener(it -> layoutBySize());
        extraNodesProperty().addListener((InvalidationListener) it -> layoutBySize());
        onSearchProperty().addListener((ob, ov, nv) -> {
            if (nv != null) {
                if (!getStyleClass().contains(WITH_SEARCH_FIELD)) {
                    getStyleClass().add(WITH_SEARCH_FIELD);
                }
            } else {
                getStyleClass().remove(WITH_SEARCH_FIELD);
            }
            layoutBySize();
        });

        layoutBySize();

    }

    /**
     * Applies the query parameters of the current request: "search" fills the search field, "sort"
     * picks a sort order and every filter group is addressed by its own parameter name.
     *
     * <p>A value that matches no item is ignored and the group falls back to its default, so that a
     * shared link keeps working after the underlying data has been renamed.
     *
     * @param params the parameters of the request, may be {@code null}
     */
    public void applyQueryParams(QueryParams params) {
        if (params == null || params.isEmpty()) {
            return;
        }

        params.get(SEARCH_PARAM).ifPresent(this::applySearchText);

        for (FilterGroup<T> filterGroup : getFilterGroups()) {
            params.get(filterGroup.paramName()).ifPresent(value -> applyFilterValue(filterGroup, value));
        }

        SortGroup<T> sortGroup = getSortGroup();
        if (sortGroup != null) {
            params.get(SORT_PARAM).ifPresent(value -> applySortValue(sortGroup, value));
        }
    }

    private void applySearchText(String text) {
        // Setting the text triggers the debounced search, which would both delay the first filtering
        // and spin up this instance's scheduler thread for a user who never typed anything.
        applyingQueryParams = true;
        try {
            searchField.setText(text);
        } finally {
            applyingQueryParams = false;
        }
        searchText.set(text);
    }

    private void applyFilterValue(FilterGroup<T> filterGroup, String value) {
        String normalized = QueryParams.normalize(value);
        FilterItem<T> item = findFilterItem(filterGroup, it -> QueryParams.normalize(it.name()).equals(normalized));
        if (item == null) {
            item = defaultItem(filterGroup);
        }
        if (item == null) {
            return;
        }

        selectedFilterNames.put(filterGroup.paramName(), item.name());
        ComboBox<FilterItem<T>> comboBox = comboBoxByParam.get(filterGroup.paramName());
        if (comboBox != null) {
            comboBox.getSelectionModel().select(item);
        }
    }

    private void applySortValue(SortGroup<T> sortGroup, String value) {
        String normalized = QueryParams.normalize(value);
        SortItem<T> item = findSortItem(sortGroup, it -> QueryParams.normalize(it.paramValue()).equals(normalized));
        if (item == null) {
            item = defaultSortItem(sortGroup);
        }
        if (item == null) {
            return;
        }

        selectedSortParamValue = item.paramValue();
        if (sortComboBox != null) {
            sortComboBox.getSelectionModel().select(item);
        }
    }

    /**
     * Sets the path the browser address bar is kept in sync with. While it is set, every later change
     * of the search text, a filter or the sort order rewrites the address bar without reloading the
     * page; {@code null} turns the sync off. The current state is not written on the call itself, so
     * that mounting the page does not overwrite the history entry the router is about to push.
     *
     * @param canonicalPath the path of the page as registered in the router, or {@code null}
     */
    public void setCanonicalPath(String canonicalPath) {
        this.canonicalPath = canonicalPath;
    }

    /**
     * The current state as query parameters, omitting every dimension that is at its default. The
     * search component is the debounced text, so the result only changes when the filtering does.
     *
     * @return the parameters, never {@code null}
     */
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();

        if (getOnSearch() != null) {
            String text = searchText.get();
            if (StringUtils.isNotBlank(text)) {
                params.put(SEARCH_PARAM, text.trim());
            }
        }

        for (FilterGroup<T> filterGroup : getFilterGroups()) {
            String selectedName = selectedFilterNames.get(filterGroup.paramName());
            FilterItem<T> defaultItem = defaultItem(filterGroup);
            if (selectedName != null && (defaultItem == null || !selectedName.equals(defaultItem.name()))) {
                params.put(filterGroup.paramName(), QueryParams.toSlug(selectedName));
            }
        }

        SortGroup<T> sortGroup = getSortGroup();
        if (sortGroup != null && selectedSortParamValue != null) {
            SortItem<T> defaultSortItem = defaultSortItem(sortGroup);
            if (defaultSortItem == null || !selectedSortParamValue.equals(defaultSortItem.paramValue())) {
                params.put(SORT_PARAM, selectedSortParamValue);
            }
        }

        return params;
    }

    /**
     * Replaces the function that writes the URL, so that a local harness can observe the sync
     * outside of the browser. Null restores the default.
     */
    void setUrlWriter(BiPredicate<Node, String> urlWriter) {
        this.urlWriter = urlWriter == null ? BrowserUrlSync::replace : urlWriter;
    }

    private void writeUrl() {
        if (canonicalPath == null) {
            return;
        }
        String url = QueryParams.buildUrl(canonicalPath, toQueryParams());
        if (!url.equals(lastWrittenUrl) && urlWriter.test(this, url)) {
            lastWrittenUrl = url;
        }
    }

    private final ReadOnlyObjectWrapper<Predicate<T>> predicate = new ReadOnlyObjectWrapper<>(this, "predicate", item -> true);

    public Predicate<T> getPredicate() {
        return predicate.get();
    }

    public ReadOnlyObjectProperty<Predicate<T>> predicateProperty() {
        return predicate.getReadOnlyProperty();
    }

    private void setPredicate(Predicate<T> predicate) {
        this.predicate.set(predicate);
    }

    private final ReadOnlyObjectWrapper<Comparator<T>> comparator = new ReadOnlyObjectWrapper<>(this, "comparator");

    public Comparator<T> getComparator() {
        return comparator.get();
    }

    public ReadOnlyObjectProperty<Comparator<T>> comparatorProperty() {
        return comparator.getReadOnlyProperty();
    }

    private BooleanBinding binding;

    @Override
    protected void layoutBySize() {
        binding = null;

        Pane contentBox = isSmall() ? new VBox() : new HBox();
        contentBox.getStyleClass().add("content-box");
        contentBox.managedProperty().bind(contentBox.visibleProperty());

        Spacer spacer = new Spacer();
        spacer.managedProperty().bind(spacer.visibleProperty());
        spacer.visibleProperty().unbind();

        Pane filtersBox = initFiltersSortGroupBox();
        HBox.setHgrow(filtersBox, Priority.ALWAYS);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        contentBox.getChildren().setAll(searchField, filtersBox, spacer);
        contentBox.getChildren().addAll(getExtraNodes());

        if (isSmall()) {
            ToggleButton collapsibleButton = new ToggleButton();
            collapsibleButton.getStyleClass().add("collapsible-button");
            collapsibleButton.setMaxWidth(Double.MAX_VALUE);
            Header header = new Header();
            header.setTitle("FILTERS");
            header.setIcon(JFXCentralIcon.CHEVRON_TOP);
            collapsibleButton.setGraphic(header);

            collapsibleButton.setSelected(true);
            contentBox.visibleProperty().bind(collapsibleButton.selectedProperty());
            VBox contentBoxWrapper = new VBox(collapsibleButton, contentBox);
            contentBoxWrapper.getStyleClass().add("content-box-wrapper");
            contentBoxWrapper.setMaxWidth(Double.MAX_VALUE);
            getChildren().setAll(contentBoxWrapper);
        } else {
            contentBox.setMaxWidth(Double.MAX_VALUE);
            getChildren().setAll(contentBox);
        }

        if (binding != null) {
            blocking.bind(binding);
        }
    }

    private Pane initFiltersSortGroupBox() {
        if (predicate.isBound()) {
            predicate.unbind();
        }
        setPredicate(item -> true);

        comboBoxByParam.clear();
        sortComboBox = null;

        Pane filtersBox = isSmall() ? new VBox() : new HBox();
        filtersBox.getStyleClass().add("filters-box");
        ObservableList<FilterGroup<T>> items = getFilterGroups();
        List<ObjectProperty<Predicate<T>>> childPredicateProperties = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            FilterGroup<T> filterGroup = items.get(i);
            ObjectProperty<Predicate<T>> childPredicateProperty = new SimpleObjectProperty<>(this, "childPredicate", item -> true);
            Node filterBox = createFilterBox(i, filterGroup, childPredicateProperty);
            HBox.setHgrow(filterBox, Priority.ALWAYS);
            filtersBox.getChildren().add(filterBox);
            childPredicateProperties.add(childPredicateProperty);
        }

        Stream.Builder<Observable> builder = Stream.builder();
        builder.add(onSearchProperty());
        builder.add(searchText);
        childPredicateProperties.forEach(builder::add);

        predicate.bind(Bindings.createObjectBinding(() -> {
            Predicate<T> predicate = item -> true;
            if (getOnSearch() != null) {
                predicate = predicate.and(getOnSearch().apply(searchText.get()));
            }
            for (ObjectProperty<Predicate<T>> childPredicateProperty : childPredicateProperties) {
                predicate = predicate.and(childPredicateProperty.get());
            }
            return predicate;
        }, builder.build().toArray(Observable[]::new)));

        if (getSortGroup() != null) {
            if (comparator.isBound()) {
                comparator.unbind();
            }
            ComboBox<SortItem<T>> sortBox = createComboBox();
            sortBox.getStyleClass().addAll("filter-combo-box", "sort-combo-box");
            sortBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(SortItem<T> object) {
                    return object == null ? null : object.name();
                }

                @Override
                public SortItem<T> fromString(String string) {
                    return null;
                }
            });
            sortBox.getItems().setAll(getSortGroup().sortItems());
            comparator.bind(sortBox.getSelectionModel().selectedItemProperty().map(SortItem::comparator));
            selectSortItem(sortBox, getSortGroup());
            sortBox.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> {
                if (nv != null) {
                    selectedSortParamValue = nv.paramValue();
                    writeUrl();
                }
            });
            sortComboBox = sortBox;

            Pane box = getFilterBoxOrientation() == Orientation.VERTICAL ? new VBox() : new HBox();
            box.getStyleClass().addAll("filter-box", "sort-box");
            HBox.setHgrow(box, Priority.ALWAYS);
            Label titleLabel = new Label(getSortGroup().title);
            titleLabel.setMinWidth(Region.USE_PREF_SIZE);
            titleLabel.getStyleClass().add("filter-title");
            HBox.setHgrow(sortBox, Priority.ALWAYS);
            sortBox.setMaxWidth(Double.MAX_VALUE);
            if (isSmall()) {
                box.getChildren().setAll(titleLabel, new Spacer(), sortBox);
            } else {
                box.getChildren().setAll(titleLabel, sortBox);
            }
            filtersBox.getChildren().add(box);
        }

        return filtersBox;
    }

    private Node createFilterBox(int index, FilterGroup<T> filterGroup, ObjectProperty<Predicate<T>> childPredicateProperty) {
        Pane box = getFilterBoxOrientation() == Orientation.VERTICAL ? new VBox() : new HBox();
        box.getStyleClass().addAll("filter-box", "filter-box-" + index);

        Label titleLabel = new Label(filterGroup.title());
        titleLabel.setMinWidth(Region.USE_PREF_SIZE);
        titleLabel.getStyleClass().add("filter-title");

        Node comboBoxNode = createComboBox(filterGroup, childPredicateProperty);
        if (isSmall()) {
            box.getChildren().setAll(titleLabel, new Spacer(), comboBoxNode);
        } else {
            box.getChildren().setAll(titleLabel, comboBoxNode);
        }
        return box;
    }

    /**
     * ComboBox: single selection
     */
    private Node createComboBox(FilterGroup<T> filterGroup, ObjectProperty<Predicate<T>> childPredicateProperty) {
        ComboBox<FilterItem<T>> comboBox = createComboBox();
        comboBox.setConverter(predicateItemStringConverter);
        comboBox.getStyleClass().addAll("filter-combo-box");
        comboBox.getItems().addAll(filterGroup.filterItems);

        childPredicateProperty.bind(comboBox.getSelectionModel().selectedItemProperty().map(it -> it == null ? item -> true : it.predicate));
        selectFilterItem(comboBox, filterGroup);
        comboBox.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> {
            if (nv != null) {
                selectedFilterNames.put(filterGroup.paramName(), nv.name());
                writeUrl();
            }
        });
        comboBoxByParam.put(filterGroup.paramName(), comboBox);
        comboBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(comboBox, Priority.ALWAYS);
        return comboBox;
    }

    /**
     * Selects the item a rebuilt combo box has to show: the one selected before, otherwise the
     * default of the group.
     */
    private void selectFilterItem(ComboBox<FilterItem<T>> comboBox, FilterGroup<T> filterGroup) {
        String selectedName = selectedFilterNames.get(filterGroup.paramName());
        FilterItem<T> item = null;
        if (selectedName != null) {
            item = findFilterItem(filterGroup, it -> selectedName.equals(it.name()));
        }
        if (item == null) {
            item = defaultItem(filterGroup);
        }
        if (item == null) {
            comboBox.getSelectionModel().selectFirst();
        } else {
            comboBox.getSelectionModel().select(item);
        }
    }

    private void selectSortItem(ComboBox<SortItem<T>> comboBox, SortGroup<T> sortGroup) {
        SortItem<T> item = null;
        if (selectedSortParamValue != null) {
            item = findSortItem(sortGroup, it -> selectedSortParamValue.equals(it.paramValue()));
        }
        if (item == null) {
            item = defaultSortItem(sortGroup);
        }
        if (item == null) {
            comboBox.getSelectionModel().selectFirst();
        } else {
            comboBox.getSelectionModel().select(item);
        }
    }

    /**
     * The item a group falls back to: the first one flagged as applied, otherwise the first one.
     * This is the single definition of "default", shared by the initial selection, the fallback for
     * an unknown query parameter value and the decision whether a parameter can be omitted.
     */
    private FilterItem<T> defaultItem(FilterGroup<T> filterGroup) {
        List<FilterItem<T>> items = filterGroup.filterItems();
        if (items == null || items.isEmpty()) {
            return null;
        }
        FilterItem<T> applied = findFilterItem(filterGroup, FilterItem::isApplied);
        return applied == null ? items.get(0) : applied;
    }

    private SortItem<T> defaultSortItem(SortGroup<T> sortGroup) {
        List<SortItem<T>> items = sortGroup.sortItems();
        if (items == null || items.isEmpty()) {
            return null;
        }
        SortItem<T> applied = findSortItem(sortGroup, SortItem::isApplied);
        return applied == null ? items.get(0) : applied;
    }

    private FilterItem<T> findFilterItem(FilterGroup<T> filterGroup, Predicate<FilterItem<T>> matcher) {
        List<FilterItem<T>> items = filterGroup.filterItems();
        if (items == null) {
            return null;
        }
        return items.stream().filter(matcher).findFirst().orElse(null);
    }

    private SortItem<T> findSortItem(SortGroup<T> sortGroup, Predicate<SortItem<T>> matcher) {
        List<SortItem<T>> items = sortGroup.sortItems();
        if (items == null) {
            return null;
        }
        return items.stream().filter(matcher).findFirst().orElse(null);
    }

    private <S> ComboBox<S> createComboBox() {
        ComboBox<S> comboBox = new ComboBox<>();
        comboBox.setFocusTraversable(false);
        if (binding == null) {
            binding = Bindings.createBooleanBinding(comboBox::isShowing, comboBox.showingProperty());
        } else {
            binding = binding.or(Bindings.createBooleanBinding(comboBox::isShowing, comboBox.showingProperty()));
        }
        return comboBox;
    }

    private final BooleanProperty blocking = new SimpleBooleanProperty(this, "blocking");

    public boolean isBlocking() {
        return blocking.get();
    }

    public BooleanProperty blockingProperty() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking.set(blocking);
    }

    private final StringProperty searchPromptText = new SimpleStringProperty(this, "searchPromptText");

    public String getSearchPromptText() {
        return searchPromptText.get();
    }

    public StringProperty searchPromptTextProperty() {
        return searchPromptText;
    }

    public void setSearchPromptText(String searchPromptText) {
        this.searchPromptText.set(searchPromptText);
    }

    private final ObjectProperty<Function<String, Predicate<T>>> onSearch = new SimpleObjectProperty<>(this, "onSearch");

    public Function<String, Predicate<T>> getOnSearch() {
        return onSearch.get();
    }

    public ObjectProperty<Function<String, Predicate<T>>> onSearchProperty() {
        return onSearch;
    }

    public void setOnSearch(Function<String, Predicate<T>> onSearch) {
        this.onSearch.set(onSearch);
    }

    private final ListProperty<FilterGroup<T>> filterGroups = new SimpleListProperty<>(this, "filterGroups", FXCollections.observableArrayList());

    public ObservableList<FilterGroup<T>> getFilterGroups() {
        return filterGroups.get();
    }

    public ListProperty<FilterGroup<T>> filterGroupsProperty() {
        return filterGroups;
    }

    public void FilterGroup(ObservableList<FilterGroup<T>> filterGroups) {
        this.filterGroups.set(filterGroups);
    }

    private final ObjectProperty<SortGroup<T>> sortGroup = new SimpleObjectProperty<>(this, "sortGroups");

    public SortGroup<T> getSortGroup() {
        return sortGroup.get();
    }

    public ObjectProperty<SortGroup<T>> sortGroupProperty() {
        return sortGroup;
    }

    public void setSortGroup(SortGroup<T> sortGroup) {
        this.sortGroup.set(sortGroup);
    }

    private final ObjectProperty<Orientation> filterBoxOrientation = new StyleableObjectProperty<>(FILTER_BOX_DEFAULT_ORIENTATION) {

        @Override
        public Object getBean() {
            return SearchFilterView.this;
        }

        @Override
        public String getName() {
            return "filterBoxOrientation";
        }

        @Override
        public CssMetaData<SearchFilterView, Orientation> getCssMetaData() {
            return StyleableProperties.ORIENTATION;
        }
    };

    public final void setFilterBoxOrientation(Orientation value) {
        filterBoxOrientation.set(value);
    }

    public final Orientation getFilterBoxOrientation() {
        return filterBoxOrientation.get();
    }

    public final ObjectProperty<Orientation> filterBoxOrientationProperty() {
        return filterBoxOrientation;
    }

    private final ListProperty<Node> extraNodes = new SimpleListProperty<>(this, "extraNodes", FXCollections.observableArrayList());

    public ObservableList<Node> getExtraNodes() {
        return extraNodes.get();
    }

    public ListProperty<Node> extraNodesProperty() {
        return extraNodes;
    }

    public void setExtraNodes(ObservableList<Node> extraNodes) {
        this.extraNodes.set(extraNodes);
    }

    private static class StyleableProperties {

        private static final CssMetaData<SearchFilterView, Orientation> ORIENTATION = new CssMetaData<>("-fx-filter-box-orientation", new EnumConverter<>(Orientation.class), FILTER_BOX_DEFAULT_ORIENTATION) {

            @Override
            public boolean isSettable(SearchFilterView n) {
                return !n.filterBoxOrientation.isBound();
            }

            @Override
            public StyleableProperty<Orientation> getStyleableProperty(SearchFilterView n) {
                return (StyleableProperty<Orientation>) n.filterBoxOrientationProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(StackPane.getClassCssMetaData());
            styleables.add(ORIENTATION);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }
}
