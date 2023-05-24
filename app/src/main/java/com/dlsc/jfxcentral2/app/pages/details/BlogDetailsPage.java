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

        // header
        BlogDetailHeader header = new BlogDetailHeader(getItem());
        header.sizeProperty().bind(sizeProperty());

        // details
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(new BlogOverviewBox(getItem()));
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());

        return wrapContent(header, detailsContentPane);
    }
}
