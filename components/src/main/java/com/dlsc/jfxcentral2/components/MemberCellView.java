package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Member;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.apache.commons.lang3.StringUtils;

public class MemberCellView extends PaneBase {
    private final SocialLinksView socialLinksView;
    private final CustomMarkdownView descriptionMd;
    private final Label nameLabel;
    private final Label jobTitleLabel;
    private final StackPane avatarWrapper;

    public MemberCellView(Member member) {
        getStyleClass().add("member-cell-view");

        nameLabel = new Label();
        nameLabel.getStyleClass().add("name-label");
        nameLabel.setText(member.getName());

        jobTitleLabel = new Label();
        jobTitleLabel.getStyleClass().add("job-title-label");
        jobTitleLabel.setText(member.getJobTitle());

        descriptionMd = new CustomMarkdownView();
        descriptionMd.setMdString(DataRepository2.getInstance().getMemberReadMe(member));

        socialLinksView = new SocialLinksView();

        if (StringUtils.isNotBlank(member.getTwitter())) {
            socialLinksView.setTwitterUrl("https://www.twitter.com/" + member.getTwitter());
        }

        socialLinksView.setMastodonUrl(member.getMastodon());
        socialLinksView.setWebsiteUrl(member.getWebsite());

        if (StringUtils.isNotBlank(member.getLinkedIn())) {
            socialLinksView.setLinkedInUrl("https://www.linkedin.com/in/" + member.getLinkedIn());
        }

        if (StringUtils.isNotBlank(member.getEmail())) {
            socialLinksView.setMailUrl("mailto:" + member.getEmail());
        }

        if (StringUtils.isNotBlank(member.getGitHub())) {
            socialLinksView.setGithubUrl("https://www.github.com/" + member.getGitHub());
        }

        AvatarView avatar = new AvatarView();
        avatar.imageProperty().bind(ImageManager.getInstance().memberImageProperty(member));

        Circle effectCircle = new Circle();
        effectCircle.getStyleClass().add("effect-circle");
        effectCircle.radiusProperty().bind(avatar.avatarSizeProperty().divide(2));

        avatarWrapper = new StackPane(effectCircle, avatar);
        avatarWrapper.getStyleClass().add("avatar-wrapper");
        avatarWrapper.maxWidthProperty().bind(avatar.avatarSizeProperty());
        avatarWrapper.maxHeightProperty().bind(avatar.avatarSizeProperty());

        updarteUI();
    }

    @Override
    protected void layoutBySize() {
        if (!isLgToMdOrMdToLg()) {
            updarteUI();
        }
    }

    private void updarteUI() {
        if (isLarge() || isMedium()) {
            HBox contentBox = new HBox();
            contentBox.getStyleClass().add("content-box");

            VBox centerBox = new VBox(nameLabel, jobTitleLabel, descriptionMd, socialLinksView);
            centerBox.getStyleClass().add("center-box");
            HBox.setHgrow(centerBox, Priority.ALWAYS);
            contentBox.getChildren().setAll(avatarWrapper, centerBox);
            getChildren().setAll(contentBox);
        } else {
            VBox contentBox = new VBox();
            contentBox.getStyleClass().add("content-box");

            contentBox.getChildren().setAll(avatarWrapper, nameLabel, jobTitleLabel, socialLinksView, descriptionMd);
            getChildren().setAll(contentBox);
        }
    }
}
