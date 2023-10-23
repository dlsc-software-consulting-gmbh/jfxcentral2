package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.SocialLinksView;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.OSUtil;
import com.jpro.webapi.WebAPI;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import one.jpro.platform.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class DetailHeader<T extends ModelObject> extends CategoryHeader {

    private T model;

    public DetailHeader(T model) {
        this.model = Objects.requireNonNull(model);

        getStyleClass().add("detail-header");

        setTitle(model.getName());
        setIkon(IkonUtil.getModelIkon(model));

        BorderPane contentPane = new BorderPane();
        contentPane.getStyleClass().add("content-pane");
        contentPane.centerProperty().bind(centerProperty());
        contentPane.setBottom(createBottomBox());

        setContent(contentPane);
    }

    public T getModel() {
        return model;
    }

    private HBox createBottomBox() {
        Button backButton = new Button();
        backButton.visibleProperty().bind(backUrlProperty().isNotEmpty());
        backButton.managedProperty().bind(backUrlProperty().isNotEmpty());
        backButton.textProperty().bind(backTextProperty());
        backButton.setGraphic(new FontIcon(Material.ARROW_BACK_IOS));
        backButton.getStyleClass().addAll("back-button");
        backUrlProperty().addListener(it -> LinkUtil.setLink(backButton, getBackUrl()));

        SocialLinksView socialLinksView = new SocialLinksView();

        CustomMenuItem customMenuItem = new CustomMenuItem(socialLinksView);
        customMenuItem.setHideOnClick(false);

        MenuButton menuButton = new MenuButton("SHARE", new FontIcon(JFXCentralIcon.SHARE));
        menuButton.setFocusTraversable(false);
        menuButton.getStyleClass().add("share-button");
        if (!WebAPI.isBrowser() && !OSUtil.isAndroidOrIOS()) {
            menuButton.getStyleClass().add("standalone");
        }
        menuButton.getItems().addAll(customMenuItem);

        InvalidationListener updateShareButtonLinks = it -> updateShareButton(socialLinksView);

        shareUrlProperty().addListener(updateShareButtonLinks);
        shareTextProperty().addListener(updateShareButtonLinks);
        shareTitleProperty().addListener(updateShareButtonLinks);

        HBox bottomBox = new HBox(backButton, new Spacer(), menuButton);
        bottomBox.getStyleClass().add("bottom-box");
        return bottomBox;
    }

    private void updateShareButton(SocialLinksView socialLinksView) {
        String shareText = getShareText();
        String shareUrl = getShareUrl();
        String shareTitle = getShareTitle();

        if (StringUtils.isNotBlank(shareUrl) && StringUtils.isNotBlank(shareText) && StringUtils.isNotBlank(shareTitle)) {
            String url = URLEncoder.encode("https://www.jfx-central.com/" + shareUrl, StandardCharsets.UTF_8);
            String title = URLEncoder.encode(shareTitle, StandardCharsets.UTF_8);
            String titleWithBody = URLEncoder.encode(shareTitle + " " + shareText, StandardCharsets.UTF_8);
            String body = URLEncoder.encode(shareText + " ", StandardCharsets.UTF_8); // extra space for proper formatting on twitter
            String bodyWithUrl = URLEncoder.encode(shareText + " https://www.jfx-central.com/" + shareUrl, StandardCharsets.UTF_8);

            socialLinksView.setFacebookUrl("https://www.facebook.com/sharer/sharer.php?u=" + url + "&t=" + titleWithBody);
            socialLinksView.setTwitterUrl("https://twitter.com/share?text=" + body + "&url=" + url + "&hashtags=javafx,java,ux,ui");
            socialLinksView.setLinkedInUrl("https://www.linkedin.com/shareArticle?mini=false&url=" + url + "&title=" + title + "&summary=" + body);
            socialLinksView.setMailUrl("mailto:?subject=" + title.replace("+", "%20") + "&body=" + body.replace("+", "%20"));
            socialLinksView.setRedditUrl("https://www.reddit.com/r/JavaFX/submit?title=" + title + "&selftext=true&text=" + bodyWithUrl + "&link=" + url);
        }
    }

    private final StringProperty backUrl = new SimpleStringProperty(this, "backUrl");

    public String getBackUrl() {
        return backUrl.get();
    }

    public StringProperty backUrlProperty() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl.set(backUrl);
    }

    private final StringProperty backText = new SimpleStringProperty(this, "backText", "BACK");

    public String getBackText() {
        return backText.get();
    }

    public StringProperty backTextProperty() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText.set(backText);
    }

    private final StringProperty shareTitle = new SimpleStringProperty(this, "shareTitle");

    public String getShareTitle() {
        return shareTitle.get();
    }

    public StringProperty shareTitleProperty() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle.set(shareTitle);
    }

    private final StringProperty shareText = new SimpleStringProperty(this, "shareText");

    public String getShareText() {
        return shareText.get();
    }

    public StringProperty shareTextProperty() {
        return shareText;
    }

    public void setShareText(String shareText) {
        this.shareText.set(shareText);
    }

    private final StringProperty shareUrl = new SimpleStringProperty(this, "shareUrl");

    public String getShareUrl() {
        return shareUrl.get();
    }

    public StringProperty shareUrlProperty() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl.set(shareUrl);
    }

    private final ObjectProperty<Node> center = new SimpleObjectProperty<>(this, "center");

    public Node getCenter() {
        return center.get();
    }

    public ObjectProperty<Node> centerProperty() {
        return center;
    }

    public void setCenter(Node center) {
        this.center.set(center);
    }
}
