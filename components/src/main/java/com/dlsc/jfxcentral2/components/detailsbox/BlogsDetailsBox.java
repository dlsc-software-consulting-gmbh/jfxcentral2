package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral2.model.BlogsDetailsObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.List;
import java.util.function.Consumer;

public class BlogsDetailsBox extends DetailsBoxBase<BlogsDetailsObject> {
    public BlogsDetailsBox() {
        getStyleClass().add("blogs-details-box");
        setTitle("BLOGS");
        setIkon(MaterialDesign.MDI_NEWSPAPER);

        setOnDetails(detailsObject -> {
            System.out.println("On Details: " + detailsObject.getTitle());
        });

        setOnVisitBlog(detailsObject -> {
            System.out.println("On Visit Blog: " + detailsObject.getTitle());
        });

    }

    @Override
    protected List<Node> createActionButtons(BlogsDetailsObject model) {
        Button visitBlogButton = new Button("VISIT BLOG", new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT));
        visitBlogButton.managedProperty().bind(visitBlogButton.visibleProperty());
        visitBlogButton.visibleProperty().bind(onVisitBlogProperty().isNotNull());
        visitBlogButton.setOnAction(evt -> {
            if (getOnVisitBlog() != null) {
                getOnVisitBlog().accept(model);
            }
        });

        return List.of(createDetailsButton(model), visitBlogButton);
    }

    private final ObjectProperty<Consumer<BlogsDetailsObject>> onVisitBlog = new SimpleObjectProperty<>(this, "onVisitBlog");

    public Consumer<BlogsDetailsObject> getOnVisitBlog() {
        return onVisitBlog.get();
    }

    public ObjectProperty<Consumer<BlogsDetailsObject>> onVisitBlogProperty() {
        return onVisitBlog;
    }

    public void setOnVisitBlog(Consumer<BlogsDetailsObject> onVisitBlog) {
        this.onVisitBlog.set(onVisitBlog);
    }

}
