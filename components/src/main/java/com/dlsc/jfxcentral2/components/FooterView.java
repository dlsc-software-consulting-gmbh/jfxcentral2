package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.LinkUtil;

public class FooterView extends PaneBase {

    private final HBox contentBox;
    private final ImageView logoImageView;
    private final LineNumberPane linksPane;
    private final LineNumberPane legalInfoPane;

    public FooterView() {
        getStyleClass().add("footer-view");
        contentBox = new HBox();
        contentBox.getStyleClass().add("content");
        contentBox.setAlignment(Pos.TOP_RIGHT);

        getChildren().add(contentBox);

        logoImageView = new ImageView();
        logoImageView.setPreserveRatio(true);
        logoImageView.getStyleClass().addAll("jfx-central-logo", "small", "color");
        contentBox.getChildren().add(logoImageView);

        linksPane = initLinksPane();
        legalInfoPane = initLegalInfoPane();

        HBox.setHgrow(logoImageView, Priority.ALWAYS);
        HBox.setHgrow(linksPane, Priority.ALWAYS);
        HBox.setHgrow(legalInfoPane, Priority.ALWAYS);

        setMinWidth(Region.USE_PREF_SIZE);
        layoutBySize();
    }

    public void layoutBySize() {
        logoImageView.setFitHeight(getSize().isLarge() ? 90 : 61);
        LineNumberPane contactPane = initContactPane();
        HBox.setHgrow(contactPane, Priority.ALWAYS);

        Size size = getSize();
        if (size.isLarge()) {
            contentBox.getChildren().setAll(logoImageView, new Spacer(), contactPane, linksPane, legalInfoPane);
        } else if (size.isMedium()) {
            contentBox.getChildren().setAll(contactPane, linksPane, legalInfoPane);
        } else {
            VBox box = new VBox(contactPane, linksPane, legalInfoPane);
            box.getStyleClass().add("number-pane-box");
            contentBox.getChildren().setAll(box);
        }
    }

    private LineNumberPane initLegalInfoPane() {
        Hyperlink tcLink = new Hyperlink("T&C");
        Hyperlink cookiesLink = new Hyperlink("Cookies");
        Hyperlink privacyPolicyLink = new Hyperlink("Privacy policy");
        Hyperlink creditsLink = new Hyperlink("Credits");

        LinkUtil.setLink(tcLink, PagePath.LEGAL_TERMS);
        LinkUtil.setLink(cookiesLink, PagePath.LEGAL_COOKIES);
        LinkUtil.setLink(privacyPolicyLink, PagePath.LEGAL_PRIVACY);
        LinkUtil.setLink(creditsLink, PagePath.CREDITS);

        return new LineNumberPane(new Label("Legal info"), null, tcLink, cookiesLink, privacyPolicyLink, creditsLink);
    }

    private LineNumberPane initLinksPane() {
        Hyperlink blueskyLink = new Hyperlink("Bluesky");
        blueskyLink.getStyleClass().addAll("link", "bluesky-link");
        ExternalLinkUtil.setExternalLink(blueskyLink, "https://bsky.app/profile/jfxcentral.com");

        Hyperlink linkedinLink = new Hyperlink("Linkedin");
        linkedinLink.getStyleClass().addAll("link", "linkedin-link");
        ExternalLinkUtil.setExternalLink(linkedinLink, "https://www.linkedin.com/in/dlemmermann/");

        Hyperlink githubLink = new Hyperlink("Github");
        githubLink.getStyleClass().addAll("link", "github-link");
        ExternalLinkUtil.setExternalLink(githubLink, "https://github.com/dlemmermann");

        Hyperlink youtubeLink = new Hyperlink("Youtube");
        youtubeLink.getStyleClass().addAll("link", "youtube-link");
        ExternalLinkUtil.setExternalLink(youtubeLink, "https://www.youtube.com/@dlsc/videos");

        LineNumberPane linksPane = new LineNumberPane(new Label("Stay in the loop"), null, blueskyLink, linkedinLink, githubLink, youtubeLink);
        linksPane.getStyleClass().add("links-pane");
        return linksPane;
    }

    private LineNumberPane initContactPane() {
        Label phoneLabel = new Label("Phone = ");
        phoneLabel.getStyleClass().add("purple-label");

        Label emaillabel = new Label("Email = ");
        emaillabel.getStyleClass().add("purple-label");

        HBox phoneBox = new HBox(phoneLabel, new Label("+41-79-800-23-20"));
        phoneBox.getStyleClass().add("phone-box");
        phoneBox.setAlignment(Pos.CENTER_LEFT);

        Hyperlink emailLink = new Hyperlink("dlemmermann@gmail.com");
        emailLink.getStyleClass().add("link");
        LinkUtil.setLink(emailLink, "mailto:dlemmermann@gmail.com");

        Label contactLabel = new Label("Contact");
        Label dlscLabel = new Label("DLSC Software & Consulting GmbH");
        Label addressLabel = new Label("Albisstrasse 3, 8134 Adliswil");
        Label countryLabel = new Label("Switzerland");
        LineNumberPane contactPane;
        if (getSize().isLarge()) {
            HBox hBox = new HBox(emaillabel, emailLink);
            hBox.setAlignment(Pos.CENTER_LEFT);
            contactPane = new LineNumberPane(contactLabel, null, dlscLabel, addressLabel, countryLabel, null, phoneBox, hBox);
        } else {
            contactPane = new LineNumberPane(contactLabel, null, dlscLabel, addressLabel, countryLabel, phoneBox, emaillabel, emailLink);
        }
        contactPane.getStyleClass().add("contact-pane");

        return contactPane;
    }

    private static class LineNumberPane extends GridPane {

        public LineNumberPane(Node... nodes ) {
            getStyleClass().add("line-number-pane");
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setHalignment(HPos.RIGHT);

            ColumnConstraints col2 = new ColumnConstraints();
            col2.setHalignment(HPos.LEFT);
            getChildren().clear();
            getColumnConstraints().clear();

            for (int i = 0; i < nodes.length; i++) {
                Label label = new Label(String.valueOf(i + 1));
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                label.getStyleClass().addAll("number-label", "number-label-" + i);
                RowConstraints row = new RowConstraints();
                row.setValignment(VPos.CENTER);
                getRowConstraints().add(row);
                add(label, 0, i);
            }
            for (int i = 0; i < nodes.length; i++) {
                Node node = nodes[i];
                if (node == null) {
                    continue;
                }
                node.getStyleClass().addAll("content-line", "content-line-" + i);
                add(node, 1, i);
            }
        }
    }
}
