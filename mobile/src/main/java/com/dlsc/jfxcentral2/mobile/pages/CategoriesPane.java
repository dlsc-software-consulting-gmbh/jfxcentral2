package com.dlsc.jfxcentral2.mobile.pages;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Member;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import org.kordamp.ikonli.javafx.FontIcon;

public class CategoriesPane extends GridPane {

    public CategoriesPane() {
        getStyleClass().add("categories-pane");

        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Button booksButton = new Button();
        booksButton.setText("Books");
        booksButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Book.class)));
        booksButton.setMaxWidth(Double.MAX_VALUE);
        booksButton.setUserData(PagePath.HOME);
        MobileLinkUtil.setLink(booksButton, PagePath.BOOKS);

        Button blogsButton = new Button();
        blogsButton.setText("Blogs");
        blogsButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Blog.class)));
        blogsButton.setMaxWidth(Double.MAX_VALUE);
        blogsButton.setUserData(PagePath.BLOGS);
        MobileLinkUtil.setLink(blogsButton, PagePath.BLOGS);

        Button companiesButton = new Button();
        companiesButton.setText("Companies");
        companiesButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Company.class)));
        companiesButton.setMaxWidth(Double.MAX_VALUE);
        companiesButton.setUserData(PagePath.COMPANIES);
        MobileLinkUtil.setLink(companiesButton, PagePath.COMPANIES);

        Button docsButton = new Button();
        docsButton.setText("Docs");
        docsButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Documentation.class)));
        docsButton.setMaxWidth(Double.MAX_VALUE);
        docsButton.setUserData(PagePath.DOCUMENTATION);
        MobileLinkUtil.setLink(docsButton, PagePath.DOCUMENTATION);

        Button tutorialsButton = new Button();
        tutorialsButton.setText("Tutorials");
        tutorialsButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Tutorial.class)));
        tutorialsButton.setMaxWidth(Double.MAX_VALUE);
        tutorialsButton.setUserData(PagePath.TUTORIALS);
        MobileLinkUtil.setLink(tutorialsButton, PagePath.TUTORIALS);

        Button tipsButton = new Button();
        tipsButton.setText("Tips");
        tipsButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Tip.class)));
        tipsButton.setMaxWidth(Double.MAX_VALUE);
        tipsButton.setUserData(PagePath.TIPS);
        MobileLinkUtil.setLink(tipsButton, PagePath.TIPS);

        Button toolsButton = new Button();
        toolsButton.setText("Tools");
        toolsButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Tool.class)));
        toolsButton.setMaxWidth(Double.MAX_VALUE);
        toolsButton.setUserData(PagePath.TOOLS);
        MobileLinkUtil.setLink(toolsButton, PagePath.TOOLS);

        Button videosButton = new Button();
        videosButton.setText("Videos");
        videosButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(Video.class)));
        videosButton.setMaxWidth(Double.MAX_VALUE);
        videosButton.setUserData(PagePath.VIDEOS);
        MobileLinkUtil.setLink(videosButton, PagePath.VIDEOS);

        Button learnJavaFXButton = new Button();
        learnJavaFXButton.setText("Learn JFX");
        learnJavaFXButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(LearnJavaFX.class)));
        learnJavaFXButton.setMaxWidth(Double.MAX_VALUE);
        learnJavaFXButton.setUserData(PagePath.LEARN_JAVAFX);
        MobileLinkUtil.setLink(learnJavaFXButton, PagePath.LEARN_JAVAFX);

        Button learnMobileButton = new Button();
        learnMobileButton.setText("Learn Mobile");
        learnMobileButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(LearnMobile.class)));
        learnMobileButton.setMaxWidth(Double.MAX_VALUE);
        learnMobileButton.setUserData(PagePath.LEARN_MOBILE);
        MobileLinkUtil.setLink(learnMobileButton, PagePath.LEARN_MOBILE);

        Button learnRaspberryPiButton = new Button();
        learnRaspberryPiButton.setText("Learn PI");
        learnRaspberryPiButton.setGraphic(new FontIcon(IkonUtil.getModelIkon(LearnRaspberryPi.class)));
        learnRaspberryPiButton.setMaxWidth(Double.MAX_VALUE);
        learnRaspberryPiButton.setUserData(PagePath.LEARN_RASPBERRYPI);
        MobileLinkUtil.setLink(learnRaspberryPiButton, PagePath.LEARN_RASPBERRYPI);

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

        add(videosButton, 0, 0);
        add(toolsButton, 1, 0);
        add(docsButton, 2, 0);
        add(tipsButton, 3, 0);
        add(tutorialsButton, 4, 0);

        add(learnJavaFXButton, 0, 1);
        add(learnRaspberryPiButton, 1, 1);
        add(learnMobileButton, 2, 1);
        add(booksButton, 3, 1);
        add(blogsButton, 4, 1);

        add(companiesButton, 0, 2);
    }
}