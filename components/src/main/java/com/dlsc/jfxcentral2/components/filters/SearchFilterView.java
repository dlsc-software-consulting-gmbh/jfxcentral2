package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.gemsfx.SearchTextField;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.IndexedCheckModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class SearchFilterView<T> extends PaneBase {

    private static final String DEFAULT_STYLE_CLASS = "search-filter-view";
    private static final Orientation FILTER_BOX_DEFAULT_ORIENTATION = Orientation.HORIZONTAL;
    private static final String WITH_SEARCH_FIELD = "with-search-field";

    private final SearchTextField searchField = new SearchTextField(true);

    public record FilterItem<E extends Enum<E>>(String title, Class<E> filterEnumClass, Enum<E> defaultValue,
                                                boolean multipleSelection) {
        public FilterItem(String title, Class<E> filterEnumClass, Enum<E> defaultValue) {
            this(title, filterEnumClass, defaultValue, false);
        }
    }

    public SearchFilterView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        searchField.promptTextProperty().bind(searchPromptTextProperty());
        searchField.managedProperty().bind(searchField.visibleProperty());
        searchField.visibleProperty().bind(onSearchProperty().isNotNull());

        filterBoxOrientation.addListener(it -> layoutBySize());
        filterItemsProperty().addListener((InvalidationListener) it -> layoutBySize());
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

        InvalidationListener updateFilter = it -> {
            if (getOnSearch() != null) {
                getOnSearch().accept(searchField.getText(), getSelectedFilters());
            }
        };

        selectedFilters.addListener(updateFilter);
        searchField.textProperty().addListener(updateFilter);
    }

    private final ObjectProperty<Predicate<? extends T>> predicate = new SimpleObjectProperty<>(this, "predicate", item -> true);

    public Predicate<? extends T> getPredicate() {
        return predicate.get();
    }

    public ObjectProperty<Predicate<? extends T>> predicateProperty() {
        return predicate;
    }

    public void setPredicate(Predicate<? extends T> predicate) {
        this.predicate.set(predicate);
    }

    private BooleanBinding binding;

    @Override
    protected void layoutBySize() {
        binding = null;

        Pane contentBox = isSmall() ? new VBox() : new HBox();
        contentBox.getStyleClass().add("content-box");
        contentBox.managedProperty().bind(contentBox.visibleProperty());

        Pane filtersBox = isSmall() ? new VBox() : new HBox();
        filtersBox.getStyleClass().add("filters-box");
        ObservableList<FilterItem> items = getFilterItems();
        for (int i = 0; i < items.size(); i++) {
            FilterItem filterItem = items.get(i);
            filtersBox.getChildren().add(createFilterBox(i, filterItem));
        }

        selectedFilters.setAll(getFilterItems().stream().map(filterItem -> filterItem.defaultValue).toList());

        Spacer spacer = new Spacer();
        spacer.managedProperty().bind(spacer.visibleProperty());

        contentBox.getChildren().setAll(searchField, filtersBox, spacer); //, applyFiltersButton);
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

    private Node createFilterBox(int index, FilterItem<?> filterItem) {
        Pane box = getFilterBoxOrientation() == Orientation.VERTICAL ? new VBox() : new HBox();
        box.getStyleClass().addAll("filter-box", "filter-box-" + index);

        Label titleLabel = new Label(filterItem.title());
        titleLabel.setMinWidth(Region.USE_PREF_SIZE);
        titleLabel.getStyleClass().add("filter-title");

        Object[] enumConstants = filterItem.filterEnumClass().getEnumConstants();
        Node comboBoxNode;
        // MULTIPLE SELECTION
        if (filterItem.multipleSelection) {
            comboBoxNode = createMultipleSelectionNode(index,filterItem, (Enum<?>[]) enumConstants);
        } else {
            // SINGLE SELECTION
            comboBoxNode = createSingleSelection(index, filterItem, (Enum<?>[]) enumConstants);
        }

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
    private Node createSingleSelection(int index, FilterItem<?> filterItem, Enum<?>[] enumConstants) {
        ComboBox<Enum<?>> comboBox = createSingleSelection();
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Enum<?> object) {
                return object.toString();
            }

            @Override
            public Enum<?> fromString(String string) {
                return null;
            }
        });
        comboBox.getStyleClass().addAll("filter-combo-box");
        comboBox.getItems().addAll(enumConstants);
        if (filterItem.defaultValue != null) {
            comboBox.getSelectionModel().select(filterItem.defaultValue);
        } else {
            comboBox.getSelectionModel().selectFirst();
        }
        comboBox.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> {
            updateSelectedFilter(index, nv != null ? List.of(nv) : List.of());
        });
        return comboBox;
    }

    /**
     * CheckComboBox: multiple selection
     */
    private Node createMultipleSelectionNode(int index,FilterItem<?> filterItem, Enum<?>[] enumConstants) {
        CheckComboBox<Enum<?>> checkComboBox = new CheckComboBox<>();
        checkComboBox.getStyleClass().addAll("check-combo-box");
        checkComboBox.getItems().addAll(enumConstants);
        IndexedCheckModel<Enum<?>> checkModel = checkComboBox.getCheckModel();
        checkComboBox.titleProperty().bind(Bindings.createStringBinding(() -> {
            ObservableList<Enum<?>> items = checkModel.getCheckedItems();
            if (items.isEmpty()) {
                return "Please Select packs";
            } else if (items.size() == checkComboBox.getItems().size()) {
                return "All packs selected";
            } else if (items.size() == 1) {
                return items.get(0).toString();
            } else {
                return items.size() + " packs- selected";
            }
        }, checkModel.getCheckedItems()));

        checkComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<>() {
            // prevent recursive calls
            private boolean updating = false;

            @Override
            public void onChanged(Change<? extends Enum<?>> c) {
                if (updating) {
                    return;
                }

                ObservableList<Enum<?>> checkedItems = checkComboBox.getCheckModel().getCheckedItems();
                updateSelectedFilter(index, new ArrayList<>(checkedItems));
                // Tip: If we want to support select all,
                // then the first element of the enumeration class needs to be ALL
                Enum<?> firstItem = checkComboBox.getCheckModel().getItem(0);
                if (c.next()) {
                    if (c.wasAdded()) {
                        if (c.getAddedSubList().contains(firstItem)) {
                            updating = true;
                            checkComboBox.getCheckModel().checkAll();
                            updating = false;
                        }
                    } else if (c.wasRemoved()) {
                        if (c.getRemoved().contains(firstItem)) {
                            updating = true;
                            checkComboBox.getCheckModel().clearChecks();
                            updating = false;
                        } else {
                            updating = true;
                            checkComboBox.getCheckModel().clearCheck(firstItem);
                            updating = false;
                        }
                    }
                }
            }
        });

        if (filterItem.defaultValue != null) {
            checkComboBox.getCheckModel().check(filterItem.defaultValue);
        }

        return checkComboBox;
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

    private void updateSelectedFilter(int index, List<Enum> newFilterValue) {
        List<List<Enum>> updatedFilters = new ArrayList<>(getSelectedFilters());
        if (index >= 0 && index < updatedFilters.size()) {
            updatedFilters.set(index, newFilterValue);
        } else {
            updatedFilters.add(newFilterValue);
        }
        if (!updatedFilters.equals(getSelectedFilters())) {
            setSelectedFilters(FXCollections.observableArrayList(updatedFilters));
        }
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

    private final ListProperty<FilterItem> filterItems = new SimpleListProperty<>(this, "filterItems", FXCollections.observableArrayList());

    public ObservableList<FilterItem> getFilterItems() {
        return filterItems.get();
    }

    public ListProperty<FilterItem> filterItemsProperty() {
        return filterItems;
    }

    public void setFilterItems(ObservableList<FilterItem> filterItems) {
        this.filterItems.set(filterItems);
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

    private final ReadOnlyListWrapper<List<Enum>> selectedFilters = new ReadOnlyListWrapper<>(this, "selectedFilters", FXCollections.observableArrayList());

    public List<List<Enum>> getSelectedFilters() {
        return Collections.unmodifiableList(selectedFilters.get());
    }

    public ReadOnlyListWrapper<List<Enum>> selectedFiltersProperty() {
        return selectedFilters;
    }

    private void setSelectedFilters(ObservableList<List<Enum>> selectedFilters) {
        this.selectedFilters.set(selectedFilters);
    }

//    private final ObjectProperty<Runnable> onApplyFilters = new SimpleObjectProperty<>(this, "onApplyFilters");
//
//    public Runnable getOnApplyFilters() {
//        return onApplyFilters.get();
//    }
//
//    public ObjectProperty<Runnable> onApplyFiltersProperty() {
//        return onApplyFilters;
//    }
//
//    public void setOnApplyFilters(Runnable onApplyFilters) {
//        this.onApplyFilters.set(onApplyFilters);
//    }

    private final ObjectProperty<BiConsumer<String, List<List<Enum>>>> onSearch = new SimpleObjectProperty<>(this, "onSearch");

    public BiConsumer<String, List<List<Enum>>> getOnSearch() {
        return onSearch.get();
    }

    public ObjectProperty<BiConsumer<String, List<List<Enum>>>> onSearchProperty() {
        return onSearch;
    }

    public void setOnSearch(BiConsumer<String, List<List<Enum>>> onSearch) {
        this.onSearch.set(onSearch);
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
