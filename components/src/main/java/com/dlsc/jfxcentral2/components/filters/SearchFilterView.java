package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.gemsfx.SearchTextField;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SearchFilterView<T> extends PaneBase {

    private static final String DEFAULT_STYLE_CLASS = "search-filter-view";
    private static final Orientation FILTER_BOX_DEFAULT_ORIENTATION = Orientation.HORIZONTAL;
    private static final String WITH_SEARCH_FIELD = "with-search-field";
    private final SearchTextField searchField = new SearchTextField(true);
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

    public record FilterGroup<T>(String title, List<FilterItem<T>> filterItems) {
    }

    public record SortItem<T>(String name, Comparator<T> comparator, boolean isApplied) {
        public SortItem(String name, Comparator<T> comparator) {
            this(name, comparator, false);
        }
    }

    public record SortGroup<T>(String title, List<SortItem<T>> sortItems) {
    }

    public SearchFilterView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        searchField.promptTextProperty().bind(searchPromptTextProperty());
        searchField.managedProperty().bind(searchField.visibleProperty());
        searchField.visibleProperty().bind(onSearchProperty().isNotNull());

        filterBoxOrientation.addListener(it -> layoutBySize());
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

    private void setComparator(Comparator<T> comparator) {
        this.comparator.set(comparator);
    }

    private BooleanBinding binding;

    @Override
    protected void layoutBySize() {
        binding = null;

        Pane contentBox = isSmall() ? new VBox() : new HBox();
        contentBox.getStyleClass().add("content-box");
        contentBox.managedProperty().bind(contentBox.visibleProperty());

        Pane filtersBox = initFiltersSortGroupBox();
        Spacer spacer = new Spacer();
        spacer.managedProperty().bind(spacer.visibleProperty());

        contentBox.getChildren().setAll(searchField, filtersBox, spacer);
        contentBox.getChildren().addAll(getExtraNodes());

        if (isSmall() && getOnSearch() == null) {
            ToggleButton collapsibleButton = new ToggleButton("FILTERS", new FontIcon(JFXCentralIcon.CHEVRON_TOP));
            collapsibleButton.getStyleClass().add("collapsible-button");
            collapsibleButton.setMaxWidth(Double.MAX_VALUE);
            collapsibleButton.setSelected(true);
            contentBox.visibleProperty().bind(collapsibleButton.selectedProperty());
            VBox contentBoxWrapper = new VBox(collapsibleButton, contentBox);
            contentBoxWrapper.getStyleClass().add("content-box-wrapper");
            getChildren().setAll(contentBoxWrapper);
        } else {
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

        Pane filtersBox = isSmall() ? new VBox() : new HBox();
        filtersBox.getStyleClass().add("filters-box");
        ObservableList<FilterGroup<T>> items = getFilterGroups();
        List<ObjectProperty<Predicate<T>>> childPredicateProperties = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            FilterGroup<T> filterGroup = items.get(i);
            ObjectProperty<Predicate<T>> childPredicateProperty = new SimpleObjectProperty<>(this, "childPredicate", item -> true);
            filtersBox.getChildren().add(createFilterBox(i, filterGroup, childPredicateProperty));
            childPredicateProperties.add(childPredicateProperty);
        }

        Stream.Builder<Observable> builder = Stream.builder();
        builder.add(onSearchProperty());
        builder.add(searchField.textProperty());
        childPredicateProperties.forEach(builder::add);

        predicate.bind(Bindings.createObjectBinding(() -> {
            Predicate<T> predicate = item -> true;
            if (getOnSearch() != null) {
                predicate = predicate.and(getOnSearch().apply(searchField.getText()));
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
            ComboBox<SortItem<T>> sortComboBox = new ComboBox<>();
            sortComboBox.getStyleClass().addAll("filter-combo-box", "sort-combo-box");
            sortComboBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(SortItem<T> object) {
                    return object == null ? null : object.name();
                }

                @Override
                public SortItem<T> fromString(String string) {
                    return null;
                }
            });
            sortComboBox.getItems().setAll(getSortGroup().sortItems());
            comparator.bind(sortComboBox.getSelectionModel().selectedItemProperty().map(SortItem::comparator));
            getSortGroup().sortItems().stream().filter(it -> it.isApplied).findFirst()
                    .ifPresentOrElse(
                            it -> sortComboBox.getSelectionModel().select(it),
                            () -> sortComboBox.getSelectionModel().selectFirst()
                    );

            Pane box = getFilterBoxOrientation() == Orientation.VERTICAL ? new VBox() : new HBox();
            box.getStyleClass().addAll("filter-box", "sort-box");
            Label titleLabel = new Label(getSortGroup().title);
            titleLabel.setMinWidth(Region.USE_PREF_SIZE);
            titleLabel.getStyleClass().add("filter-title");
            if (isSmall()) {
                box.getChildren().setAll(titleLabel, new Spacer(), sortComboBox);
            } else {
                box.getChildren().setAll(titleLabel, sortComboBox);
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

        Node comboBoxNode = createSingleSelection(filterGroup, childPredicateProperty);
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
    private Node createSingleSelection(FilterGroup<T> filterGroup, ObjectProperty<Predicate<T>> childPredicateProperty) {
        ComboBox<FilterItem<T>> comboBox = createSingleSelection();
        comboBox.setConverter(predicateItemStringConverter);
        comboBox.getStyleClass().addAll("filter-combo-box");
        comboBox.getItems().addAll(filterGroup.filterItems);

        childPredicateProperty.bind(comboBox.getSelectionModel().selectedItemProperty().map(it -> it == null ? item -> true : it.predicate));
        filterGroup.filterItems.stream()
                .filter(FilterItem::isApplied).findFirst()
                .ifPresentOrElse(it -> comboBox.getSelectionModel().select(it), () -> comboBox.getSelectionModel().selectFirst());

        return comboBox;
    }

    private <T> ComboBox<T> createSingleSelection() {
        ComboBox<T> comboBox = new ComboBox<>();
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
