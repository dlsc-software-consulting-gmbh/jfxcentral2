package com.dlsc.jfxcentral2.mobile.home;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableIntegerProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.SizeConverter;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryAdvancedView extends GridPane {

    private static final String DEFAULT_STYLE_CLASS = "category-tile-view";
    private static final int DEFAULT_MAX_COLUMNS = 2;

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public record Category(String title, String description, Node graphic, String url) {
    }

    public CategoryAdvancedView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setMaxHeight(Region.USE_PREF_SIZE);

        // add categories to the view;
        addCategoriesToView();

        // update view
        categoriesProperty().addListener((observable, oldValue, newValue) -> updateView());
        updateView();
    }

    private void addCategoriesToView() {
        getCategories().setAll(List.of(
                new Category("Tools",
                        "Delve into our selection of JavaFX tools, including plugins, layout and CSS tools, testing, packaging, and more.",
                        new FontIcon(IkonUtil.getModelIkon(Tool.class)),
                        PagePath.TOOLS),

                new Category("Videos",
                        "Browse JavaFX videos featuring conference talks, application demos, and tutorials, offering insights for all levels of JavaFX expertise.",
                        new FontIcon(IkonUtil.getModelIkon(Video.class)),
                        PagePath.VIDEOS),

                new Category("Books",
                        "Explore our JavaFX book selection, offering everything from beginner guides to advanced tutorials, including game development.",
                        new FontIcon(IkonUtil.getModelIkon(Book.class)),
                        PagePath.BOOKS),

                new Category("Tutorials",
                        "Browse tutorials ranging from JavaFX basics to advanced application and game development. Learn from experts and community members.",
                        new FontIcon(IkonUtil.getModelIkon(Tutorial.class)),
                        PagePath.TUTORIALS),

                new Category("Tips",
                        "Discover practical JavaFX tips in this section, featuring articles that share real-world techniques and tricks for effective JavaFX development.",
                        new FontIcon(IkonUtil.getModelIkon(Tip.class)),
                        PagePath.TIPS),

                new Category("Blog",
                        "Visit our blog section for articles from JavaFX experts, featuring practical tips, cutting-edge insights, and more from the world of JavaFX.",
                        new FontIcon(IkonUtil.getModelIkon(Blog.class)),
                        PagePath.BLOGS),

                new Category("Documentation",
                        "Access comprehensive resources including JavaFX API documentation, plugin guides, CSS references, and default style sheets.",
                        new FontIcon(IkonUtil.getModelIkon(Documentation.class)),
                        PagePath.DOCUMENTATION),

                new Category("Companies",
                        "Explore companies in JavaFX, especially those with significant influence and contributions to the JavaFX community, shaping and advancing the ecosystem.",
                        new FontIcon(IkonUtil.getModelIkon(Company.class)),
                        PagePath.COMPANIES)
        ));
    }

    private void updateView() {
        getChildren().clear();
        getColumnConstraints().clear();
        getRowConstraints().clear();

        if (categories != null) {
            int row = 0;
            int col = 0;
            int index = 0;
            for (Category category : categories) {
                CategoryTile tile = new CategoryTile();
                tile.getStyleClass().add("category-tile-" + index++);
                tile.setTitle(category.title());
                tile.setDescription(category.description());
                tile.setGraphic(category.graphic());
                // set link
                MobileLinkUtil.setLink(tile, category.url());

                add(tile, col, row);
                col++;
                if (col == getMaxColumns()) {
                    col = 0;
                    row++;
                }
            }
        }

        for (int i = 0; i < getMaxColumns(); i++) {
            getColumnConstraints().add(createColConstraints());
        }

        for (int i = 0; i < getRowCount(); i++) {
            getRowConstraints().add(createRowConstraints());
        }
    }

    private RowConstraints createRowConstraints() {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.CENTER);
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setPercentHeight(100.0 / getRowCount());
        return rowConstraints;
    }

    private ColumnConstraints createColConstraints() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setPercentWidth(100.0 / getMaxColumns());
        return columnConstraints;
    }

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    // Max columns per row.

    private IntegerProperty maxColumns;

    public final IntegerProperty maxColumnsProperty() {
        if (maxColumns == null) {
            maxColumns = new StyleableIntegerProperty(DEFAULT_MAX_COLUMNS) {

                @Override
                protected void invalidated() {
                    updateView();
                }

                @Override
                public Object getBean() {
                    return this;
                }

                @Override
                public String getName() {
                    return "maxColumns";
                }

                @Override
                public CssMetaData<? extends Styleable, Number> getCssMetaData() {
                    return StyleableProperties.MAX_COLUMNS;
                }
            };
        }
        return maxColumns;
    }

    public final int getMaxColumns() {
        return maxColumns == null ? DEFAULT_MAX_COLUMNS : maxColumns.get();
    }

    public final void setMaxColumns(int maxColumns) {
        maxColumnsProperty().set(maxColumns);
    }

    // categories

    private final ListProperty<Category> categories = new SimpleListProperty<>(this, "categories", FXCollections.observableArrayList());

    public final ListProperty<Category> categoriesProperty() {
        return categories;
    }

    public final void setCategories(ObservableList<Category> categories) {
        categoriesProperty().set(categories);
    }

    public final ObservableList<Category> getCategories() {
        return categoriesProperty().get();
    }

    private static class StyleableProperties {
        private static final CssMetaData<CategoryAdvancedView, Number> MAX_COLUMNS =
                new CssMetaData<>("-fx-max-columns", SizeConverter.getInstance(), DEFAULT_MAX_COLUMNS) {

                    @Override
                    public boolean isSettable(CategoryAdvancedView styleable) {
                        return styleable.maxColumns == null || !styleable.maxColumns.isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(CategoryAdvancedView styleable) {
                        return (StyleableProperty<Number>) styleable.maxColumnsProperty();
                    }
                };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(GridPane.getClassCssMetaData());
            styleables.add(MAX_COLUMNS);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    // CategoryTile

    private static class CategoryTile extends VBox {

        private static final String DEFAULT_STYLE_CLASS = "category-tile";
        private final StackPane graphicWrapper = new StackPane();

        public CategoryTile() {
            getStyleClass().add(DEFAULT_STYLE_CLASS);

            graphicWrapper.getStyleClass().add("graphic-wrapper");

            Label titleLabel = new Label();
            titleLabel.getStyleClass().add("title");
            titleLabel.textProperty().bind(titleProperty());

            Label descriptionLabel = new Label();
            descriptionLabel.getStyleClass().add("description");
            descriptionLabel.setWrapText(true);
            descriptionLabel.textProperty().bind(descriptionProperty());

            getChildren().addAll(graphicWrapper, titleLabel, descriptionLabel);
        }

        // tile graphic

        private final ObjectProperty<Node> graphic = new SimpleObjectProperty<>(this, "graphic") {

            @Override
            protected void invalidated() {
                Node graphic = get();
                if (graphic != null) {
                    graphicWrapper.getChildren().setAll(graphic);
                } else {
                    graphicWrapper.getChildren().clear();
                }
            }
        };

        public final ObjectProperty<Node> graphicProperty() {
            return graphic;
        }

        public final Node getGraphic() {
            return graphicProperty().get();
        }

        public final void setGraphic(Node graphic) {
            graphicProperty().set(graphic);
        }

        // tile title

        private final ObjectProperty<String> title = new SimpleObjectProperty<>(this, "title");

        public final ObjectProperty<String> titleProperty() {
            return title;
        }

        public final String getTitle() {
            return titleProperty().get();
        }

        public final void setTitle(String title) {
            titleProperty().set(title);
        }

        // tile description

        private final ObjectProperty<String> description = new SimpleObjectProperty<>(this, "description");

        public final ObjectProperty<String> descriptionProperty() {
            return description;
        }

        public final String getDescription() {
            return descriptionProperty().get();
        }

        public final void setDescription(String description) {
            descriptionProperty().set(description);
        }
    }

}
