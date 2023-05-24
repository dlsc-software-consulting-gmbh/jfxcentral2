package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Post;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.jpro.webapi.WebAPI;
import com.rometools.rome.feed.synd.SyndEntry;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import one.jpro.routing.LinkUtil;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

public class BlogOverviewBox extends OverviewBox<Blog> {

    public BlogOverviewBox(Blog blog) {
        super(blog);

        getStyleClass().add("blog-overview-box");

        setTitle("Recent posts found on " + blog.getName());
        setIcon(IkonUtil.getModelIkon(Blog.class));
    }

    @Override
    protected Node createTopNode() {
        VBox box = new VBox();
        box.getStyleClass().add("posts-box");
        DataRepository.getInstance().loadPosts(getModel()).forEach(post -> box.getChildren().add(new PostView(post)));
        return box;
    }

    public static class PostView extends HBox {

        private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

        public PostView(Post post) {
            getStyleClass().add("post-view");

            setPrefWidth(0);

            Label titleLabel = new Label(post.getSyndEntry().getTitle());
            titleLabel.getStyleClass().add("title-label");
            titleLabel.setMaxWidth(Double.MAX_VALUE);
            titleLabel.setWrapText(true);
            titleLabel.setMinHeight(Region.USE_PREF_SIZE);

            ImageView imageView = new ImageView();
            imageView.setFitHeight(32);
            imageView.setFitWidth(32);
            imageView.setPreserveRatio(true);
            imageView.imageProperty().bind(ImageManager.getInstance().blogIconImageProperty(post.getBlog()));

            Label ageLabel = new Label(getAge(post));
            ageLabel.getStyleClass().add("age-label");
            ageLabel.setMinWidth(Region.USE_PREF_SIZE);

            getChildren().setAll(imageView, titleLabel, ageLabel);
            HBox.setHgrow(titleLabel, Priority.ALWAYS);

            if (WebAPI.isBrowser()) {
                LinkUtil.setExternalLink(this, post.getSyndEntry().getLink());
            } else {
                setOnMouseClicked(evt -> {
                    try {
                        Desktop.getDesktop().browse(URI.create(post.getSyndEntry().getLink()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        private String getAge(Post post) {
            SyndEntry syndEntry = post.getSyndEntry();
            Date date = syndEntry.getUpdatedDate();
            if (date == null) {
                date = syndEntry.getPublishedDate();
            }

            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            Duration between = Duration.between(zonedDateTime, ZonedDateTime.now());

            long days = between.toDays();
            if (days <= 0) {
                return between.toHours() + " hours";
            } else if (days < 100) {
                return days + (days > 1 ? " days" : "day");
            }

            return dateTimeFormatter.format(zonedDateTime);
        }
    }
}
