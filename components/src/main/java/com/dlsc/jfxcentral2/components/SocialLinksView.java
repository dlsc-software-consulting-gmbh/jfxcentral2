package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import one.jpro.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;

public class SocialLinksView extends HBox {

    private final Button twitterLinkBtn;
    private final Button linkedInLinkBtn;
    private final Button websiteLinkBtn;
    private final Button mailLinkBtn;
    private final Button githubLinkBtn;
    private final Button facebookLinkBtn;
    private final Button redditLinkBtn;

    public SocialLinksView() {
        getStyleClass().add("social-links-view");

        setMaxWidth(Region.USE_PREF_SIZE);

        twitterLinkBtn = new Button("Twitter", new FontIcon(IkonUtil.twitter));
        twitterLinkBtn.getStyleClass().add("twitter-link-btn");
        twitterLinkBtn.visibleProperty().bind(twitterUrlProperty().isNotEmpty());
        twitterLinkBtn.managedProperty().bind(twitterLinkBtn.visibleProperty());
        twitterUrl.addListener(it -> updateLink(twitterLinkBtn, getTwitterUrl()));

        redditLinkBtn = new Button("Reddit", new FontIcon(IkonUtil.reddit));
        redditLinkBtn.getStyleClass().add("reddit-link-btn");
        redditLinkBtn.visibleProperty().bind(redditUrlProperty().isNotEmpty());
        redditLinkBtn.managedProperty().bind(redditLinkBtn.visibleProperty());
        redditUrl.addListener(it -> updateLink(redditLinkBtn, getRedditUrl()));

        linkedInLinkBtn = new Button("LinkedIn", new FontIcon(IkonUtil.linkedin));
        linkedInLinkBtn.getStyleClass().add("linkedin-link-btn");
        linkedInLinkBtn.visibleProperty().bind(linkedInUrlProperty().isNotEmpty());
        linkedInLinkBtn.managedProperty().bind(linkedInLinkBtn.visibleProperty());
        linkedInUrl.addListener(it -> updateLink(linkedInLinkBtn, getLinkedInUrl()));

        websiteLinkBtn = new Button("Website", new FontIcon(IkonUtil.website));
        websiteLinkBtn.getStyleClass().add("website-link-btn");
        websiteLinkBtn.visibleProperty().bind(websiteUrlProperty().isNotEmpty());
        websiteLinkBtn.managedProperty().bind(websiteLinkBtn.visibleProperty());
        websiteUrl.addListener(it -> updateLink(websiteLinkBtn, getWebsiteUrl()));

        githubLinkBtn = new Button("GitHub", new FontIcon(IkonUtil.github));
        githubLinkBtn.getStyleClass().add("github-link-btn");
        githubLinkBtn.visibleProperty().bind(githubUrlProperty().isNotEmpty());
        githubLinkBtn.managedProperty().bind(githubLinkBtn.visibleProperty());
        githubUrl.addListener(it -> updateLink(githubLinkBtn, getGithubUrl()));

        facebookLinkBtn = new Button("Facebook", new FontIcon(IkonUtil.facebook));
        facebookLinkBtn.getStyleClass().add("facebook-link-btn");
        facebookLinkBtn.visibleProperty().bind(facebookUrlProperty().isNotEmpty());
        facebookLinkBtn.managedProperty().bind(facebookLinkBtn.visibleProperty());
        facebookUrl.addListener(it -> updateLink(facebookLinkBtn, getFacebookUrl()));

        mailLinkBtn = new Button("Mail", new FontIcon(IkonUtil.mail));
        mailLinkBtn.getStyleClass().add("mail-link-btn");
        mailLinkBtn.visibleProperty().bind(mailUrlProperty().isNotEmpty());
        mailLinkBtn.managedProperty().bind(mailLinkBtn.visibleProperty());
        mailUrl.addListener(it -> updateLink(mailLinkBtn, getMailUrl()));

        InvalidationListener updateViewListener = it -> updateView();

        twitterUrl.addListener(updateViewListener);
        linkedInUrl.addListener(updateViewListener);
        websiteUrl.addListener(updateViewListener);
        githubUrl.addListener(updateViewListener);
        mailUrl.addListener(updateViewListener);
        facebookUrl.addListener(updateViewListener);
        redditUrl.addListener(updateViewListener);

        updateView();
    }

    private void updateView() {
        getChildren().clear();

        if (getTwitterUrl() != null) {
            getChildren().add(twitterLinkBtn);
        }
        if (getLinkedInUrl() != null) {
            getChildren().add(linkedInLinkBtn);
        }
        if (getWebsiteUrl() != null) {
            getChildren().add(websiteLinkBtn);
        }
        if (getGithubUrl() != null) {
            getChildren().add(githubLinkBtn);
        }
        if (getMailUrl() != null) {
            getChildren().add(facebookLinkBtn);
        }
        if (getRedditUrl() != null) {
            getChildren().add(redditLinkBtn);
        }
        if (getMailUrl() != null) {
            getChildren().add(mailLinkBtn);
        }
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

    private final StringProperty facebookUrl = new SimpleStringProperty(this, "facebookUrl");

    public String getFacebookUrl() {
        return facebookUrl.get();
    }

    public StringProperty facebookUrlProperty() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl.set(facebookUrl);
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

    private final StringProperty redditUrl = new SimpleStringProperty(this, "redditUrl");

    public String getRedditUrl() {
        return redditUrl.get();
    }

    public StringProperty redditUrlProperty() {
        return redditUrl;
    }

    public void setRedditUrl(String redditUrl) {
        this.redditUrl.set(redditUrl);
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
