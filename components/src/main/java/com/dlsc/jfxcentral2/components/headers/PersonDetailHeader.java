package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.model.Badge;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.List;

public class PersonDetailHeader extends DetailHeader {

    private final Person person;

    public PersonDetailHeader(Person person) {
        this.person = person;

        getStyleClass().add("person-detail-header");
        centerProperty().bind(Bindings.createObjectBinding(this::createCenterNode, sizeProperty()));

        setOnBack(() -> System.out.println(getClass().getSimpleName() + " back"));
        setOnShare(() -> System.out.println(getClass().getSimpleName() + " share"));
    }

    private Pane createCenterNode() {
        if (person == null) {
            return null;
        }
        AvatarView avatarImage = new AvatarView();
        avatarImage.imageProperty().bind(ImageManager.getInstance().personImageProperty(person));

        FlowPane nameBadgePane = createNameBadgePane(person);

        Label descriptionLabel = new Label(person.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("description");

        FlowPane socialFlowPane = createSocialFlowPane(person);

        if (!isSmall()) {
            HBox contentBox = new HBox();
            contentBox.getStyleClass().add("content-box");
            VBox infoBox = new VBox(nameBadgePane, descriptionLabel, socialFlowPane);
            infoBox.getStyleClass().add("info-box");
            contentBox.getChildren().addAll(avatarImage, infoBox);
            contentBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            return contentBox;
        } else {
            VBox contentBox = new VBox();
            contentBox.getStyleClass().add("content-box");
            contentBox.getChildren().addAll(avatarImage, nameBadgePane, descriptionLabel, socialFlowPane);
            contentBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            return contentBox;
        }
    }

    private FlowPane createNameBadgePane(Person person) {
        FlowPane nameFlowPane = new FlowPane();
        nameFlowPane.getStyleClass().add("name-flow-pane");

        Label nameLabel = new Label(person.getName());
        nameLabel.getStyleClass().add("name");

        nameFlowPane.getChildren().add(nameLabel);
        List<Badge> badges = Badge.of(person);

        if (!badges.isEmpty()) {
            for (Badge badge : badges) {
                Label badgeLabel = new Label(badge.name(), new FontIcon(badge.icon()));
                badgeLabel.getStyleClass().add("badge");
                nameFlowPane.getChildren().add(badgeLabel);
            }
        }

        return nameFlowPane;
    }

    private FlowPane createSocialFlowPane(Person person) {
        FlowPane socialFlowPane = new FlowPane();
        socialFlowPane.getStyleClass().add("social-flow-pane");

        if (person.getTwitter() != null) {
            Button twitterLinkBtn = new Button("TWITTER", new FontIcon(MaterialDesign.MDI_TWITTER));
            twitterLinkBtn.getStyleClass().addAll("twitter-link-btn","link-button");
            LinkUtil.setExternalLink(twitterLinkBtn, person.getTwitter());
            twitterLinkBtn.setOnAction(e -> System.out.println(person.getTwitter()));
            socialFlowPane.getChildren().add(twitterLinkBtn);
        }

        if (person.getLinkedIn() != null) {
            Button linkedInLinkBtn = new Button("LINKEDIN", new FontIcon(MaterialDesign.MDI_LINKEDIN_BOX));
            linkedInLinkBtn.getStyleClass().addAll("linkedin-link-btn","link-button");
            linkedInLinkBtn.setOnAction(e -> System.out.println(person.getLinkedIn()));
            LinkUtil.setExternalLink(linkedInLinkBtn, person.getTwitter());
            socialFlowPane.getChildren().add(linkedInLinkBtn);
        }

        if (person.getWebsite() != null) {
            Button websiteLinkBtn = new Button("WEBSITE", new FontIcon(MaterialDesign.MDI_WEB));
            websiteLinkBtn.getStyleClass().addAll("website-link-btn","link-button");
            websiteLinkBtn.setOnAction(e -> System.out.println(person.getWebsite()));
            LinkUtil.setExternalLink(websiteLinkBtn, person.getWebsite());
            socialFlowPane.getChildren().add(websiteLinkBtn);
        }

        if (person.getEmail() != null) {
            Button mailLinkBtn = new Button("MAIL", new FontIcon(MaterialDesign.MDI_SEND));
            mailLinkBtn.getStyleClass().addAll("mail-link-btn","link-button");
            mailLinkBtn.setOnAction(e -> System.out.println(person.getEmail()));
            LinkUtil.setExternalLink(mailLinkBtn, person.getEmail());
            socialFlowPane.getChildren().add(mailLinkBtn);
        }

        if (person.getGitHub() != null) {
            Button githubLinkBtn = new Button("GITHUB", new FontIcon(MaterialDesign.MDI_GITHUB_CIRCLE));
            githubLinkBtn.getStyleClass().addAll("github-link-btn","link-button");
            githubLinkBtn.setOnAction(e -> System.out.println(person.getGitHub()));
            LinkUtil.setExternalLink(githubLinkBtn, person.getGitHub());
            socialFlowPane.getChildren().add(githubLinkBtn);
        }
        return socialFlowPane;
    }
}
