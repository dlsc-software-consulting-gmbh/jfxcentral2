package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.News;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.Post;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Utility;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;

public class SearchResultCell extends ListCell<ModelObject> {

    private final FontIcon fontIcon = new FontIcon();
    private final Label titleLabel = new Label();
    private final Label subtitleLabel = new Label();

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
        }
    }

    private String createTitle(ModelObject item) {
        return getTypePrefix(item.getClass()) + ": " + item.getName();
    }

    private String getTypePrefix(Class<? extends ModelObject> clazz) {
        if (clazz.equals(Tip.class)) {
            return "Tip";
        } else if (clazz.equals(RealWorldApp.class)) {
            return "Showcase";
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
        } else if (clazz.equals(IkonliPack.class)) {
            return "Icon Pack";
        } else if (clazz.equals(Utility.class)) {
            return "Utility";
        } else if (clazz.equals(LearnJavaFX.class) || clazz.equals(LearnMobile.class) || clazz.equals(LearnRaspberryPi.class)) {
            return "Learn";
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
        } else if (item instanceof IkonliPack) {
            return item.getDescription();
        } else if (item instanceof Learn) {
            return StringUtils.replace(item.getClass().getSimpleName(), "Learn", "Learn ");
        } else {
            return item.getSummary();
        }
    }
}
