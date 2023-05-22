package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import one.jpro.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;

public class SocialLinksView extends FlowPane {

    private final Button twitterLinkBtn;
    private final Button linkedInLinkBtn;
    private final Button websiteLinkBtn;
    private final Button mailLinkBtn;
    private final Button githubLinkBtn;

    public SocialLinksView() {
        getStyleClass().add("social-links-view");

        twitterLinkBtn = new Button("Twitter", new FontIcon(IkonUtil.twitter));
        twitterLinkBtn.getStyleClass().add("twitter-link-btn");
        twitterLinkBtn.managedProperty().bind(twitterLinkBtn.visibleProperty());
        twitterLinkBtn.visibleProperty().bind(twitterUrlProperty().isNotEmpty());
        twitterUrl.addListener(it -> updateLink(twitterLinkBtn, getTwitterUrl()));

        linkedInLinkBtn = new Button("LinkedIn", new FontIcon(IkonUtil.linkedin));
        linkedInLinkBtn.getStyleClass().add("linkedin-link-btn");
        linkedInLinkBtn.managedProperty().bind(linkedInLinkBtn.visibleProperty());
        linkedInLinkBtn.visibleProperty().bind(linkedInUrlProperty().isNotEmpty());
        linkedInUrl.addListener(it -> updateLink(linkedInLinkBtn, getTwitterUrl()));

        websiteLinkBtn = new Button("Website", new FontIcon(IkonUtil.website));
        websiteLinkBtn.getStyleClass().add("website-link-btn");
        websiteLinkBtn.managedProperty().bind(websiteLinkBtn.visibleProperty());
        websiteLinkBtn.visibleProperty().bind(websiteUrlProperty().isNotEmpty());
        websiteUrl.addListener(it -> updateLink(websiteLinkBtn, getTwitterUrl()));

        mailLinkBtn = new Button("Mail", new FontIcon(IkonUtil.mail));
        mailLinkBtn.getStyleClass().add("mail-link-btn");
        mailLinkBtn.managedProperty().bind(mailLinkBtn.visibleProperty());
        mailLinkBtn.visibleProperty().bind(mailUrlProperty().isNotEmpty());
        mailUrl.addListener(it -> updateLink(mailLinkBtn, getTwitterUrl()));

        githubLinkBtn = new Button("GitHub", new FontIcon(IkonUtil.github));
        githubLinkBtn.getStyleClass().add("github-link-btn");
        githubLinkBtn.managedProperty().bind(githubLinkBtn.visibleProperty());
        githubLinkBtn.visibleProperty().bind(githubUrlProperty().isNotEmpty());
        githubUrl.addListener(it -> updateLink(githubLinkBtn, getTwitterUrl()));

        getChildren().setAll(twitterLinkBtn, linkedInLinkBtn, websiteLinkBtn, mailLinkBtn, githubLinkBtn);
    }

    private void updateLink(Node node, String url) {
        if (StringUtils.isNotBlank(url)) {
            LinkUtil.setExternalLink(node, url);
        }
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
