package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.GitHubStarsService;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import javafx.scene.control.Label;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class LibraryTileView extends SimpleTileView<Library> {

    public LibraryTileView(Library library) {
        super(library);

        getStyleClass().add("library-tile-view");

        setAvatarType(AvatarView.Type.PLAIN);

        saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(library));
        saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(library));

        setTitle(library.getName());
        setDescription(library.getDescription());

        imageProperty().bind(ImageManager.getInstance().libraryImageProperty(library));

        String account = library.getGithubAccount();
        String project = library.getGithubProject();

        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(project)) {
            Label starsLabel = new Label();
            starsLabel.getStyleClass().addAll("linked-badge", "github-stars-badge");
            starsLabel.setGraphic(new FontIcon(MaterialDesign.MDI_STAR));
            starsLabel.setVisible(false);
            starsLabel.setManaged(false);
            badgeBox.getChildren().add(starsLabel);

            GitHubStarsService.fetchStarCount(account, project, stars -> {
                starsLabel.setText(GitHubStarsService.formatStarCount(stars));
                starsLabel.setVisible(true);
                starsLabel.setManaged(true);
            });
        }
    }
}
