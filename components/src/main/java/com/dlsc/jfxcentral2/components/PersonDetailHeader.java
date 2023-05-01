package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.Badge;
import com.dlsc.jfxcentral2.model.Person;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class PersonDetailHeader extends DetailHeader {

    private static final int AVATAR_SIZE_LG = 134;
    private static final int AVATAR_SIZE_MD = 68;

    public PersonDetailHeader(Person person) {
        this();
        setPerson(person);
    }

    public PersonDetailHeader() {
        getStyleClass().add("person-detail-header");
        centerProperty().bind(Bindings.createObjectBinding(this::createCenterNode, sizeProperty(), personProperty()));

        setOnBack(() -> System.out.println(getClass().getSimpleName() + " back"));
        setOnShare(() -> System.out.println(getClass().getSimpleName() + " share"));
    }

    private Pane createCenterNode() {
        Person person = getPerson();
        if (person == null) {
            return null;
        }
        AvatarView avatarImage = new AvatarView();
        avatarImage.setImage(person.avatar());

        FlowPane nameBadgePane = createNameBadgePane(person);

        Label descriptionLabel = new Label(person.description());
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("description");

        FlowPane socialFlowPane = createSocialFlowPane(person);

        if (isLarge()) {
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

        Label nameLabel = new Label(person.name());
        nameLabel.getStyleClass().add("name");

        nameFlowPane.getChildren().add(nameLabel);
        if (person.badges() != null) {
            for (Badge badge : person.badges()) {
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

        if (person.twitterUrl() != null) {
            Button twitterLinkBtn = new Button("TWITTER", new FontIcon(MaterialDesign.MDI_TWITTER));
            twitterLinkBtn.getStyleClass().addAll("twitter-link-btn","link-button");
            twitterLinkBtn.setOnAction(e -> System.out.println(person.twitterUrl()));
            socialFlowPane.getChildren().add(twitterLinkBtn);
        }

        if (person.linkedInUrl() != null) {
            Button linkedInLinkBtn = new Button("LINKEDIN", new FontIcon(MaterialDesign.MDI_LINKEDIN_BOX));
            linkedInLinkBtn.getStyleClass().addAll("linkedin-link-btn","link-button");
            linkedInLinkBtn.setOnAction(e -> System.out.println(person.linkedInUrl()));
            socialFlowPane.getChildren().add(linkedInLinkBtn);
        }

        if (person.websiteUrl() != null) {
            Button websiteLinkBtn = new Button("WEBSITE", new FontIcon(MaterialDesign.MDI_WEB));
            websiteLinkBtn.getStyleClass().addAll("website-link-btn","link-button");
            websiteLinkBtn.setOnAction(e -> System.out.println(person.websiteUrl()));
            socialFlowPane.getChildren().add(websiteLinkBtn);
        }

        if (person.mailUrl() != null) {
            Button mailLinkBtn = new Button("MAIL", new FontIcon(MaterialDesign.MDI_SEND));
            mailLinkBtn.getStyleClass().addAll("mail-link-btn","link-button");
            mailLinkBtn.setOnAction(e -> System.out.println(person.mailUrl()));
            socialFlowPane.getChildren().add(mailLinkBtn);
        }

        if (person.githubUrl() != null) {
            Button githubLinkBtn = new Button("GITHUB", new FontIcon(MaterialDesign.MDI_GITHUB_CIRCLE));
            githubLinkBtn.getStyleClass().addAll("github-link-btn","link-button");
            githubLinkBtn.setOnAction(e -> System.out.println(person.githubUrl()));
            socialFlowPane.getChildren().add(githubLinkBtn);
        }
        return socialFlowPane;
    }

    private final ObjectProperty<Person> person = new SimpleObjectProperty<>(this, "person");

    public Person getPerson() {
        return person.get();
    }

    public ObjectProperty<Person> personProperty() {
        return person;
    }

    public void setPerson(Person person) {
        this.person.set(person);
    }
}
