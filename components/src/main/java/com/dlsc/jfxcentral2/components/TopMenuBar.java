package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.SearchField;
import javafx.application.Platform;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.EnumConverter;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopMenuBar extends PaneBase {
    private static final BackgroundMode DEFAULT_BACKGROUND_MODE = BackgroundMode.DARK;
    private static final PseudoClass LIGHT_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("light");
    private static final PseudoClass DARK_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("dark");
    private final CustomImageView dukeView;
    private final SearchField<String> searchField;
    private final HBox contentBox;
    private Node searchTextField;

    public enum BackgroundMode {
        LIGHT,
        DARK
    }

    public TopMenuBar() {
        getStyleClass().add("top-menu-bar");

        activateBackgroundModePseudoClass();
        backgroundModeProperty().addListener(it -> activateBackgroundModePseudoClass());

        contentBox = new HBox();
        contentBox.getStyleClass().add("content");
        getChildren().add(contentBox);

        dukeView = new CustomImageView();
        dukeView.getStyleClass().add("duke-image");

        createSpacingRegion();
        searchField = new SearchField<>();
        searchField.setPromptText("Search");
        layoutBySize();
    }

    private void activateBackgroundModePseudoClass() {
        BackgroundMode mode = getBackgroundMode();
        pseudoClassStateChanged(LIGHT_PSEUDOCLASS_STATE, mode == BackgroundMode.LIGHT);
        pseudoClassStateChanged(DARK_PSEUDOCLASS_STATE, mode == BackgroundMode.DARK);
    }

    private Region createSpacingRegion() {
        Region spacingRegion = new Region();
        HBox.setHgrow(spacingRegion, Priority.ALWAYS);
        return spacingRegion;
    }

    protected void layoutBySize() {
        if (isLarge()) {
            MenuButton resourcesBtn = new MenuButton("Resources");
            resourcesBtn.getStyleClass().add("resources-button");

            MenuButton communityBtn = new MenuButton("Community");
            communityBtn.getStyleClass().add("community-button");

            Button showcasesBtn = new Button("Showcases");
            showcasesBtn.getStyleClass().add("sh owcases-button");

            Button downloadsBtn = new Button("Downloads");
            downloadsBtn.getStyleClass().add("downloads-button");

            Button loginBtn = new Button("Login", new FontIcon(MaterialDesignA.ACCOUNT_CIRCLE_OUTLINE));
            loginBtn.getStyleClass().add("login-button");

            searchField.setVisible(true);
            contentBox.getChildren().setAll(createLogo(), createSpacingRegion(), resourcesBtn, communityBtn, showcasesBtn, downloadsBtn, createSeparatorRegion(), loginBtn, searchField);
        } else {
            Region logoutRegion = new Region();
            logoutRegion.getStyleClass().add("logout-region");

            Button logOutBtn = new Button(null, logoutRegion);
            logOutBtn.getStyleClass().add("logout-button");

            Region searchRegion = new Region();
            searchRegion.getStyleClass().add("search-region");

            Button searchBtn = new Button(null, searchRegion);
            StackPane stackPane = new StackPane(searchField, searchBtn);

            searchField.managedProperty().bind(searchField.visibleProperty());
            searchBtn.managedProperty().bind(searchBtn.visibleProperty());
            searchBtn.visibleProperty().bind(searchField.visibleProperty().not());
            searchField.setVisible(false);

            searchBtn.setOnAction(event -> {
                searchField.setVisible(true);
                Platform.runLater(() -> getSearchTextField().requestFocus());
            });
            searchBtn.getStyleClass().add("search-button");

            MenuButton menuBtn = new MenuButton("Menu");
            menuBtn.getStyleClass().add("top-menu-button");

            contentBox.getChildren().setAll(createLogo(), createSpacingRegion(), logOutBtn, createSeparatorRegion(), stackPane, createSeparatorRegion(), menuBtn);
        }
    }

    private Node getSearchTextField() {
        if (searchTextField == null) {
            searchTextField = searchField.lookup(".text-field");
            searchTextField.focusedProperty().addListener((ob, ov, nv) -> {
                if (!nv) {
                    searchField.setVisible(false);
                }
            });
        }
        return searchTextField;
    }

    private Region createSeparatorRegion() {
        Region separator = new Region();
        separator.getStyleClass().add("separator-region");
        return separator;
    }

    private Node createLogo() {
        if (isSmall()) {
            return dukeView;
        } else {
            Region jfxcentralRegion = new Region();
            jfxcentralRegion.getStyleClass().add("jfxcentral-region");
            HBox logoBox = new HBox(dukeView, jfxcentralRegion);
            logoBox.getStyleClass().add("logo-box");
            return logoBox;
        }
    }

    private final StyleableObjectProperty<BackgroundMode> backgroundMode = new StyleableObjectProperty<>(DEFAULT_BACKGROUND_MODE) {
        @Override
        public Object getBean() {
            return TopMenuBar.this;
        }

        @Override
        public String getName() {
            return "backgroundMode";
        }

        @Override
        public CssMetaData<? extends Styleable, BackgroundMode> getCssMetaData() {
            return StyleableProperties.BACKGROUND_MODE;
        }
    };

    public final StyleableObjectProperty<BackgroundMode> backgroundModeProperty() {
        return backgroundMode;
    }

    public final BackgroundMode getBackgroundMode() {
        return backgroundMode.get();
    }

    public final void setBackgroundMode(final BackgroundMode backgroundMode) {
        this.backgroundModeProperty().set(backgroundMode);
    }

    private static class StyleableProperties {

        private static final CssMetaData<TopMenuBar, BackgroundMode> BACKGROUND_MODE = new CssMetaData<>(
                "-fx-background-mode", new EnumConverter<>(BackgroundMode.class), DEFAULT_BACKGROUND_MODE) {

            @Override
            public boolean isSettable(TopMenuBar control) {
                return !control.backgroundMode.isBound();
            }

            @Override
            public StyleableProperty<BackgroundMode> getStyleableProperty(TopMenuBar control) {
                return control.backgroundModeProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(PaneBase.getClassCssMetaData());
            styleables.add(BACKGROUND_MODE);
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

}
