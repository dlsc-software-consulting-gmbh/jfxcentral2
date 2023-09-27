package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.util.Callback;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class BlogsDetailsBox extends DetailsBoxBase<Blog> {

    public BlogsDetailsBox() {
        getStyleClass().add("blogs-details-box");

        setTitle("BLOGS");
        setIkon(IkonUtil.getModelIkon(Blog.class));
        setVisitUrlProvider(Blog::getUrl);
    }

    @Override
    protected List<Node> createActionButtons(Blog blog) {
        Button visitBlogButton = new Button("VISIT BLOG", new FontIcon(IkonUtil.link));
        visitBlogButton.setFocusTraversable(false);
        visitBlogButton.managedProperty().bind(visitBlogButton.visibleProperty());
        visitBlogButton.visibleProperty().bind(visitUrlProviderProperty().isNotNull());
        LinkUtil.setExternalLink(visitBlogButton, getVisitUrlProvider().call(blog));
        return List.of(createDetailsButton(blog), visitBlogButton);
    }

    private final ObjectProperty<Callback<Blog, String>> visitUrlProvider = new SimpleObjectProperty<>(this, "visitUrlProvider");

    public Callback<Blog, String> getVisitUrlProvider() {
        return visitUrlProvider.get();
    }

    public ObjectProperty<Callback<Blog, String>> visitUrlProviderProperty() {
        return visitUrlProvider;
    }

    public void setVisitUrlProvider(Callback<Blog, String> visitUrlProvider) {
        this.visitUrlProvider.set(visitUrlProvider);
    }
}
