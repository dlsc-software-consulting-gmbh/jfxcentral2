package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.JFXCentralUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class FooterView extends PaneBase {

    private final HBox contentBox;
    private final ImageView dukeImageView;
    private final LineNumberPane linksPane;
    private final LineNumberPane legalInfoPane;

    public FooterView() {
        getStyleClass().add("footer-view");
        contentBox = new HBox();
        contentBox.getStyleClass().add("content");
        getChildren().add(contentBox);

        dukeImageView = new ImageView();
        dukeImageView.setPreserveRatio(true);
        dukeImageView.getStyleClass().add("duke-image");
        contentBox.getChildren().add(dukeImageView);

        linksPane = initLinksPane();
        legalInfoPane = initLegalInfoPane();

        setMinWidth(Region.USE_PREF_SIZE);
        layoutBySize();
    }

    public void layoutBySize() {
        dukeImageView.setFitHeight(getSize().isLarge() ? 90 : 61);
        LineNumberPane contactPane = initContactPane();
        Size size = getSize();
        if (size.isLarge() || size.isMedium()) {
            contentBox.getChildren().setAll(dukeImageView, contactPane, linksPane, legalInfoPane);
        } else {
            VBox box = new VBox(contactPane, linksPane, legalInfoPane);
            box.getStyleClass().add("number-pane-box");
            contentBox.getChildren().setAll(dukeImageView, box);
        }
    }

    private LineNumberPane initLegalInfoPane() {
        Hyperlink tcLink = new Hyperlink("T&C");
        tcLink.setOnAction(event -> JFXCentralUtil.run(onTC));
        Hyperlink cookiesLink = new Hyperlink("Cookies");
        cookiesLink.setOnAction(event -> JFXCentralUtil.run(onCookies));
        Hyperlink privacyPolicyLink = new Hyperlink("Privacy policy");
        privacyPolicyLink.setOnAction(event -> JFXCentralUtil.run(onPrivacyPolicy));
        return new LineNumberPane(new Label("Legal info"), null, tcLink, cookiesLink, privacyPolicyLink);
    }

    private LineNumberPane initLinksPane() {
        Hyperlink twitterLink = new Hyperlink("Twitter");
        twitterLink.getStyleClass().addAll("link", "twitter-link");
        twitterLink.setOnAction(event -> JFXCentralUtil.run(onTwitter));

        Hyperlink LinkedinLink = new Hyperlink("Linkedin");
        LinkedinLink.getStyleClass().addAll("link", "linkedin-link");
        LinkedinLink.setOnAction(event -> JFXCentralUtil.run(onLinkedin));

        Hyperlink githubLink = new Hyperlink("Github");
        githubLink.getStyleClass().addAll("link", "github-link");
        githubLink.setOnAction(event -> JFXCentralUtil.run(onGithub));

        Hyperlink youtubeLink = new Hyperlink("Youtube");
        youtubeLink.getStyleClass().addAll("link", "youtube-link");
        youtubeLink.setOnAction(event -> JFXCentralUtil.run(onYoutube));

        LineNumberPane linksPane = new LineNumberPane(new Label("Stay in the loop"), null, twitterLink, LinkedinLink, githubLink, youtubeLink);
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
        emailLink.setOnAction(event -> JFXCentralUtil.run(onSendMail));

        Label contactLabel = new Label("Contact");
        Label dlscLabel = new Label("DLSC Gmbh");
        Label addressLabel = new Label("Asylweg 28,8134 Adliswil,");
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

    private final ObjectProperty<Runnable> onSendMail = new SimpleObjectProperty<>(this, "onSendMail");

    public Runnable getOnSendMail() {
        return onSendMail.get();
    }

    public ObjectProperty<Runnable> onSendMailProperty() {
        return onSendMail;
    }

    public void setOnSendMail(Runnable onSendMail) {
        this.onSendMail.set(onSendMail);
    }

    private final ObjectProperty<Runnable> onCookies = new SimpleObjectProperty<>(this, "onCookies");

    public Runnable getOnCookies() {
        return onCookies.get();
    }

    public ObjectProperty<Runnable> onCookiesProperty() {
        return onCookies;
    }

    public void setOnCookies(Runnable onCookies) {
        this.onCookies.set(onCookies);
    }

    private final ObjectProperty<Runnable> onTwitter = new SimpleObjectProperty<>(this, "onTwitter");

    public Runnable getOnTwitter() {
        return onTwitter.get();
    }

    public ObjectProperty<Runnable> onTwitterProperty() {
        return onTwitter;
    }

    public void setOnTwitter(Runnable onTwitter) {
        this.onTwitter.set(onTwitter);
    }

    private final ObjectProperty<Runnable> onLinkedin = new SimpleObjectProperty<>(this, "onLinkedin");

    public Runnable getOnLinkedin() {
        return onLinkedin.get();
    }

    public ObjectProperty<Runnable> onLinkedinProperty() {
        return onLinkedin;
    }

    public void setOnLinkedin(Runnable onLinkedin) {
        this.onLinkedin.set(onLinkedin);
    }

    private final ObjectProperty<Runnable> onGithub = new SimpleObjectProperty<>(this, "onGithub");

    public Runnable getOnGithub() {
        return onGithub.get();
    }

    public ObjectProperty<Runnable> onGithubProperty() {
        return onGithub;
    }

    public void setOnGithub(Runnable onGithub) {
        this.onGithub.set(onGithub);
    }

    private final ObjectProperty<Runnable> onYoutube = new SimpleObjectProperty<>(this, "onYoutube");

    public Runnable getOnYoutube() {
        return onYoutube.get();
    }

    public ObjectProperty<Runnable> onYoutubeProperty() {
        return onYoutube;
    }

    public void setOnYoutube(Runnable onYoutube) {
        this.onYoutube.set(onYoutube);
    }

    private final ObjectProperty<Runnable> onTC = new SimpleObjectProperty<>(this, "onTC");

    public Runnable getOnTC() {
        return onTC.get();
    }

    public ObjectProperty<Runnable> onTCProperty() {
        return onTC;
    }

    public void setOnTC(Runnable onTC) {
        this.onTC.set(onTC);
    }

    private final ObjectProperty<Runnable> onPrivacyPolicy = new SimpleObjectProperty<>(this, "onPrivacyPolicy");

    public Runnable getOnPrivacyPolicy() {
        return onPrivacyPolicy.get();
    }

    public ObjectProperty<Runnable> onPrivacyPolicyProperty() {
        return onPrivacyPolicy;
    }

    public void setOnPrivacyPolicy(Runnable onPrivacyPolicy) {
        this.onPrivacyPolicy.set(onPrivacyPolicy);
    }
}
