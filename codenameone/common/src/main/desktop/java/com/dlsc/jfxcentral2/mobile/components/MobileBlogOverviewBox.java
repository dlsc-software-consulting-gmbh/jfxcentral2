package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral2.utils.CellGraphics;
import com.dlsc.jfxcentral2.utils.WrapLabel;

import com.dlsc.jfxcentral2.icons.MaterialIcon;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Post;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import com.dlsc.jfxcentral2.utils.StringUtil;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.dlsc.jfxcentral2.icons.FontIcon;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class MobileBlogOverviewBox extends VBox {

    private final SizeSupport sizeSupport = new SizeSupport(this);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");

    public MobileBlogOverviewBox(Blog blog) {
        getStyleClass().add("mobile-blog-overview-box");

        // top
        Header header = new Header();
        header.setTitle("Recent posts");
        header.setIcon(null);

        // center
        Label loadingTipsLabel = new Label(StringUtil.LOADING_TIPS, new FontIcon(MaterialIcon.REFRESH));
        loadingTipsLabel.getStyleClass().add("loading-label");

        ListView<Post> listView = new ListView<>();
        listView.setPlaceholder(loadingTipsLabel);
        listView.getStyleClass().addAll("posts-list", "mobile");
        listView.setCellFactory(param -> new PostViewCell());
        listView.setMaxHeight(Double.MAX_VALUE);
        listView.prefHeightProperty().bind(Bindings.createDoubleBinding(() -> {
                    return listView.getItems().size() * listView.getFixedCellSize();
                },
                Bindings.size(listView.getItems()), listView.fixedCellSizeProperty()));

        StackPane listWrapper = new StackPane(listView);
        listWrapper.getStyleClass().add("list-wrapper");

        VBox.setVgrow(listWrapper, Priority.ALWAYS);
        getChildren().addAll(header, listWrapper);

        Service<Void> service = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws InterruptedException {
                        List<Post> posts = DataRepository.getInstance().loadPosts(blog);
                        posts.sort((o1, o2) -> compareDates(o2, o1));
                        Thread.sleep(500);
                        Platform.runLater(() -> {
                            listView.getItems().setAll(posts);
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
    }


    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    private static class PostViewCell extends ListCell<Post> {

        private final WrapLabel titleLabel;
        private final Label timeLabel;
        private final HBox contentBox;

        public PostViewCell() {
            getStyleClass().add("post-view-cell");
            setPrefWidth(0);

            titleLabel = new WrapLabel();
            titleLabel.getStyleClass().add("title-label");
            titleLabel.setMaxWidth(Double.MAX_VALUE);
            titleLabel.setMinHeight(Region.USE_PREF_SIZE);

            timeLabel = new Label();
            timeLabel.getStyleClass().add("time-label");
            timeLabel.setMinWidth(Region.USE_PREF_SIZE);

            contentBox = new HBox(titleLabel, timeLabel);
            CellGraphics.fitToCell(this, contentBox);
            contentBox.getStyleClass().add("content-box");
            HBox.setHgrow(titleLabel, Priority.ALWAYS);
        }

        @Override
        protected void updateItem(Post item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                titleLabel.setText(null);
                timeLabel.setText(null);
                setText(null);
                setGraphic(null);
            } else {
                titleLabel.setText(item.getTitle());
                timeLabel.setText(getTimeSincePublished(item));
                setGraphic(contentBox);

                ExternalLinkUtil.setExternalLink(this, item.getLink());
            }
        }
    }

    private static int compareDates(Post a, Post b) {
        Date da = a.getPublishedDate();
        Date db = b.getPublishedDate();
        if (da == null || db == null) {
            return da == null ? (db == null ? 0 : -1) : 1;
        }
        return da.compareTo(db);
    }

    private static String getTimeSincePublished(Post post) {
        Date date = post.getPublishedDate();
        if (date == null) {
            return "";
        }

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        Duration between = Duration.between(zonedDateTime, ZonedDateTime.now());

        long days = between.toDays();
        if (days <= 0) {
            return between.toHours() + " hours";
        } else if (days < 100) {
            return days + (days > 1 ? " days" : " day");
        }

        return dateTimeFormatter.format(zonedDateTime);
    }

}
