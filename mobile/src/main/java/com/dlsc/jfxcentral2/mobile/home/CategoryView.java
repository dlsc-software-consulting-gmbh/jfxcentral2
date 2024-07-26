package com.dlsc.jfxcentral2.mobile.home;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import org.kordamp.ikonli.javafx.FontIcon;

public class CategoryView extends GridPane {

    private static final String DEFAULT_STYLE_CLASS = "category-view";

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public CategoryView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Button toolsButton = new Button("Tools", new FontIcon(IkonUtil.getModelIkon(Tool.class)));
        toolsButton.getStyleClass().add("tools-button");
        MobileLinkUtil.setLink(toolsButton, PagePath.TOOLS);

        Button videosButton = new Button("Videos", new FontIcon(IkonUtil.getModelIkon(Video.class)));
        videosButton.getStyleClass().add("videos-button");
        MobileLinkUtil.setLink(videosButton, PagePath.VIDEOS);

        Button booksButton = new Button("Books", new FontIcon(IkonUtil.getModelIkon(Book.class)));
        booksButton.getStyleClass().add("books-button");
        MobileLinkUtil.setLink(booksButton, PagePath.BOOKS);

        Button tutorialsButton = new Button("Tutorials", new FontIcon(IkonUtil.getModelIkon(Tutorial.class)));
        tutorialsButton.getStyleClass().add("tutorials-button");
        MobileLinkUtil.setLink(tutorialsButton, PagePath.TUTORIALS);

        Button tipsButton = new Button("Tips", new FontIcon(IkonUtil.getModelIkon(Tip.class)));
        tipsButton.getStyleClass().add("tips-button");
        MobileLinkUtil.setLink(tipsButton, PagePath.TIPS);

        Button blogButton = new Button("Blog", new FontIcon(IkonUtil.getModelIkon(Blog.class)));
        blogButton.getStyleClass().add("blog-button");
        MobileLinkUtil.setLink(blogButton, PagePath.BLOGS);

        Button docButton = new Button("Doc", new FontIcon(IkonUtil.getModelIkon(Documentation.class)));
        docButton.getStyleClass().add("docs-button");
        MobileLinkUtil.setLink(docButton, PagePath.DOCUMENTATION);

        Button companiesButton = new Button("Companies", new FontIcon(IkonUtil.getModelIkon(Company.class)));
        companiesButton.getStyleClass().add("companies-button");
        MobileLinkUtil.setLink(companiesButton, PagePath.COMPANIES);

        // Add the buttons to the grid pane
        addRow(0, toolsButton, videosButton, booksButton, tutorialsButton);
        addRow(1, tipsButton, blogButton, docButton, companiesButton);

        // Set the column and row constraints
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(25);
        columnConstraints.setHalignment(HPos.CENTER);
        getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints, columnConstraints);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(50);
        rowConstraints.setValignment(VPos.CENTER);
        getRowConstraints().addAll(rowConstraints, rowConstraints);

        setMaxHeight(Region.USE_PREF_SIZE);
    }

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

}
