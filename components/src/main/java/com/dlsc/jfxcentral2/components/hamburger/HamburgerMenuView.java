package com.dlsc.jfxcentral2.components.hamburger;

import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.binding.Bindings;
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
import org.kordamp.ikonli.javafx.FontIcon;

public class HamburgerMenuView extends PaneBase {

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
        closeButton.setOnAction(e -> {
            if (getScene() != null) {
                getScene().getWindow().hide();
            }
        });

        HBox topHeader = new HBox(imageView, jfxCentralLogo, new Spacer(), closeButton);
        topHeader.getStyleClass().add("top-header");

        VBox menusBox = new VBox();
        menusBox.getStyleClass().add("menus-box");

        VBox contentBox = new VBox(topHeader, menusBox);
        contentBox.getStyleClass().add("content-box");

        getChildren().add(contentBox);

        getMenus().addListener((ListChangeListener<HamburgerMenu>) change -> refreshMenus(menusBox));
    }

    private void refreshMenus(VBox menusBox) {
        menusBox.getChildren().clear();
        for (HamburgerMenu menu : getMenus()) {
            menusBox.getChildren().add(createMenuButton(menu));
        }

    }

    private Node createMenuButton(HamburgerMenu menu) {
        if (menu.getItems().isEmpty()) {
            Button menuButton = new Button();
            menuButton.getStyleClass().add("hamburger-menu-button");
            initLabeled(menu, menuButton);
            return menuButton;
        } else {
            ToggleButton menuButton = new ToggleButton();
            menuButton.getStyleClass().add("hamburger-menu-button");

            Label menuLabel = new Label();
            menuLabel.getStyleClass().add("menu-label");
            initLabeled(menu, menuLabel);

            FontIcon expandIndicator = new FontIcon(JFXCentralIcon.CHEVRON_MENU_BOTTOM);
            expandIndicator.getStyleClass().add("expand-indicator");

            HBox buttonGraphic = new HBox(menuLabel, new Spacer(), expandIndicator);
            buttonGraphic.getStyleClass().add("button-graphic");

            menuButton.setGraphic(buttonGraphic);
            menuButton.selectedProperty().bindBidirectional(menu.expandedProperty());
            menuButton.onActionProperty().bind(menu.onActionProperty());
            menuButton.setMaxWidth(Double.MAX_VALUE);

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

            return new VBox(menuButton, submenuWrapper);
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
        itemButton.setMaxWidth(Double.MAX_VALUE);

    }

    private ObservableList<HamburgerMenu> menus = FXCollections.<HamburgerMenu>observableArrayList();

    public ObservableList<HamburgerMenu> getMenus() {
        return menus;
    }

    public void setMenus(ObservableList<HamburgerMenu> menus) {
        this.menus = menus;
    }
}
