package com.dlsc.jfxcentral2.components.hamburger;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.OSUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import com.dlsc.jfxcentral2.utils.SocialUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class HamburgerMenuView extends PaneBase {
    private final VBox menusBox;

    public HamburgerMenuView(boolean mobile) {
        getStyleClass().add("hamburger-menu-view");

        // top header
        CustomImageView imageView = new CustomImageView();
        imageView.getStyleClass().addAll("jfx-central-logo", "black");

        Button closeButton = new Button();
        closeButton.setFocusTraversable(false);
        closeButton.setGraphic(new FontIcon(IkonUtil.close));
        closeButton.getStyleClass().add("close-button");
        closeButton.setOnAction(e -> getOnClose().run());

        HBox topHeader = new HBox(imageView, new Spacer(), closeButton);
        topHeader.getStyleClass().add("top-header");

        menusBox = new VBox();
        menusBox.getStyleClass().add("menus-box");

        VBox contentBox = new VBox(topHeader, menusBox);
        contentBox.getStyleClass().add("content-box");

        getChildren().add(contentBox);

        HamburgerMenu resourcesMenu = new HamburgerMenu("Resources");
        resourcesMenu.getItems().addAll(
                new HamburgerMenuItem("Libraries", IkonUtil.getModelIkon(Library.class), PagePath.LIBRARIES),
                new HamburgerMenuItem("Tools", IkonUtil.getModelIkon(Tool.class), PagePath.TOOLS),
                new HamburgerMenuItem("Videos", IkonUtil.getModelIkon(Video.class), PagePath.VIDEOS),
                new HamburgerMenuItem("Books", IkonUtil.getModelIkon(Book.class), PagePath.BOOKS),
                new HamburgerMenuItem("Blogs", IkonUtil.getModelIkon(Blog.class), PagePath.BLOGS),
                new HamburgerMenuItem("Tips", IkonUtil.getModelIkon(Tip.class), PagePath.TIPS),
                new HamburgerMenuItem("Tutorials", IkonUtil.getModelIkon(Tutorial.class), PagePath.TUTORIALS),
                new HamburgerMenuItem("Icons", IkonUtil.getModelIkon(IkonliPack.class), PagePath.ICONS),
                new HamburgerMenuItem("Documentation", IkonUtil.getModelIkon(Documentation.class), PagePath.DOCUMENTATION)
        );

        if (!mobile && !OSUtil.isAndroidOrIOS()) {
            resourcesMenu.getItems().add(new HamburgerMenuItem("Downloads", IkonUtil.getModelIkon(Download.class), PagePath.DOWNLOADS));
        }

        HamburgerMenu communityMenu = new HamburgerMenu("Community");
        communityMenu.getItems().addAll(
                new HamburgerMenuItem("People", IkonUtil.getModelIkon(Person.class), PagePath.PEOPLE),
                new HamburgerMenuItem("Companies", IkonUtil.getModelIkon(Company.class), PagePath.COMPANIES),
                new HamburgerMenuItem("OpenJFX", MaterialDesign.MDI_GITHUB_BOX, PagePath.OPENJFX),
                new HamburgerMenuItem("Links of the Week", IkonUtil.getModelIkon(LinksOfTheWeek.class), PagePath.LINKS),
                new HamburgerMenuItem("Meet the Team", JFXCentralIcon.TEAM, PagePath.TEAM)
        );

        if (SocialUtil.isSocialFeaturesEnabled()) {
            communityMenu.getItems().add(new HamburgerMenuItem("Top Content", JFXCentralIcon.TOP_CONTENT, PagePath.TOP));
        }

        HamburgerMenu showcases = new HamburgerMenu("Showcases", PagePath.SHOWCASES);

        HamburgerMenu learnMenu = new HamburgerMenu("Learn");
        learnMenu.getItems().addAll(
                new HamburgerMenuItem("Learn JavaFX", IkonUtil.learnJavaFX, PagePath.LEARN_JAVAFX),
                new HamburgerMenuItem("Learn Mobile", IkonUtil.learnMobile, PagePath.LEARN_MOBILE),
                new HamburgerMenuItem("Learn Raspberry Pi", IkonUtil.learnRaspberryPi, PagePath.LEARN_RASPBERRYPI)
        );

        HamburgerMenu utilities = new HamburgerMenu("Utilities", PagePath.UTILITIES);

        setMaxHeight(Region.USE_PREF_SIZE);

        getMenus().addAll(resourcesMenu, communityMenu, showcases, learnMenu);
        if (!OSUtil.isNative()) {
            getMenus().add(utilities);
        }

        refreshMenus();

        getMenus().addListener((ListChangeListener<HamburgerMenu>) change -> refreshMenus());
    }

    private void refreshMenus() {
        menusBox.getChildren().clear();
        ToggleGroup group = new ToggleGroup();
        for (HamburgerMenu menu : getMenus()) {
            menusBox.getChildren().add(createMenuButton(menu, group));
        }
    }

    private Node createMenuButton(HamburgerMenu menu, ToggleGroup group) {
        if (menu.getItems().isEmpty()) {
            Button button = new Button();
            button.getStyleClass().add("hamburger-menu-button");
            initLabeled(menu, button);
            return button;
        } else {
            ToggleButton toggleButton = new ToggleButton();
            toggleButton.getStyleClass().add("hamburger-menu-button");
            if (group != null) {
                toggleButton.setToggleGroup(group);
            }

            Label menuLabel = new Label();
            menuLabel.getStyleClass().add("menu-label");
            initLabeled(menu, menuLabel);

            FontIcon expandIndicator = new FontIcon(JFXCentralIcon.CHEVRON_MENU_BOTTOM);
            expandIndicator.getStyleClass().add("expand-indicator");

            HBox buttonGraphic = new HBox(menuLabel, new Spacer(), expandIndicator);
            buttonGraphic.getStyleClass().add("button-graphic");

            toggleButton.setGraphic(buttonGraphic);
            toggleButton.selectedProperty().bindBidirectional(menu.expandedProperty());
            toggleButton.onActionProperty().bind(menu.onActionProperty());
            toggleButton.setMaxWidth(Double.MAX_VALUE);

            VBox submenuBox = new VBox();
            submenuBox.getStyleClass().add("submenu-box");

            for (HamburgerMenuItem item : menu.getItems()) {
                if (item instanceof HamburgerMenu) {
                    submenuBox.getChildren().add(createMenuButton((HamburgerMenu) item, null));
                } else {
                    Button itemButton = new Button();
                    String customStyleClass = item.getUrl().replace("/", "").replace("_", "-") + "-item";
                    itemButton.getStyleClass().addAll("hamburger-item-button", customStyleClass);
                    initLabeled(item, itemButton);
                    submenuBox.getChildren().add(itemButton);
                }
            }

            StackPane submenuWrapper = new StackPane(submenuBox);
            submenuWrapper.getStyleClass().add("submenu-wrapper");
            submenuWrapper.managedProperty().bind(menu.expandedProperty());
            submenuWrapper.visibleProperty().bind(menu.expandedProperty());

            return new VBox(toggleButton, submenuWrapper);
        }
    }

    private void initLabeled(HamburgerMenuItem item, Labeled itemButton) {
        itemButton.textProperty().bind(item.textProperty());
        itemButton.graphicProperty().bind(Bindings.createObjectBinding(() -> {
            if (item.getIkon() == null) {
                return null;
            }
            FontIcon icon = new FontIcon();
            icon.getStyleClass().add("menu-icon");
            icon.iconCodeProperty().bind(item.ikonProperty());
            return icon;
        }, item.ikonProperty()));

        if (itemButton instanceof ButtonBase btn) {
            btn.onActionProperty().bind(item.onActionProperty());
        }

        String url = item.getUrl();
        if (StringUtils.isNotBlank(url)) {
            LinkUtil.setLink(itemButton, url);
        }

        itemButton.setMaxWidth(Double.MAX_VALUE);

    }

    private ObservableList<HamburgerMenu> menus = FXCollections.observableArrayList();

    public ObservableList<HamburgerMenu> getMenus() {
        return menus;
    }

    public void setMenus(ObservableList<HamburgerMenu> menus) {
        this.menus = menus;
    }

    private final ObjectProperty<Runnable> onClose = new SimpleObjectProperty<>(this, "onClose", () -> System.out.println("on close callback not set"));

    public Runnable getOnClose() {
        return onClose.get();
    }

    public ObjectProperty<Runnable> onCloseProperty() {
        return onClose;
    }

    public void setOnClose(Runnable onClose) {
        this.onClose.set(onClose);
    }
}
