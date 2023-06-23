package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Member;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.StringUtils;

public class MemberCellView extends PaneBase {

    private static final DropShadow DEFAULT_EFFECT = new DropShadow(BlurType.GAUSSIAN, Color.rgb(5, 0, 0, 0.35), 10, 0.3, 0, 0);

    private final AvatarView avatar;
    private final SocialLinksView socialLinksView;
    private final CustomMarkdownView descriptionMd;
    private final Label nameLabel;
    private final Label jobTitleLabel;

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

        avatar = new AvatarView();
        avatar.setEffect(DEFAULT_EFFECT);
        avatar.imageProperty().bind(ImageManager.getInstance().memberImageProperty(member));
    }

    @Override
    protected void layoutBySize() {
        if (isLarge() || isMedium()) {
            HBox contentBox = new HBox();
            contentBox.getStyleClass().add("content-box");

            VBox centerBox = new VBox(nameLabel, jobTitleLabel, descriptionMd, socialLinksView);
            centerBox.getStyleClass().add("center-box");
            HBox.setHgrow(centerBox, Priority.ALWAYS);
            contentBox.getChildren().setAll(avatar, centerBox);
            getChildren().setAll(contentBox);
        } else {
            VBox contentBox = new VBox();
            contentBox.getStyleClass().add("content-box");

            contentBox.getChildren().setAll(avatar, nameLabel, jobTitleLabel, socialLinksView, descriptionMd);
            getChildren().setAll(contentBox);
        }

    }
}
