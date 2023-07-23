package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

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
        menuButton.setFocusTraversable(false);
        List<MenuItem> items = createMenuItems();
        menuButton.getItems().addAll(items);
        return menuButton;
    }

    private List<MenuItem> createMenuItems() {
        MenuItem item1 = new MenuItem("Libraries", new FontIcon(IkonUtil.getModelIkon(Library.class)));
        MenuItem item2 = new MenuItem("Blogs", new FontIcon(IkonUtil.getModelIkon(Blog.class)));
        MenuItem item3 = new MenuItem("Tools", new FontIcon(IkonUtil.getModelIkon(Tool.class)));
        MenuItem item4 = new MenuItem("Books", new FontIcon(IkonUtil.getModelIkon(Book.class)));
        MenuItem item5 = new MenuItem("Tutorials", new FontIcon(IkonUtil.getModelIkon(Tutorial.class)));
        MenuItem item6 = new MenuItem("Videos", new FontIcon(IkonUtil.getModelIkon(Video.class)));
        MenuItem item7 = new MenuItem("Links of the week", new FontIcon(IkonUtil.getModelIkon(LinksOfTheWeek.class)));
        return List.of(item1, item2, item3, item4, item5, item6, item7);
    }

    @Override
    public String getSampleName() {
        return "Styling Dropdowns";
    }
}
