package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.SearchField;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListWrapper;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class SearchFilterView extends PaneBase {
    private static final String DEFAULT_STYLE_CLASS = "search-filter-view";
    private static final Orientation FILTER_BOX_DEFAULT_ORIENTATION = Orientation.HORIZONTAL;
    private static final String WITH_SEARCH_FIELD = "with-search-field";

    public record FilterItem<E extends Enum<E>>(String title, Class<E> filterEnumClass, Enum<E> defaultValue) {
    }

    public SearchFilterView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

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
    }

    @Override
    protected void layoutBySize() {
        Pane contentBox = isSmall() ? new VBox() : new HBox();
        contentBox.getStyleClass().add("content-box");
        contentBox.managedProperty().bind(contentBox.visibleProperty());

        SearchField<String> searchField = new SearchField<>();
        searchField.promptTextProperty().bind(searchPromptTextProperty());
        searchField.managedProperty().bind(searchField.visibleProperty());
        searchField.visibleProperty().bind(onSearchProperty().isNotNull());
        searchField.setOnSearchStarted(evt -> {
            if (onSearch.get() != null) {
                onSearch.get().accept(searchField.getText(), getSelectedFilters());
            }
        });

        Button applyFiltersButton = new Button("APPLY FILTERS");
        applyFiltersButton.getStyleClass().addAll("apply-filters-button", "blue-button");
        applyFiltersButton.managedProperty().bind(applyFiltersButton.visibleProperty());
        applyFiltersButton.visibleProperty().bind(filterItemsProperty().sizeProperty().greaterThan(1));
        applyFiltersButton.setOnAction(evt -> {
            if (onApplyFilters.get() != null) {
                onApplyFilters.get().run();
            }
        });

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

        contentBox.getChildren().setAll(searchField, filtersBox, spacer, applyFiltersButton);
        contentBox.getChildren().addAll(getExtraNodes());

        if (isSmall() && getOnSearch() == null) {
            ToggleButton collapsibleButton = new ToggleButton("FILTERS", new FontIcon(Material.EXPAND_MORE));
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
    }

    private Node createFilterBox(int index, FilterItem<?> filterItem) {
        Pane box = getFilterBoxOrientation() == Orientation.VERTICAL ? new VBox() : new HBox();
        box.getStyleClass().addAll("filter-box", "filter-box-" + index);

        Label titleLabel = new Label(filterItem.title());
        titleLabel.getStyleClass().add("filter-title");

        Object[] enumConstants = filterItem.filterEnumClass().getEnumConstants();
        ComboBox<Enum<?>> comboBox = new ComboBox<>();
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
        comboBox.getItems().addAll((Enum<?>[]) enumConstants);
        if (filterItem.defaultValue != null) {
            comboBox.getSelectionModel().select(filterItem.defaultValue);
        } else {
            comboBox.getSelectionModel().selectFirst();
        }
        comboBox.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> {
            if (nv != null) {
                updateSelectedFilter(index, nv);
            }
        });

        if (isSmall()) {
            box.getChildren().setAll(titleLabel, new Spacer(), comboBox);
        } else {
            box.getChildren().setAll(titleLabel, comboBox);
        }
        return box;
    }

    private void updateSelectedFilter(int index, Enum<?> newFilterValue) {
        List<Enum> updatedFilters = new ArrayList<>(getSelectedFilters());
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
            return SearchFilterView.StyleableProperties.ORIENTATION;
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

    private final ReadOnlyListWrapper<Enum> selectedFilters = new ReadOnlyListWrapper<>(this, "selectedFilters", FXCollections.observableArrayList());

    public List<Enum> getSelectedFilters() {
        return Collections.unmodifiableList(selectedFilters.get());
    }

    public ReadOnlyListWrapper<Enum> selectedFiltersProperty() {
        return selectedFilters;
    }

    private void setSelectedFilters(ObservableList<Enum> selectedFilters) {
        this.selectedFilters.set(selectedFilters);
    }

    private final ObjectProperty<Runnable> onApplyFilters = new SimpleObjectProperty<>(this, "onApplyFilters");

    public Runnable getOnApplyFilters() {
        return onApplyFilters.get();
    }

    public ObjectProperty<Runnable> onApplyFiltersProperty() {
        return onApplyFilters;
    }

    public void setOnApplyFilters(Runnable onApplyFilters) {
        this.onApplyFilters.set(onApplyFilters);
    }

    private final ObjectProperty<BiConsumer<String, List<Enum>>> onSearch = new SimpleObjectProperty<>(this, "onSearch");

    public BiConsumer<String, List<Enum>> getOnSearch() {
        return onSearch.get();
    }

    public ObjectProperty<BiConsumer<String, List<Enum>>> onSearchProperty() {
        return onSearch;
    }

    public void setOnSearch(BiConsumer<String, List<Enum>> onSearch) {
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
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(PaneBase.getClassCssMetaData());
            styleables.add(ORIENTATION);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return SearchFilterView.StyleableProperties.STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }
}
