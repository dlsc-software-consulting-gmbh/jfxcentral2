package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.materialdesign2.MaterialDesignB;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;
import org.kordamp.ikonli.materialdesign2.MaterialDesignV;

import java.util.List;

public class HelloStylingDropdowns extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        MenuButton menuButton = createMenuButton();

        MenuButton menuButton2 = createMenuButton();
        menuButton2.getStyleClass().add("dropdowns-style2");

        Label label = new Label("Click the text to pop up the ContextMenu");
        List<MenuItem> menuItems = createMenuItems();

        label.setContextMenu(new ContextMenu(menuItems.toArray(new MenuItem[0])));
        label.getStyleClass().add("");

        VBox box = new VBox(80, menuButton, menuButton2,label);
        box.setPadding(new Insets(180));
        box.getStyleClass().add("hello-styling-dropdowns");

        return box;
    }

    private MenuButton createMenuButton() {
        MenuButton menuButton = new MenuButton("Menu Button");
        List<MenuItem> items = createMenuItems();
        menuButton.getItems().addAll(items);
        return menuButton;
    }

    private List<MenuItem> createMenuItems() {
        MenuItem item1 = new MenuItem("Libraries", new FontIcon(Material2OutlinedAL.LIBRARY_BOOKS));
        MenuItem item2 = new MenuItem("Blogs", new FontIcon(MaterialDesign.MDI_NEWSPAPER));
        MenuItem item3 = new MenuItem("Tools", new FontIcon(MaterialDesignT.TOOLS));
        MenuItem item4 = new MenuItem("Books", new FontIcon(MaterialDesignB.BOOK_OPEN_VARIANT));
        MenuItem item5 = new MenuItem("Tutorials", new FontIcon(Material.VIEW_IN_AR));
        MenuItem item6 = new MenuItem("Videos", new FontIcon(MaterialDesignV.VIDEO_OUTLINE));
        MenuItem item7 = new MenuItem("Links of the week", new FontIcon(Material2AL.LIST_ALT));
        return List.of(item1, item2, item3, item4, item5, item6, item7);
    }

    @Override
    public String getSampleName() {
        return "Styling Dropdowns";
    }
}
