package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.SearchField;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.EnumConverter;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopMenuBar extends PaneBase {

    private static final Mode DEFAULT_MODE = Mode.DARK;

    private static final PseudoClass LIGHT_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("light");
    private static final PseudoClass DARK_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("dark");

    private final CustomImageView dukeView;
    private final SearchField<String> searchField;
    private final HBox contentBox;

    private Node searchTextField;

    public enum Mode {
        LIGHT,
        DARK
    }

    public TopMenuBar() {
        getStyleClass().add("top-menu-bar");

        activateModePseudoClass();
        modeProperty().addListener(it -> activateModePseudoClass());

        contentBox = new HBox();
        contentBox.getStyleClass().add("content");
        getChildren().add(contentBox);

        dukeView = new CustomImageView();
        dukeView.getStyleClass().add("duke-image");

        searchField = new SearchField<>();
        searchField.setPromptText("Search");

        layoutBySize();
    }

    private final BooleanProperty used = new SimpleBooleanProperty(this, "used");

    public boolean isUsed() {
        return used.get();
    }

    public BooleanProperty usedProperty() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used.set(used);
    }

    private void activateModePseudoClass() {
        Mode mode = getMode();
        pseudoClassStateChanged(LIGHT_PSEUDOCLASS_STATE, mode == Mode.LIGHT);
        pseudoClassStateChanged(DARK_PSEUDOCLASS_STATE, mode == Mode.DARK);
    }

    private BooleanBinding blocking;

    protected void layoutBySize() {
        if (isLarge()) {
            MenuButton resourcesBtn = createMenuButton("Resources");
            resourcesBtn.getStyleClass().add("resources-button");

            MenuButton communityBtn = createMenuButton("Community");
            communityBtn.getStyleClass().add("community-button");

            fillResourcesMenu(resourcesBtn);
            fillCommunityMenu(communityBtn);

            Button showcasesBtn = new Button("Showcases");
            showcasesBtn.setMinWidth(Region.USE_PREF_SIZE);
            showcasesBtn.getStyleClass().add("showcases-button");
            LinkUtil.setLink(showcasesBtn, "/showcases");

            Button downloadsBtn = new Button("Downloads");
            downloadsBtn.setMinWidth(Region.USE_PREF_SIZE);
            downloadsBtn.getStyleClass().add("downloads-button");
            LinkUtil.setLink(downloadsBtn, "/downloads");

            Button loginBtn = new Button("Login", new FontIcon(MaterialDesignA.ACCOUNT_CIRCLE_OUTLINE));
            loginBtn.setMinWidth(Region.USE_PREF_SIZE);
            loginBtn.getStyleClass().add("login-button");

            searchField.setVisible(true);
            searchField.setMinWidth(Region.USE_PREF_SIZE);
            contentBox.getChildren().setAll(createLogo(), new Spacer(), resourcesBtn, communityBtn, showcasesBtn, downloadsBtn, new Spacer(), loginBtn, searchField);
        } else {
            Region logoutRegion = new Region();
            logoutRegion.getStyleClass().add("logout-region");

            Button logOutBtn = new Button(null, logoutRegion);
            logOutBtn.getStyleClass().add("logout-button");

            Region searchRegion = new Region();
            searchRegion.getStyleClass().add("search-region");

            Button searchBtn = new Button(null, searchRegion);
            StackPane stackPane = new StackPane(searchField, searchBtn);
            stackPane.getStyleClass().add("search-stack-pane");

            searchField.managedProperty().bind(searchField.visibleProperty());
            searchBtn.managedProperty().bind(searchBtn.visibleProperty());
            searchBtn.visibleProperty().bind(searchField.visibleProperty().not());
            searchField.setVisible(false);

            searchBtn.setOnAction(event -> {
                searchField.setVisible(true);
                Platform.runLater(() -> getSearchTextField().requestFocus());
            });
            searchBtn.getStyleClass().add("search-button");

            MenuButton menuBtn = createMenuButton("Menu");
            menuBtn.getStyleClass().add("top-menu-button");

            contentBox.getChildren().setAll(createLogo(), new Spacer(), logOutBtn, createSeparatorRegion(), stackPane, createSeparatorRegion(), menuBtn);
        }

        usedProperty().bind(blocking);
    }

    private void fillResourcesMenu(MenuButton button) {
        button.getItems().add(createMenuItem("Libraries", "/libraries", IkonUtil.getModelIkon(Library.class)));
        button.getItems().add(createMenuItem("Tools", "/tools", IkonUtil.getModelIkon(Tool.class)));
        button.getItems().add(createMenuItem("Videos", "/videos", IkonUtil.getModelIkon(Video.class)));
        button.getItems().add(createMenuItem("Books", "/books", IkonUtil.getModelIkon(Book.class)));
        button.getItems().add(createMenuItem("Blogs", "/blogs", IkonUtil.getModelIkon(Blog.class)));
        button.getItems().add(createMenuItem("Tutorials", "/tutorials", IkonUtil.getModelIkon(Tutorial.class)));
    }

    private void fillCommunityMenu(MenuButton button) {
        button.getItems().add(createMenuItem("People", "/people", IkonUtil.getModelIkon(Person.class)));
        button.getItems().add(createMenuItem("Companies", "/companies", IkonUtil.getModelIkon(Company.class)));
        button.getItems().add(createMenuItem("OpenJFX", "/openjfx", null));
        button.getItems().add(createMenuItem("Links of the Week", "/links", IkonUtil.getModelIkon(LinksOfTheWeek.class)));
    }

    private MenuItem createMenuItem(String text, String url, Ikon ikon) {
        Label label = new Label(text, ikon != null ? new FontIcon(ikon) : null);
        // The StackPane is a workaround for the setLink.
        // setLink currently only works when the Parent is a Pane.
        StackPane wrapper = new StackPane(label);
        LinkUtil.setLink(label, url);
        return new CustomMenuItem(wrapper);
    }

    private MenuButton createMenuButton(String text) {
        MenuButton menuButton = new MenuButton(text);
        menuButton.setMinWidth(Region.USE_PREF_SIZE);
        if (blocking == null) {
            blocking = Bindings.createBooleanBinding(menuButton::isShowing, menuButton.showingProperty());
        } else {
            blocking = blocking.or(Bindings.createBooleanBinding(menuButton::isShowing, menuButton.showingProperty()));
        }
        return menuButton;
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
            LinkUtil.setLink(logoBox, "/", "Back to homepage");
            return logoBox;
        }
    }

    private final StyleableObjectProperty<Mode> mode = new StyleableObjectProperty<>(DEFAULT_MODE) {

        @Override
        public Object getBean() {
            return TopMenuBar.this;
        }

        @Override
        public String getName() {
            return "mode";
        }

        @Override
        public CssMetaData<? extends Styleable, Mode> getCssMetaData() {
            return StyleableProperties.MODE;
        }
    };

    public final StyleableObjectProperty<Mode> modeProperty() {
        return mode;
    }

    public final Mode getMode() {
        return mode.get();
    }

    public final void setMode(Mode mode) {
        modeProperty().set(mode);
    }

    private static class StyleableProperties {

        private static final CssMetaData<TopMenuBar, Mode> MODE = new CssMetaData<>("-fx-mode", new EnumConverter<>(Mode.class), DEFAULT_MODE) {

            @Override
            public boolean isSettable(TopMenuBar control) {
                return !control.mode.isBound();
            }

            @Override
            public StyleableProperty<Mode> getStyleableProperty(TopMenuBar control) {
                return control.modeProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(StackPane.getClassCssMetaData());
            styleables.add(MODE);
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
