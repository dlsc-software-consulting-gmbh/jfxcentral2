package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.function.Consumer;

public class BlogsDetailsBox extends DetailsBoxBase<Blog> {
    public BlogsDetailsBox() {
        getStyleClass().add("blogs-details-box");
        setTitle("BLOGS");
        setIkon(IkonUtil.blog);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getName());
        });

        setOnVisitBlog(detailsObject -> {
            System.out.println("On Visit Blog: " + detailsObject.getName());
        });

    }

    @Override
    protected List<Node> createActionButtons(Blog model) {
        Button visitBlogButton = new Button("VISIT BLOG", new FontIcon(IkonUtil.link));
        visitBlogButton.managedProperty().bind(visitBlogButton.visibleProperty());
        visitBlogButton.visibleProperty().bind(onVisitBlogProperty().isNotNull());
        visitBlogButton.setOnAction(evt -> {
            if (getOnVisitBlog() != null) {
                getOnVisitBlog().accept(model);
            }
        });

        return List.of(createDetailsButton(model), visitBlogButton);
    }

    private final ObjectProperty<Consumer<Blog>> onVisitBlog = new SimpleObjectProperty<>(this, "onVisitBlog");

    public Consumer<Blog> getOnVisitBlog() {
        return onVisitBlog.get();
    }

    public ObjectProperty<Consumer<Blog>> onVisitBlogProperty() {
        return onVisitBlog;
    }

    public void setOnVisitBlog(Consumer<Blog> onVisitBlog) {
        this.onVisitBlog.set(onVisitBlog);
    }

}
