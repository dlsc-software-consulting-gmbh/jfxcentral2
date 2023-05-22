package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.News;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.components.Spacer;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SimpleTileView: PersonTileView; ToolTileView; BlogTileView
 *
 * @param <T>
 */
public class SimpleTileView<T extends ModelObject> extends TileViewBase<T> {
    protected HBox badgeBox = new HBox();
    protected SaveAndLikeButton saveAndLikeButton;
    private final HBox linkedObjectBox;

    public SimpleTileView() {
        getStyleClass().addAll("simple-tile-view");

        AvatarView avatarView = new AvatarView();
        avatarView.imageProperty().bind(imageProperty());

        Label nameLabel = new Label();
        nameLabel.getStyleClass().add("title");
        nameLabel.textProperty().bind(titleProperty());

        Button detailButton = new Button();
        detailButton.getStyleClass().add("detail-button");
        detailButton.setGraphic(new FontIcon(IkonUtil.link));

        badgeBox.getStyleClass().add("badge-box");

        HBox topBox = new HBox(nameLabel, badgeBox, new Spacer(), detailButton);
        topBox.getStyleClass().add("top-box");

        Label descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.textProperty().bind(descriptionProperty());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(descriptionLabel, Priority.ALWAYS);

        linkedObjectBox = new HBox();
        linkedObjectBox.getStyleClass().add("linked-object-box");

        HBox bottomBox = new HBox(linkedObjectBox, new Spacer());
        List<Node> extraNodes = createExtraNodes();
        if (extraNodes != null) {
            bottomBox.getChildren().addAll(extraNodes);
        }
        bottomBox.getStyleClass().add("bottom-box");

        VBox leftBox = new VBox(topBox, descriptionLabel, bottomBox);
        leftBox.getStyleClass().add("left-box");
        HBox.setHgrow(leftBox, Priority.ALWAYS);

        HBox contentBox = new HBox(avatarView, leftBox);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);

    }

    protected List<Node> createExtraNodes() {
        saveAndLikeButton = new SaveAndLikeButton();
        saveSelectedProperty().addListener((ob, ov, nv) -> saveAndLikeButton.setSaveButtonSelected(nv));
        likeSelectedProperty().addListener((ob, ov, nv) -> saveAndLikeButton.setLikeButtonSelected(nv));
        saveAndLikeButton.saveButtonSelectedProperty().addListener((ob, ov, saved) -> {
            setSaveSelected(saved);
            if (getData() != null) {
                System.out.println((saved ? "SELECTED: " : "UNSELECTED: ") + getData().getName());
            }
        });

        saveAndLikeButton.likeButtonSelectedProperty().addListener((ob, ov, liked) -> {
            setLikeSelected(liked);
            if (getData() != null) {
                System.out.println((liked ? "LIKED: " : "UNLIKED: ") + getData().getName());
            }
        });

        return List.of(saveAndLikeButton);
    }

    protected void updateLinkedObjectBadges() {
        //add linked objects
        linkedObjectBox.getChildren().clear();
        T data = getData();
        if (data == null) {
            return;
        }

        DataRepository dataRepository = DataRepository.getInstance();
        List<LinkedObjectBadge> linkedObjectBadge = List.of(
                new LinkedObjectBadge("Books", IkonUtil.getModelIkon(Book.class), dataRepository.getLinkedObjects(data, Book.class).size()),
                new LinkedObjectBadge("Apps", IkonUtil.getModelIkon(RealWorldApp.class), dataRepository.getLinkedObjects(data, Book.class).size()),
                new LinkedObjectBadge("Libraries", IkonUtil.getModelIkon(Library.class), dataRepository.getLinkedObjects(data, Book.class).size()),
                new LinkedObjectBadge("Tutorials", IkonUtil.getModelIkon(Tutorial.class), dataRepository.getLinkedObjects(data, Book.class).size()),
                new LinkedObjectBadge("Downloads", IkonUtil.getModelIkon(Download.class), dataRepository.getLinkedObjects(data, Book.class).size()),
                new LinkedObjectBadge("Tools", IkonUtil.getModelIkon(Tool.class), dataRepository.getLinkedObjects(data, Book.class).size()),
                new LinkedObjectBadge("Videos", IkonUtil.getModelIkon(Video.class), dataRepository.getLinkedObjects(data, Book.class).size()),
                new LinkedObjectBadge("Companies", IkonUtil.getModelIkon(Company.class), dataRepository.getLinkedObjects(data, Book.class).size()),
                new LinkedObjectBadge("Blogs", IkonUtil.getModelIkon(Blog.class), dataRepository.getLinkedObjects(data, Book.class).size()),
                new LinkedObjectBadge("Tips", IkonUtil.getModelIkon(Tip.class), dataRepository.getLinkedObjects(data, Book.class).size()),
                new LinkedObjectBadge("News", IkonUtil.getModelIkon(News.class), dataRepository.getLinkedObjects(data, Book.class).size()),
                new LinkedObjectBadge("Links", IkonUtil.getModelIkon(LinksOfTheWeek.class), dataRepository.getLinkedObjects(data, Book.class).size())
        );

        AtomicInteger index = new AtomicInteger(0);
        int maxSize = isLarge() ? 3 : 2;
        linkedObjectBadge.stream().filter(item -> item.count() > 0).limit(maxSize).forEach(item -> {
            Label label = new Label(item.name() + "(" + item.count() + ")");
            label.getStyleClass().add("linked-badge");
            linkedObjectBox.getChildren().add(label);
            if (index.getAndIncrement() > maxSize - 2) {
                label.setText("...");
                label.getStyleClass().add("linked-badge-more");
            } else {
                label.setGraphic(new FontIcon(item.ikon));
            }
        });
    }

    public record LinkedObjectBadge(String name, Ikon ikon, int count) {
    }

}
