package com.dlsc.jfxcentral2.demo.components.tileview;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral2.components.tiles.BlogTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.List;

public class HelloBlogTileView extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        Blog blog = new Blog();
        blog.setName("Pixel Perfect");
        blog.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor ");
        blog.setBookIds(List.of("Book1", "Book2"));
        blog.setAppIds(List.of("App1", "App2"));
        BlogTileView blogTileView = new BlogTileView(blog);
        return new StackPane(blogTileView);
    }

    @Override
    public String getSampleName() {
        return "BlogTileView";
    }
}
