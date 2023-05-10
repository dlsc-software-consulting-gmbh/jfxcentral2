package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.filters.AppsFilterView;
import com.dlsc.jfxcentral2.components.filters.BlogsFilterView;
import com.dlsc.jfxcentral2.components.filters.BooksFilterView;
import com.dlsc.jfxcentral2.components.filters.CompaniesFilterView;
import com.dlsc.jfxcentral2.components.filters.DownloadsFilterView;
import com.dlsc.jfxcentral2.components.filters.PeopleFilterView;
import com.dlsc.jfxcentral2.components.filters.PullRequestsFilterView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.filters.TipsAndTricksFilterView;
import com.dlsc.jfxcentral2.components.filters.ToolsFilterView;
import com.dlsc.jfxcentral2.components.filters.TutorialsFilterView;
import com.dlsc.jfxcentral2.components.filters.VideosFilterView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloSearchFilterView extends JFXCentralSampleBase {

    SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);

    @Override
    protected Region createControl() {
        VBox box = new VBox(20);

        VideosFilterView videosFilterView = new VideosFilterView();
        videosFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        AppsFilterView appsFilterView = new AppsFilterView();
        appsFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        PeopleFilterView peopleFilterView = new PeopleFilterView();
        peopleFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        CompaniesFilterView companiesFilterView = new CompaniesFilterView();
        companiesFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        BlogsFilterView blogsFilterView = new BlogsFilterView();
        blogsFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        BooksFilterView booksFilterView = new BooksFilterView();
        booksFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        DownloadsFilterView downloadsFilterView = new DownloadsFilterView();
        downloadsFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        TipsAndTricksFilterView tipsAndTricksFilterView = new TipsAndTricksFilterView();
        tipsAndTricksFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        ToolsFilterView toolsFilterView = new ToolsFilterView();
        toolsFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        TutorialsFilterView tutorialsFilterView = new TutorialsFilterView();
        tutorialsFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        PullRequestsFilterView pullRequestsFilterView = new PullRequestsFilterView();
        pullRequestsFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        box.getChildren().addAll(createTiledBox("Videos", videosFilterView),
                createTiledBox("People", peopleFilterView),
                createTiledBox("Companies", companiesFilterView),
                createTiledBox("App", appsFilterView),
                createTiledBox("Blogs", blogsFilterView),
                createTiledBox("Books", booksFilterView),
                createTiledBox("Downloads", downloadsFilterView),
                createTiledBox("Tips And Tricks", tipsAndTricksFilterView),
                createTiledBox("Tools", toolsFilterView),
                createTiledBox("Tutorials", tutorialsFilterView),
                createTiledBox("Pull Requests", pullRequestsFilterView));

        return new ScrollPane(box);
    }

    private VBox createTiledBox(String title, Node node) {
        VBox box = new VBox(5, new Label(title), node);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    @Override
    public Node getControlPanel() {

        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "(Various) SearchFilterViews";
    }
}
