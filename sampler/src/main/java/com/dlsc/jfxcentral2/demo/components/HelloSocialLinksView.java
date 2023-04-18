package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SocialLinksView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloSocialLinksView extends JFXCentralSampleBase {

    private CustomMenuItem customMenuItem;

    @Override
    protected Region createControl() {
        SocialLinksView socialLinksView = new SocialLinksView(new SocialLinksView.SocialLinks(
                "https://twitter.com/dlemmermann",
                "https://www.linkedin.com/in/dlemmermann/",
                "https://www.dlsc.com",
                "mailto:dlemmermann@gmail.com",
                "https://github.com/dlsc-software-consulting-gmbh"));

        MenuButton menuButton = new MenuButton("Menu");
        customMenuItem = new CustomMenuItem(new SocialLinksView(new SocialLinksView.SocialLinks(
                "https://twitter.com/",
                "https://www.linkedin.com/in/",
                null,
                null,
                "https://github.com/")));
        customMenuItem.setHideOnClick(false);

        menuButton.getItems().addAll(
                customMenuItem);

        VBox vBox = new VBox(20, socialLinksView, menuButton);
        vBox.getStyleClass().add("hello-social-links-view");
        vBox.getStylesheets().add(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/test.css").toExternalForm());
        return vBox;
    }

    @Override
    public String getSampleName() {
        return "SocialLinksView";
    }

}
