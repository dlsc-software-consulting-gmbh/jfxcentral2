package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.SearchField;
import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import com.dlsc.jfxcentral2.utils.SocialUtil;
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
import javafx.util.StringConverter;
import one.jpro.routing.LinkUtil;
import one.jpro.routing.View;
import one.jpro.routing.sessionmanager.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopMenuBar extends PaneBase {
    private static final Logger LOGGER = LogManager.getLogger(TopMenuBar.class);
    private static final Mode DEFAULT_MODE = Mode.DARK;

    private static final PseudoClass LIGHT_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("light");
    private static final PseudoClass DARK_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("dark");

    private final CustomImageView jfxCentralLogoView;
    private final View view;
    private SearchField<ModelObject> searchField;
    private final HBox contentBox;
    private final StackPane logoWrapper;
    private Node searchTextField;
    private SessionManager sessionManager;

    public TopMenuBar(View view) {
        this.view = view;

        getStyleClass().add("top-menu-bar");

        activateModePseudoClass();
        modeProperty().addListener(it -> activateModePseudoClass());

        contentBox = new HBox();
        contentBox.getStyleClass().add("content");
        getChildren().add(contentBox);

        jfxCentralLogoView = new CustomImageView();
        jfxCentralLogoView.setPreserveRatio(true);
        jfxCentralLogoView.getStyleClass().addAll("jfx-central-logo", "small");

        modeProperty().addListener(it -> {
            if (getMode().equals(Mode.LIGHT)) {
                jfxCentralLogoView.getStyleClass().add("black");
            }
        });

        // wrap logo to make it completely clickable
        logoWrapper = new StackPane(jfxCentralLogoView);
        LinkUtil.setLink(logoWrapper, "/", "Back to homepage");

        layoutBySize();

        sceneProperty().addListener(it -> {
            if (getScene() != null && getStyleClass().contains("start-page")) {
                jfxCentralLogoView.getStyleClass().add("color");
            }
        });
    }

    private SearchField<ModelObject> createSearchField() {
        SearchField<ModelObject> searchField = new SearchField<>();
        searchField.setOnCommit(selectedItem -> {
            if (selectedItem != null) {
                getSessionManager().gotoURL(ModelObjectTool.getModelLink(selectedItem));
            }
        });

        searchField.getPopup().setPrefWidth(600);
        searchField.getEditor().setFocusTraversable(false);
        searchField.setPromptText("Search");
        searchField.setCellFactory(listView -> new SearchResultCell());
        searchField.setSuggestionProvider(request -> search(request.getUserText()));
        searchField.setMatcher((modelObject, text) -> modelObject.getName().startsWith(text));
        searchField.setConverter(new StringConverter<>() {
            @Override
            public String toString(ModelObject object) {
                if (object != null) {
                    return object.getName();
                }
                return "";
            }

            @Override
            public ModelObject fromString(String string) {
                return null;
            }
        });
        return searchField;
    }

    private SessionManager getSessionManager() {
        if (sessionManager == null) {
            sessionManager = LinkUtil.getSessionManager(view.realContent());
            if (sessionManager == null) {
                LOGGER.error("Failed to initialize SessionManager.");
                throw new IllegalStateException("Failed to initialize SessionManager.");
            }
        }
        return sessionManager;
    }

    public List<ModelObject> search(String pattern) {
        DataRepository2 repository = DataRepository2.getInstance();

        List<ModelObject> results = new ArrayList<>();
        search(repository.getBooks(), pattern, results);
        search(repository.getBlogs(), pattern, results);
        search(repository.getCompanies(), pattern, results);
        search(repository.getPeople(), pattern, results);
        search(repository.getLibraries(), pattern, results);
        search(repository.getRealWorldApps(), pattern, results);
        search(repository.getTools(), pattern, results);
        search(repository.getVideos(), pattern, results);
        search(repository.getNews(), pattern, results);
        search(repository.getDownloads(), pattern, results);
        search(repository.getTutorials(), pattern, results);
        search(repository.getTips(), pattern, results);
        search(repository.getIkonliPacks(), pattern, results);

        return results;
    }

    private void search(List<? extends ModelObject> modelObjects, String pattern, List<ModelObject> result) {
        modelObjects.forEach(mo -> {
            if (mo.matches(pattern)) {
                result.add(mo);
            }
        });
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
        searchField = createSearchField();
        searchField.setFocusTraversable(false);
        if (isLarge()) {
            MenuButton resourcesBtn = createMenuButton("Resources");
            resourcesBtn.getStyleClass().add("resources-button");

            MenuButton communityBtn = createMenuButton("Community");
            communityBtn.getStyleClass().add("community-button");

            fillResourcesMenu(resourcesBtn);
            fillCommunityMenu(communityBtn);

            Button showcasesBtn = new Button("Showcases");
            showcasesBtn.setFocusTraversable(false);
            showcasesBtn.setMinWidth(Region.USE_PREF_SIZE);
            showcasesBtn.getStyleClass().add("showcases-button");
            LinkUtil.setLink(showcasesBtn, "/showcases");

            Button documentationBtn = new Button("Documentation");
            documentationBtn.setFocusTraversable(false);
            documentationBtn.setMinWidth(Region.USE_PREF_SIZE);
            documentationBtn.getStyleClass().add("docs-button");
            LinkUtil.setLink(documentationBtn, "/documentation");

            Button downloadsBtn = new Button("Downloads");
            downloadsBtn.setFocusTraversable(false);
            downloadsBtn.setMinWidth(Region.USE_PREF_SIZE);
            downloadsBtn.getStyleClass().add("downloads-button");
            downloadsBtn.setVisible(!view.isMobile());
            downloadsBtn.setManaged(!view.isMobile());
            LinkUtil.setLink(downloadsBtn, "/downloads");

            Button loginBtn = new Button("Login", new FontIcon(JFXCentralIcon.LOG_IN));
            loginBtn.setFocusTraversable(false);
            loginBtn.setVisible(SocialUtil.isSocialFeaturesEnabled());
            loginBtn.setManaged(SocialUtil.isSocialFeaturesEnabled());
            loginBtn.setMinWidth(Region.USE_PREF_SIZE);
            loginBtn.getStyleClass().add("login-button");
            LinkUtil.setLink(loginBtn, "/login");

            Region separatorRegion = createSeparatorRegion();
            separatorRegion.visibleProperty().bind(loginBtn.visibleProperty());
            separatorRegion.managedProperty().bind(loginBtn.managedProperty());

            searchField.setVisible(true);
            searchField.setMinWidth(Region.USE_PREF_SIZE);
            contentBox.getChildren().setAll(logoWrapper, new Spacer(), resourcesBtn, communityBtn, showcasesBtn,documentationBtn, downloadsBtn, separatorRegion, loginBtn, searchField);
        } else {
            Region logoutRegion = new Region();
            logoutRegion.getStyleClass().add("logout-region");

            Button logOutBtn = new Button(null, logoutRegion);
            logOutBtn.setFocusTraversable(false);
            logOutBtn.getStyleClass().add("logout-button");
            logOutBtn.setVisible(SocialUtil.isSocialFeaturesEnabled());
            logOutBtn.setManaged(SocialUtil.isSocialFeaturesEnabled());

            Region searchRegion = new Region();
            searchRegion.getStyleClass().add("search-region");

            Button searchBtn = new Button(null, searchRegion);
            searchBtn.setFocusTraversable(false);
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
            menuBtn.setFocusTraversable(false);
            menuBtn.getStyleClass().add("top-menu-button");
            menuBtn.showingProperty().addListener(it -> {
                if (menuBtn.isShowing()) {
                    setShowHamburgerMenu(true);
                }
            });

            showHamburgerMenuProperty().addListener(it -> {
                if (!isShowHamburgerMenu()) {
                    menuBtn.hide();
                }
            });

            Region separatorRegion = createSeparatorRegion();
            separatorRegion.visibleProperty().bind(logOutBtn.visibleProperty());
            separatorRegion.managedProperty().bind(logOutBtn.managedProperty());

            contentBox.getChildren().setAll(logoWrapper, new Spacer(), logOutBtn, separatorRegion, stackPane, createSeparatorRegion(), menuBtn);
        }

        usedProperty().bind(blocking);
    }

    private void fillResourcesMenu(MenuButton button) {
        button.getItems().add(createMenuItem("Libraries", "/libraries", IkonUtil.getModelIkon(Library.class)));
        button.getItems().add(createMenuItem("Tools", "/tools", IkonUtil.getModelIkon(Tool.class)));
        button.getItems().add(createMenuItem("Videos", "/videos", IkonUtil.getModelIkon(Video.class)));
        button.getItems().add(createMenuItem("Books", "/books", IkonUtil.getModelIkon(Book.class)));
        button.getItems().add(createMenuItem("Blogs", "/blogs", IkonUtil.getModelIkon(Blog.class)));
        button.getItems().add(createMenuItem("Tips", "/tips", IkonUtil.getModelIkon(Tip.class)));
        button.getItems().add(createMenuItem("Tutorials", "/tutorials", IkonUtil.getModelIkon(Tutorial.class)));
        button.getItems().add(createMenuItem("Icons", "/icons", IkonUtil.getModelIkon(IkonliPack.class)));
    }

    private void fillCommunityMenu(MenuButton button) {
        button.getItems().add(createMenuItem("People", "/people", IkonUtil.getModelIkon(Person.class)));
        button.getItems().add(createMenuItem("Companies", "/companies", IkonUtil.getModelIkon(Company.class)));
        button.getItems().add(createMenuItem("OpenJFX Project", "/openjfx", MaterialDesign.MDI_GITHUB_BOX));
        button.getItems().add(createMenuItem("Links of the Week", "/links", IkonUtil.getModelIkon(LinksOfTheWeek.class)));
        button.getItems().add(createMenuItem("Meet the Team", "/team", JFXCentralIcon.TEAM));
        if (SocialUtil.isSocialFeaturesEnabled()) {
            button.getItems().add(createMenuItem("Top Content", "/top", JFXCentralIcon.TOP_CONTENT));
        }
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
        menuButton.setFocusTraversable(false);
        if (blocking == null) {
            blocking = Bindings.createBooleanBinding(menuButton::isShowing, menuButton.showingProperty());
        } else {
            blocking = blocking.or(Bindings.createBooleanBinding(menuButton::isShowing, menuButton.showingProperty()));
        }
        return menuButton;
    }

    private final BooleanProperty showHamburgerMenu = new SimpleBooleanProperty(this, "showHamburgerMenu");

    public boolean isShowHamburgerMenu() {
        return showHamburgerMenu.get();
    }

    public BooleanProperty showHamburgerMenuProperty() {
        return showHamburgerMenu;
    }

    public void setShowHamburgerMenu(boolean showHamburgerMenu) {
        this.showHamburgerMenu.set(showHamburgerMenu);
    }

    private Node getSearchTextField() {
        if (searchTextField == null) {
            searchTextField = searchField.getEditor();
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
        separator.setMinWidth(Region.USE_PREF_SIZE);
        return separator;
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
