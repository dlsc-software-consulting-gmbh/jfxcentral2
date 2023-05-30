package com.dlsc.jfxcentral2.demo.components;

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
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.hamburger.HamburgerMenu;
import com.dlsc.jfxcentral2.components.hamburger.HamburgerMenuItem;
import com.dlsc.jfxcentral2.components.hamburger.HamburgerMenuView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class HelloHamburgerMenuView extends JFXCentralSampleBase {

    private HamburgerMenuView menuView;

    @Override
    protected Region createControl() {
        menuView = new HamburgerMenuView();

        HamburgerMenu resourcesMenu = new HamburgerMenu("Resources");
        //expanded by default
        resourcesMenu.setExpanded(true);
        //resourcesMenu.setOnAction(e -> System.out.println("onAction Resources ..."));
        resourcesMenu.getItems().addAll(
                new HamburgerMenuItem("Libraries", IkonUtil.getModelIkon(Library.class)),
                new HamburgerMenuItem("Tools", IkonUtil.getModelIkon(Tool.class)),
                new HamburgerMenuItem("Videos", IkonUtil.getModelIkon(Video.class)),
                new HamburgerMenuItem("Books", IkonUtil.getModelIkon(Book.class)),
                new HamburgerMenuItem("Blogs", IkonUtil.getModelIkon(Blog.class)),
                new HamburgerMenuItem("Tutorials", IkonUtil.getModelIkon(Tutorial.class)),
                new HamburgerMenuItem("Icons", IkonUtil.getModelIkon(IkonliPack.class))
        );

        HamburgerMenu communityMenu = new HamburgerMenu("Community");
        communityMenu.getItems().addAll(
                new HamburgerMenuItem("People", IkonUtil.getModelIkon(Person.class)),
                new HamburgerMenuItem("Companies", IkonUtil.getModelIkon(Company.class)),
                new HamburgerMenuItem("OpenJFX", MaterialDesign.MDI_GITHUB_BOX),
                new HamburgerMenuItem("Links of the Week", IkonUtil.getModelIkon(LinksOfTheWeek.class))
        );

        HamburgerMenu showcases = new HamburgerMenu("Showcases");
        showcases.setOnAction(e -> System.out.println("onAction Showcases ..."));

        HamburgerMenu downloads = new HamburgerMenu("Downloads");
        downloads.setOnAction(e -> System.out.println("onAction Downloads ..."));

        menuView.getMenus().addAll(
                resourcesMenu,
                communityMenu,
                showcases,
                downloads
        );
        return new ScrollPane(menuView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.MEDIUM);
        menuView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "HamburgerMenuView";
    }
}
