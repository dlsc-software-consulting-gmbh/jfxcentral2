package com.dlsc.jfxcentral2.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class SocialLinksView extends FlowPane {

    public SocialLinksView() {
        getStyleClass().add("social-links-view");

        Button twitterLinkBtn = new Button("Twitter", new FontIcon(MaterialDesign.MDI_TWITTER));
        twitterLinkBtn.getStyleClass().add("twitter-link-btn");
        twitterLinkBtn.managedProperty().bind(twitterLinkBtn.visibleProperty());
        twitterLinkBtn.visibleProperty().bind(twitterUrlProperty().isNotEmpty());
        twitterLinkBtn.setOnAction(e -> System.out.println(getTwitterUrl()));

        Button linkedInLinkBtn = new Button("LinkedIn", new FontIcon(MaterialDesign.MDI_LINKEDIN_BOX));
        linkedInLinkBtn.getStyleClass().add("linkedin-link-btn");
        linkedInLinkBtn.managedProperty().bind(linkedInLinkBtn.visibleProperty());
        linkedInLinkBtn.visibleProperty().bind(linkedInUrlProperty().isNotEmpty());
        linkedInLinkBtn.setOnAction(e -> System.out.println(getLinkedInUrl()));

        Button websiteLinkBtn = new Button("Website", new FontIcon(MaterialDesign.MDI_WEB));
        websiteLinkBtn.getStyleClass().add("website-link-btn");
        websiteLinkBtn.managedProperty().bind(websiteLinkBtn.visibleProperty());
        websiteLinkBtn.visibleProperty().bind(websiteUrlProperty().isNotEmpty());
        websiteLinkBtn.setOnAction(e -> System.out.println(getWebsiteUrl()));

        Button mailLinkBtn = new Button("Mail", new FontIcon(MaterialDesign.MDI_SEND));
        mailLinkBtn.getStyleClass().add("mail-link-btn");
        mailLinkBtn.managedProperty().bind(mailLinkBtn.visibleProperty());
        mailLinkBtn.visibleProperty().bind(mailUrlProperty().isNotEmpty());
        mailLinkBtn.setOnAction(e -> System.out.println(getMailUrl()));

        Button githubLinkBtn = new Button("GitHub", new FontIcon(MaterialDesign.MDI_GITHUB_CIRCLE));
        githubLinkBtn.getStyleClass().add("github-link-btn");
        githubLinkBtn.managedProperty().bind(githubLinkBtn.visibleProperty());
        githubLinkBtn.visibleProperty().bind(githubUrlProperty().isNotEmpty());
        githubLinkBtn.setOnAction(e -> System.out.println(getGithubUrl()));

        getChildren().setAll(twitterLinkBtn, linkedInLinkBtn, websiteLinkBtn, mailLinkBtn, githubLinkBtn);
    }

    private final StringProperty twitterUrl = new SimpleStringProperty(this, "twitterUrl");

    public String getTwitterUrl() {
        return twitterUrl.get();
    }

    public StringProperty twitterUrlProperty() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl.set(twitterUrl);
    }

    private final StringProperty linkedInUrl = new SimpleStringProperty(this, "linkedInUrl");

    public String getLinkedInUrl() {
        return linkedInUrl.get();
    }

    public StringProperty linkedInUrlProperty() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl.set(linkedInUrl);
    }

    private final StringProperty websiteUrl = new SimpleStringProperty(this, "websiteUrl");

    public String getWebsiteUrl() {
        return websiteUrl.get();
    }

    public StringProperty websiteUrlProperty() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl.set(websiteUrl);
    }

    private final StringProperty mailUrl = new SimpleStringProperty(this, "mailUrl");

    public String getMailUrl() {
        return mailUrl.get();
    }

    public StringProperty mailUrlProperty() {
        return mailUrl;
    }

    public void setMailUrl(String mailUrl) {
        this.mailUrl.set(mailUrl);
    }

    private final StringProperty githubUrl = new SimpleStringProperty(this, "githubUrl");

    public String getGithubUrl() {
        return githubUrl.get();
    }

    public StringProperty githubUrlProperty() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl.set(githubUrl);
    }
}
