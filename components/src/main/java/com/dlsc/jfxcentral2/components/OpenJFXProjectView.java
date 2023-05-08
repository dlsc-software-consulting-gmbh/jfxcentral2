package com.dlsc.jfxcentral2.components;


import com.dlsc.jfxcentral2.utils.JFXCentralUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import one.jpro.routing.LinkUtil;

public class OpenJFXProjectView extends PaneBase {

    private final ImageView image;
    private final Button visitHomePageButton;
    private final Button reportAnIssueButton;
    private final Label description;
    private final TextFlow textFlow;
    private final VBox backgroundVBox;
    private final VBox titleVBox;

    public OpenJFXProjectView() {
        getStyleClass().add("openjfx-project-view");

        /*vbox as background*/
        Region placeHolder = new Region();
        placeHolder.getStyleClass().add("place-holder");

        Region backgroundRegion = new Region();
        backgroundRegion.getStyleClass().add("background");
        backgroundVBox = new VBox(placeHolder, backgroundRegion);
        backgroundVBox.getStyleClass().add("bg-vbox");
        /*title*/
        Label title = new Label("The open source project behind");
        title.getStyleClass().add("title");
        title.setWrapText(true);

        CustomImageView fxLogo = new CustomImageView();
        fxLogo.getStyleClass().add("fx-logo");

        titleVBox = new VBox(title, fxLogo);
        titleVBox.getStyleClass().add("title-vbox");
        setAlignment(titleVBox, Pos.TOP_LEFT);

        /*description*/
        description = new Label("OpenJFX is an open source, next generation " +
                "client application platform for desktop, mobile and embedded systems based on JavaSE." +
                " It is a collaborative effort by many individuals " +
                "and companies with the goal of producing a modern, efficient, and fully featured toolkit for" +
                " developing rich client applications. This is the open source project where JavaFX is being developed.");
        description.getStyleClass().add("description");
        description.setWrapText(true);

        /*text-flow for rich text*/
        Text text1 = new Text("OpenJFX is free software, licensed under ");

        Text text2 = new Text("GPL v2 with the Classpath exception");
        text2.getStyleClass().add("link");
        LinkUtil.setLink(text2, "https://en.wikipedia.org/wiki/GPL_linking_exception#The_Classpath_exception");

        Text text3 = new Text(", just like the JDK. Anybody is welcome to contribute to this project," +
                " port it to other platforms or devices, or do anything else that " +
                "a free software license allows you to do!");

        textFlow = new TextFlow(text1, text2, text3);
        textFlow.getStyleClass().add("text-flow");

        /*buttons*/
        Region openjfxRegion = new Region();
        openjfxRegion.getStyleClass().add("openjfx-region");
        visitHomePageButton = new Button("Visit project Homepage", openjfxRegion);
        visitHomePageButton.getStyleClass().addAll("fill-button", "visit-homepage-button");
        visitHomePageButton.setOnAction(event -> JFXCentralUtil.run(onVisitHomePage));

        Region openjfxRegion2 = new Region();
        openjfxRegion2.getStyleClass().add("openjfx-region2");
        reportAnIssueButton = new Button("Report an issue", openjfxRegion2);
        reportAnIssueButton.getStyleClass().addAll("fill-button", "report-issue-button");
        reportAnIssueButton.setOnAction(event -> JFXCentralUtil.run(onReportAnIssue));

        /*image*/
        image = new ImageView();
        image.getStyleClass().add("image");
        image.setPreserveRatio(false);
        setAlignment(image, Pos.BOTTOM_LEFT);
        image.setFitWidth(635);

        layoutBySize();
    }

    protected void layoutBySize() {
        VBox detailsVBox;
        if (isLarge()) {
            image.setFitHeight(396);
            HBox buttonsBox = new HBox(visitHomePageButton, reportAnIssueButton);
            buttonsBox.getStyleClass().add("buttons-box");
            detailsVBox = new VBox(description, textFlow, buttonsBox);
            detailsVBox.getStyleClass().add("details-vbox");
            getChildren().setAll(backgroundVBox, image, titleVBox, detailsVBox);
            setAlignment(detailsVBox, Pos.BOTTOM_LEFT);
        }else{
            VBox buttonsBox = new VBox(visitHomePageButton, reportAnIssueButton);
            buttonsBox.getStyleClass().add("buttons-box");
            detailsVBox = new VBox(description, textFlow, buttonsBox);
            detailsVBox.getStyleClass().add("details-vbox");
            setAlignment(detailsVBox, Pos.BOTTOM_LEFT);
            if(isMedium()){
                image.setFitHeight(551);
                getChildren().setAll(backgroundVBox, image, titleVBox, detailsVBox);
            }else{
                //small:
                VBox columnVBox = new VBox(titleVBox, detailsVBox);
                columnVBox.getStyleClass().add("column-vbox");
                setAlignment(columnVBox, Pos.TOP_CENTER);
                getChildren().setAll(backgroundVBox, columnVBox);
            }
        }
        detailsVBox.getStyleClass().add("details-vbox");
        setAlignment(detailsVBox, Pos.BOTTOM_LEFT);
    }

    private final ObjectProperty<Runnable> onVisitHomePage = new SimpleObjectProperty<>(this, "onVisitHomePage");

    public Runnable getOnVisitHomePage() {
        return onVisitHomePage.get();
    }

    public ObjectProperty<Runnable> onVisitHomePageProperty() {
        return onVisitHomePage;
    }

    public void setOnVisitHomePage(Runnable onVisitHomePage) {
        this.onVisitHomePage.set(onVisitHomePage);
    }

    private final ObjectProperty<Runnable> onReportAnIssue = new SimpleObjectProperty<>(this, "onReportAnIssue");

    public Runnable getOnReportAnIssue() {
        return onReportAnIssue.get();
    }

    public ObjectProperty<Runnable> onReportAnIssueProperty() {
        return onReportAnIssue;
    }

    public void setOnReportAnIssue(Runnable onReportAnIssue) {
        this.onReportAnIssue.set(onReportAnIssue);
    }
}
