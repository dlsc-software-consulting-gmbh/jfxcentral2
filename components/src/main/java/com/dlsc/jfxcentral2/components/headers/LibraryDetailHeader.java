package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.utils.GitHubStarsService;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class LibraryDetailHeader extends SimpleDetailHeader<Library> {

    private final String githubAccount;
    private final String githubProject;
    private long cachedStarCount = -1;

    public LibraryDetailHeader(Library library) {
        super(library);
        getStyleClass().add("library-detail-header");

        imageProperty().bind(ImageManager.getInstance().libraryImageProperty(library));

        setWebsite(StringUtils.isNotBlank(getModel().getRepository()) ? getModel().getRepository() : getModel().getHomepage());
        setWebsiteButtonText(StringUtils.isNotBlank(getModel().getRepository()) ? "REPOSITORY" : "WEBSITE");
        setWebsiteButtonIcon(StringUtils.isNotBlank(getModel().getRepository()) ? IkonUtil.github : IkonUtil.website);
        setShareUrl("libraries/" + library.getId());
        setShareText("Found this library on @JFXCentral: " + library.getName());
        setShareTitle("Library: " + library.getName());
        setBackText("ALL LIBRARIES");
        setBackUrl(PagePath.LIBRARIES);

        githubAccount = library.getGithubAccount();
        githubProject = library.getGithubProject();

        if (StringUtils.isNotBlank(githubAccount) && StringUtils.isNotBlank(githubProject)) {
            GitHubStarsService.fetchStarCount(githubAccount, githubProject, stars -> {
                cachedStarCount = stars;
                Button btn = getWebsiteButton();
                if (btn != null) {
                    applyStarsGraphic(btn, stars);
                }
            });
        }
    }

    @Override
    protected void configureWebsiteButton(Button button) {
        if (cachedStarCount >= 0) {
            applyStarsGraphic(button, cachedStarCount);
        }
    }

    private void applyStarsGraphic(Button button, long stars) {
        FontIcon repoIcon = new FontIcon(IkonUtil.github);
        repoIcon.getStyleClass().add("repo-icon");

        FontIcon starIcon = new FontIcon(MaterialDesign.MDI_STAR);
        starIcon.getStyleClass().add("star-icon");

        Label countLabel = new Label(formatStarCount(stars));
        countLabel.getStyleClass().add("stars-count");

        HBox graphic = new HBox(repoIcon, starIcon, countLabel);
        graphic.getStyleClass().add("repo-button-graphic");
        graphic.setAlignment(Pos.CENTER);

        button.graphicProperty().unbind();
        button.setGraphic(graphic);
    }

    private String formatStarCount(long stars) {
        return GitHubStarsService.formatStarCount(stars);
    }
}
