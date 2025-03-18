package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SocialLinksView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloSocialLinksView extends JFXCentralSampleBase {

    private CustomMenuItem customMenuItem;
    private SocialLinksView socialLinksView;

    @Override
    protected Region createControl() {
        socialLinksView = new SocialLinksView();
        socialLinksView.setLinkedInUrl("https://www.linkedin.com/in/dlemmermann/");
        socialLinksView.setWebsiteUrl("https://www.dlsc.com");
        socialLinksView.setMailUrl("mailto:dlemmermann@gmail.com");
        socialLinksView.setGithubUrl("https://github.com/dlsc-software-consulting-gmbh");

        MenuButton menuButton = new MenuButton("Menu");
        menuButton.setFocusTraversable(false);

        SocialLinksView menuLinksView = new SocialLinksView();
        menuLinksView.setLinkedInUrl("https://www.linkedin.com/in/");
        menuLinksView.setGithubUrl("https://github.com/");
        customMenuItem = new CustomMenuItem(menuLinksView);
        customMenuItem.setHideOnClick(false);

        menuButton.getItems().addAll(customMenuItem);

        StackPane wrapper = new StackPane(socialLinksView);
        StackPane.setAlignment(socialLinksView, Pos.CENTER);

        VBox vBox = new VBox(20, wrapper, menuButton);
        vBox.setPadding(new Insets(20));
        vBox.getStyleClass().add("hello-social-links-view");
        vBox.getStylesheets().add(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/test.css").toExternalForm());
        return vBox;
    }

    @Override
    public String getSampleName() {
        return "SocialLinksView";
    }

}
