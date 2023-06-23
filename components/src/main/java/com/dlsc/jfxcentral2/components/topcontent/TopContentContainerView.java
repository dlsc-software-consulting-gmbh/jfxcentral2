package com.dlsc.jfxcentral2.components.topcontent;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.layout.VBox;

public class TopContentContainerView extends PaneBase {

    public TopContentContainerView() {
        getStyleClass().add("top-content-container-view");

        TopContentView<Tool> toolsTop = new TopContentView<>();
        getStyleClass().add("tools-top-view");
        toolsTop.sizeProperty().bind(sizeProperty());
        toolsTop.setTitle("Tools");
        toolsTop.setIcon(IkonUtil.tool);
        toolsTop.getItems().setAll(DataRepository2.getInstance().getTools());

        TopContentView<Library> librariesTop = new TopContentView<>();
        getStyleClass().add("libraries-top");
        librariesTop.sizeProperty().bind(sizeProperty());
        librariesTop.setTitle("Libraries");
        librariesTop.setIcon(IkonUtil.library);
        librariesTop.getItems().setAll(DataRepository2.getInstance().getLibraries());

        TopContentView<Blog> blogsTop = new TopContentView<>();
        getStyleClass().add("blogs-top");
        blogsTop.sizeProperty().bind(sizeProperty());
        blogsTop.setTitle("Blogs");
        blogsTop.setIcon(IkonUtil.blog);
        blogsTop.getItems().setAll(DataRepository2.getInstance().getBlogs());

        TopContentView<Video> videosTop = new TopContentView<>();
        getStyleClass().add("videos-top");
        videosTop.sizeProperty().bind(sizeProperty());
        videosTop.setTitle("Videos");
        videosTop.setIcon(IkonUtil.video);
        videosTop.getItems().setAll(DataRepository2.getInstance().getVideos());

        TopContentView<Download> downloadTop = new TopContentView<>();
        getStyleClass().add("downloads-top");
        downloadTop.sizeProperty().bind(sizeProperty());
        downloadTop.setTitle("Downloads");
        downloadTop.setIcon(IkonUtil.download);
        downloadTop.getItems().setAll(DataRepository2.getInstance().getDownloads());

        TopContentView<Book> booksTop = new TopContentView<>();
        getStyleClass().add("books-top");
        booksTop.sizeProperty().bind(sizeProperty());
        booksTop.setTitle("Books");
        booksTop.setIcon(IkonUtil.book);
        booksTop.getItems().setAll(DataRepository2.getInstance().getBooks());

        TopContentView<Tip> tipsTop = new TopContentView<>();
        getStyleClass().add("tips-top");
        tipsTop.sizeProperty().bind(sizeProperty());
        tipsTop.setTitle("Tips");
        tipsTop.setIcon(IkonUtil.tip);
        tipsTop.getItems().setAll(DataRepository2.getInstance().getTips());

        TopContentView<Company> companiesTop = new TopContentView<>();
        getStyleClass().add("companies-top");
        companiesTop.sizeProperty().bind(sizeProperty());
        companiesTop.setTitle("Companies");
        companiesTop.setIcon(IkonUtil.company);
        companiesTop.getItems().setAll(DataRepository2.getInstance().getCompanies());

        VBox containerBox = new VBox(toolsTop, librariesTop, blogsTop, videosTop, downloadTop, booksTop, tipsTop, companiesTop);
        containerBox.getStyleClass().add("container-box");
        getChildren().setAll(containerBox);
    }


}
