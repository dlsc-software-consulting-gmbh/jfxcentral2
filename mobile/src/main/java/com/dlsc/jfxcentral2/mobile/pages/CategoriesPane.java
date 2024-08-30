package com.dlsc.jfxcentral2.mobile.pages;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.Member;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class CategoriesPane extends GridPane {

    public CategoriesPane(Runnable closeDrawer) {
        getStyleClass().add("categories-pane");

        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        CustomToggleButton homeButton = new CustomToggleButton();
        homeButton.setText("Home");
        homeButton.setPrefWidth(0);
        homeButton.setGraphic(new FontIcon(MaterialDesign.MDI_HOME));
        homeButton.setMaxWidth(Double.MAX_VALUE);
        homeButton.setUserData(PagePath.HOME);
        homeButton.setOnMousePressed(evt -> {
            MobileHomePage.getInstance().setContentType(MobileHomePage.ContentType.NORMAL);
            MobileLinkUtil.getToPage(PagePath.HOME);
        });

        CustomToggleButton linksWeekButton = new CustomToggleButton();
        linksWeekButton.setPrefWidth(0);
        linksWeekButton.setText("News");
        linksWeekButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(LinksOfTheWeek.class)));
        linksWeekButton.setMaxWidth(Double.MAX_VALUE);
        linksWeekButton.setUserData(PagePath.LINKS);
        linksWeekButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.LINKS));

        CustomToggleButton showcasesButton = new CustomToggleButton();
        showcasesButton.setPrefWidth(0);
        showcasesButton.setText("Apps");
        showcasesButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(RealWorldApp.class)));
        showcasesButton.setMaxWidth(Double.MAX_VALUE);
        showcasesButton.setUserData(PagePath.SHOWCASES);
        showcasesButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.SHOWCASES));

        CustomToggleButton libraryButton = new CustomToggleButton();
        libraryButton.setPrefWidth(0);
        libraryButton.setText("Libs");
        libraryButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Library.class)));
        libraryButton.setMaxWidth(Double.MAX_VALUE);
        libraryButton.setUserData(PagePath.LIBRARIES);
        libraryButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.LIBRARIES));

        CustomToggleButton peopleButton = new CustomToggleButton();
        peopleButton.setPrefWidth(0);
        peopleButton.setText("People");
        peopleButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Person.class)));
        peopleButton.setMaxWidth(Double.MAX_VALUE);
        peopleButton.setUserData(PagePath.PEOPLE);
        peopleButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.PEOPLE));

        CustomToggleButton booksButton = new CustomToggleButton();
        booksButton.setText("Books");
        booksButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Book.class)));
        booksButton.setMaxWidth(Double.MAX_VALUE);
        booksButton.setUserData(PagePath.BOOKS);
        booksButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.BOOKS));

        CustomToggleButton blogsButton = new CustomToggleButton();
        blogsButton.setText("Blogs");
        blogsButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Blog.class)));
        blogsButton.setMaxWidth(Double.MAX_VALUE);
        blogsButton.setUserData(PagePath.BLOGS);
        blogsButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.BLOGS));

        CustomToggleButton companiesButton = new CustomToggleButton();
        companiesButton.setText("Companies");
        companiesButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Company.class)));
        companiesButton.setMaxWidth(Double.MAX_VALUE);
        companiesButton.setUserData(PagePath.COMPANIES);
        companiesButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.COMPANIES));

        CustomToggleButton docsButton = new CustomToggleButton();
        docsButton.setText("Docs");
        docsButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Documentation.class)));
        docsButton.setMaxWidth(Double.MAX_VALUE);
        docsButton.setUserData(PagePath.DOCUMENTATION);
        docsButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.DOCUMENTATION));

        CustomToggleButton tutorialsButton = new CustomToggleButton();
        tutorialsButton.setText("Tutorials");
        tutorialsButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Tutorial.class)));
        tutorialsButton.setMaxWidth(Double.MAX_VALUE);
        tutorialsButton.setUserData(PagePath.TUTORIALS);
        tutorialsButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.TUTORIALS));

        CustomToggleButton tipsButton = new CustomToggleButton();
        tipsButton.setText("Tips");
        tipsButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Tip.class)));
        tipsButton.setMaxWidth(Double.MAX_VALUE);
        tipsButton.setUserData(PagePath.TIPS);
        tipsButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.LINKS));

        CustomToggleButton toolsButton = new CustomToggleButton();
        toolsButton.setText("Tools");
        toolsButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Tool.class)));
        toolsButton.setMaxWidth(Double.MAX_VALUE);
        toolsButton.setUserData(PagePath.TOOLS);
        toolsButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.TOOLS));

        CustomToggleButton videosButton = new CustomToggleButton();
        videosButton.setText("Videos");
        videosButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Video.class)));
        videosButton.setMaxWidth(Double.MAX_VALUE);
        videosButton.setUserData(PagePath.VIDEOS);
        videosButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.VIDEOS));

        CustomToggleButton learnJavaFXButton = new CustomToggleButton();
        learnJavaFXButton.setText("Learn JFX");
        learnJavaFXButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(LearnJavaFX.class)));
        learnJavaFXButton.setMaxWidth(Double.MAX_VALUE);
        learnJavaFXButton.setUserData(PagePath.LEARN_JAVAFX);
        learnJavaFXButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.LEARN_JAVAFX));

        CustomToggleButton learnMobileButton = new CustomToggleButton();
        learnMobileButton.setText("Learn Mobile");
        learnMobileButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(LearnMobile.class)));
        learnMobileButton.setMaxWidth(Double.MAX_VALUE);
        learnMobileButton.setUserData(PagePath.LEARN_MOBILE);
        learnMobileButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.LEARN_MOBILE));

        CustomToggleButton learnRaspberryPiButton = new CustomToggleButton();
        learnRaspberryPiButton.setText("Learn PI");
        learnRaspberryPiButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(LearnRaspberryPi.class)));
        learnRaspberryPiButton.setMaxWidth(Double.MAX_VALUE);
        learnRaspberryPiButton.setUserData(PagePath.LEARN_RASPBERRYPI);
        learnRaspberryPiButton.setOnMousePressed(evt -> MobileLinkUtil.getToPage(PagePath.LEARN_RASPBERRYPI));

        GridPane.setHgrow(videosButton, Priority.ALWAYS);
        GridPane.setHgrow(toolsButton, Priority.ALWAYS);
        GridPane.setHgrow(docsButton, Priority.ALWAYS);
        GridPane.setHgrow(tipsButton, Priority.ALWAYS);
        GridPane.setHgrow(tutorialsButton, Priority.ALWAYS);
        GridPane.setHgrow(booksButton, Priority.ALWAYS);
        GridPane.setHgrow(blogsButton, Priority.ALWAYS);
        GridPane.setHgrow(companiesButton, Priority.ALWAYS);
        GridPane.setHgrow(learnJavaFXButton, Priority.ALWAYS);
        GridPane.setHgrow(learnMobileButton, Priority.ALWAYS);
        GridPane.setHgrow(learnRaspberryPiButton, Priority.ALWAYS);

        GridPane.setVgrow(videosButton, Priority.ALWAYS);
        GridPane.setVgrow(toolsButton, Priority.ALWAYS);
        GridPane.setVgrow(docsButton, Priority.ALWAYS);
        GridPane.setVgrow(tipsButton, Priority.ALWAYS);
        GridPane.setVgrow(tutorialsButton, Priority.ALWAYS);
        GridPane.setVgrow(booksButton, Priority.ALWAYS);
        GridPane.setVgrow(blogsButton, Priority.ALWAYS);
        GridPane.setVgrow(companiesButton, Priority.ALWAYS);
        GridPane.setVgrow(learnJavaFXButton, Priority.ALWAYS);
        GridPane.setVgrow(learnMobileButton, Priority.ALWAYS);
        GridPane.setVgrow(learnRaspberryPiButton, Priority.ALWAYS);

        videosButton.setPrefWidth(0);
        toolsButton.setPrefWidth(0);
        docsButton.setPrefWidth(0);
        tipsButton.setPrefWidth(0);
        tutorialsButton.setPrefWidth(0);
        booksButton.setPrefWidth(0);
        blogsButton.setPrefWidth(0);
        companiesButton.setPrefWidth(0);
        learnJavaFXButton.setPrefWidth(0);
        learnMobileButton.setPrefWidth(0);
        learnRaspberryPiButton.setPrefWidth(0);

        add(homeButton, 0, 0);
        add(linksWeekButton, 1, 0);
        add(showcasesButton, 2, 0);
        add(libraryButton, 3, 0);
        add(peopleButton, 4, 0);

        add(videosButton, 0, 1);
        add(toolsButton, 1, 1);
        add(docsButton, 2, 1);
        add(tipsButton, 3, 1);
        add(tutorialsButton, 4, 1);

        add(learnJavaFXButton, 0, 2);
        add(learnRaspberryPiButton, 1, 2);
        add(learnMobileButton, 2, 2);
        add(booksButton, 3, 2);
        add(blogsButton, 4, 2);

        add(companiesButton, 0, 3);

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(
                homeButton, linksWeekButton, showcasesButton, libraryButton, peopleButton,
                videosButton, toolsButton, docsButton, tipsButton, tutorialsButton,
                learnJavaFXButton, learnRaspberryPiButton, learnMobileButton, booksButton, blogsButton
        );

        toggleGroup.selectToggle(homeButton);
//        toggleGroup.selectedToggleProperty().addListener(it -> closeDrawer.run());
    }
}