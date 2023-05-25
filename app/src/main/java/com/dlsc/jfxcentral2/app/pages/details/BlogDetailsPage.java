package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.headers.BlogDetailHeader;
import com.dlsc.jfxcentral2.components.overviewbox.BlogOverviewBox;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class BlogDetailsPage extends DetailsPageBase<Blog> {

    public BlogDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, Blog.class, itemId);
    }

    @Override
    public Node content() {
        Blog blog = getItem();

        // header
        BlogDetailHeader header = new BlogDetailHeader(blog);
        header.sizeProperty().bind(sizeProperty());

        // overview
        BlogOverviewBox blogOverviewBox = new BlogOverviewBox(blog);
        blogOverviewBox.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(blogOverviewBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(header, detailsContentPane);
    }
}
