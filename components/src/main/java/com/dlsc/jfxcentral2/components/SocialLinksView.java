package com.dlsc.jfxcentral2.components;

import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class SocialLinksView extends FlowPane {

    public record SocialLinks(String twitter, String linkedIn, String website, String mail, String github) {
    }

    public SocialLinksView(SocialLinks socialLinks) {
        getStyleClass().add("social-links-view");

        String twitter = socialLinks.twitter();
        if (isNotBlank(twitter)) {
            Button twitterLinkBtn = new Button("Twitter", new FontIcon(MaterialDesign.MDI_TWITTER));
            twitterLinkBtn.getStyleClass().add("twitter-link-btn");
            twitterLinkBtn.setOnAction(e -> System.out.println(twitter));
            getChildren().add(twitterLinkBtn);
        }

        String linkedIn = socialLinks.linkedIn();
        if (isNotBlank(linkedIn)) {
            Button linkedInLinkBtn = new Button("LinkedIn", new FontIcon(MaterialDesign.MDI_LINKEDIN_BOX));
            linkedInLinkBtn.getStyleClass().add("linkedin-link-btn");
            linkedInLinkBtn.setOnAction(e -> System.out.println(linkedIn));
            getChildren().add(linkedInLinkBtn);
        }

        String website = socialLinks.website();
        if (isNotBlank(website)) {
            Button websiteLinkBtn = new Button("Website", new FontIcon(MaterialDesign.MDI_WEB));
            websiteLinkBtn.getStyleClass().add("website-link-btn");
            websiteLinkBtn.setOnAction(e -> System.out.println(website));
            getChildren().add(websiteLinkBtn);
        }

        String mail = socialLinks.mail();
        if (isNotBlank(mail)) {
            Button mailLinkBtn = new Button("Mail", new FontIcon(MaterialDesign.MDI_SEND));
            mailLinkBtn.getStyleClass().add("mail-link-btn");
            mailLinkBtn.setOnAction(e -> System.out.println(mail));
            getChildren().add(mailLinkBtn);
        }

        String github = socialLinks.github();
        if (isNotBlank(github)) {
            Button githubLinkBtn = new Button("GitHub", new FontIcon(MaterialDesign.MDI_GITHUB_CIRCLE));
            githubLinkBtn.getStyleClass().add("github-link-btn");
            githubLinkBtn.setOnAction(e -> System.out.println(github));
            getChildren().add(githubLinkBtn);
        }


    }

    private boolean isNotBlank(String str) {
        return str != null && !str.isBlank();
    }

}
