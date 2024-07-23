package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral2.mobile.components.MobileBlogOverviewBox;
import com.dlsc.jfxcentral2.mobile.components.MobilePageHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileBlogDetailsPage extends MobileDetailsPageBase<Blog> {

    public MobileBlogDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Blog.class, itemId);
    }

    @Override
    public List<Node> content() {
        Blog blog = getItem();

        // header
        MobilePageHeader header = new MobilePageHeader();
        header.sizeProperty().bind(sizeProperty());
        header.setTitle(blog.getName());

        // overview
        MobileBlogOverviewBox blogOverviewBox = new MobileBlogOverviewBox(blog);
        blogOverviewBox.sizeProperty().bind(sizeProperty());
        blogOverviewBox.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(blogOverviewBox, Priority.ALWAYS);

        return List.of(header, blogOverviewBox);
    }
}
