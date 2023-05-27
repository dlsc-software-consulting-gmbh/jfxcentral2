package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.News;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.Post;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.PageUtil;
import com.jpro.webapi.WebAPI;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import one.jpro.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;

public class SearchResultCell extends ListCell<ModelObject> {

    private FontIcon fontIcon = new FontIcon();
    private Label titleLabel = new Label();
    private Label subtitleLabel = new Label();

    public SearchResultCell() {
        getStyleClass().add("search-result-list-cell");

        VBox vBox = new VBox(titleLabel, subtitleLabel);
        vBox.getStyleClass().add("vbox");
        HBox.setHgrow(vBox, Priority.ALWAYS);

        StackPane iconWrapper = new StackPane(fontIcon);
        iconWrapper.getStyleClass().add("icon-wrapper");

        HBox hBox = new HBox(iconWrapper, vBox);
        hBox.getStyleClass().add("hbox");

        titleLabel.getStyleClass().add("title-label");
        subtitleLabel.getStyleClass().add("subtitle-label");

        setPrefWidth(0);

        hBox.visibleProperty().bind(itemProperty().isNotNull());
        setGraphic(hBox);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    @Override
    protected void updateItem(ModelObject item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty && item != null) {
            titleLabel.setText(createTitle(item));
            subtitleLabel.setText(createSubTitle(item));
            fontIcon.setIconCode(IkonUtil.getModelIkon(item));

            if (WebAPI.isBrowser()) {
                LinkUtil.setLink(this, PageUtil.getLink(item));
            } else {

                // work around ... setLink() should work after a fix from Florian
                setOnMouseClicked(evt -> {
                    getScene().getWindow().hide();
                    LinkUtil.getSessionManager(this).gotoURL(PageUtil.getLink(item));
                });
            }
        }
    }

    private String createTitle(ModelObject item) {
        return getTypePrefix(item.getClass()) + ": " + item.getName();
    }

    private String getTypePrefix(Class<? extends ModelObject> clazz) {
        if (clazz.equals(Tip.class)) {
            return "Tip";
        } else if (clazz.equals(RealWorldApp.class)) {
            return "Real World App";
        } else if (clazz.equals(Person.class)) {
            return "Person";
        } else if (clazz.equals(Company.class)) {
            return "Company";
        } else if (clazz.equals(Blog.class)) {
            return "Blog";
        } else if (clazz.equals(Video.class)) {
            return "Video";
        } else if (clazz.equals(Book.class)) {
            return "Book";
        } else if (clazz.equals(Tool.class)) {
            return "Tool";
        } else if (clazz.equals(Library.class)) {
            return "Library";
        } else if (clazz.equals(Tutorial.class)) {
            return "Tutorial";
        } else if (clazz.equals(Download.class)) {
            return "Download";
        } else if (clazz.equals(News.class)) {
            return "News";
        } else if (clazz.equals(Post.class)) {
            return "Post";
        } else {
            return "Item";
        }
    }

    private String createSubTitle(ModelObject item) {
        if (item instanceof Book) {
            return ((Book) item).getSubtitle();
        } else if (item instanceof Person) {
            return ((Person) item).getWebsite();
        } else if (item instanceof News) {
            return ((News) item).getSubtitle();
        } else if (item instanceof Video) {
            String description = item.getDescription();
            if (StringUtils.isNotBlank(description)) {
                return description.substring(0, Math.min(100, description.length())).replace("\n", " ");
            }
            return "";
        } else if (item instanceof Download) {
            return "Download";
        } else {
            return item.getSummary();
        }
    }
}
