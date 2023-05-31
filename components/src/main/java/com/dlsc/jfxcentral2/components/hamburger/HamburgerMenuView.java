package com.dlsc.jfxcentral2.components.hamburger;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
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
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import one.jpro.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class HamburgerMenuView extends PaneBase {

    private final VBox menusBox;

    public HamburgerMenuView() {
        getStyleClass().add("hamburger-menu-view");

        //top header
        CustomImageView imageView = new CustomImageView();
        imageView.setImage(new Image(getClass().getResource("/com/dlsc/jfxcentral2/images/duke.png").toExternalForm()));

        Region jfxCentralLogo = new Region();
        jfxCentralLogo.getStyleClass().add("jfxcentral-region");

        Button closeButton = new Button();
        closeButton.setGraphic(new FontIcon(IkonUtil.close));
        closeButton.getStyleClass().add("close-button");
        closeButton.setOnAction(e -> getOnClose().run());

        HBox topHeader = new HBox(imageView, jfxCentralLogo, new Spacer(), closeButton);
        topHeader.getStyleClass().add("top-header");

        menusBox = new VBox();
        menusBox.getStyleClass().add("menus-box");

        VBox contentBox = new VBox(topHeader, menusBox);
        contentBox.getStyleClass().add("content-box");

        getChildren().add(contentBox);

        HamburgerMenu resourcesMenu = new HamburgerMenu("Resources");
        resourcesMenu.getItems().addAll(
                new HamburgerMenuItem("Libraries", IkonUtil.getModelIkon(Library.class), "/libraries"),
                new HamburgerMenuItem("Tools", IkonUtil.getModelIkon(Tool.class), "/tools"),
                new HamburgerMenuItem("Videos", IkonUtil.getModelIkon(Video.class), "/videos"),
                new HamburgerMenuItem("Books", IkonUtil.getModelIkon(Book.class), "/books"),
                new HamburgerMenuItem("Blogs", IkonUtil.getModelIkon(Blog.class), "/blogs"),
                new HamburgerMenuItem("Tutorials", IkonUtil.getModelIkon(Tutorial.class), "/tutorials"),
                new HamburgerMenuItem("Icons", IkonUtil.getModelIkon(IkonliPack.class), "/icons")
        );

        HamburgerMenu communityMenu = new HamburgerMenu("Community");
        communityMenu.getItems().addAll(
                new HamburgerMenuItem("People", IkonUtil.getModelIkon(Person.class), "/people"),
                new HamburgerMenuItem("Companies", IkonUtil.getModelIkon(Company.class), "/companies"),
                new HamburgerMenuItem("OpenJFX", MaterialDesign.MDI_GITHUB_BOX, "/openjfx"),
                new HamburgerMenuItem("Links of the Week", IkonUtil.getModelIkon(LinksOfTheWeek.class), "/links")
        );

        HamburgerMenu showcases = new HamburgerMenu("Showcases", "/showcases");
        showcases.setOnAction(e -> System.out.println("onAction Showcases ..."));

        HamburgerMenu downloads = new HamburgerMenu("Downloads", "/downloads");
        downloads.setOnAction(e -> System.out.println("onAction Downloads ..."));

        setMaxHeight(Region.USE_PREF_SIZE);

        getMenus().addAll(resourcesMenu, communityMenu, showcases, downloads);

        refreshMenus();

        getMenus().addListener((ListChangeListener<HamburgerMenu>) change -> refreshMenus());
    }

    private void refreshMenus() {
        menusBox.getChildren().clear();
        for (HamburgerMenu menu : getMenus()) {
            menusBox.getChildren().add(createMenuButton(menu));
        }
    }

    private Node createMenuButton(HamburgerMenu menu) {
        if (menu.getItems().isEmpty()) {
            Button button = new Button();
            button.getStyleClass().add("hamburger-menu-button");
            initLabeled(menu, button);
            return button;
        } else {
            ToggleButton toggleButton = new ToggleButton();
            toggleButton.getStyleClass().add("hamburger-menu-button");

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
                    submenuBox.getChildren().add(createMenuButton((HamburgerMenu) item));
                } else {
                    Button itemButton = new Button();
                    itemButton.getStyleClass().add("hamburger-item-button");
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

    private ObservableList<HamburgerMenu> menus = FXCollections.<HamburgerMenu>observableArrayList();

    public ObservableList<HamburgerMenu> getMenus() {
        return menus;
    }

    public void setMenus(ObservableList<HamburgerMenu> menus) {
        this.menus = menus;
    }

    private final ObjectProperty<Runnable> onClose = new SimpleObjectProperty<>(this, "onClose", () -> {
        System.out.println("on close callback not set");
    });

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
