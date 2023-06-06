package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.filters.BlogsFilterView;
import com.dlsc.jfxcentral2.components.filters.BooksFilterView;
import com.dlsc.jfxcentral2.components.filters.CompaniesFilterView;
import com.dlsc.jfxcentral2.components.filters.DownloadsFilterView;
import com.dlsc.jfxcentral2.components.filters.IkonliIconsFilter;
import com.dlsc.jfxcentral2.components.filters.IkonliPacksFilter;
import com.dlsc.jfxcentral2.components.filters.LibrariesFilterView;
import com.dlsc.jfxcentral2.components.filters.PeopleFilterView;
import com.dlsc.jfxcentral2.components.filters.PullRequestsFilterView;
import com.dlsc.jfxcentral2.components.filters.ShowcaseFilterView;
import com.dlsc.jfxcentral2.components.filters.TipsAndTricksFilterView;
import com.dlsc.jfxcentral2.components.filters.ToolsFilterView;
import com.dlsc.jfxcentral2.components.filters.TutorialsFilterView;
import com.dlsc.jfxcentral2.components.filters.VideosFilterView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloSearchFilterView extends JFXCentralSampleBase {

    private final SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);

    @Override
    protected Region createControl() {
        VBox box = new VBox(20);

        VideosFilterView videosFilterView = new VideosFilterView();
        videosFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        IkonliIconsFilter ikonliIconsFilter = new IkonliIconsFilter();
        ikonliIconsFilter.sizeProperty().bind(sizeComboBox.valueProperty());

        IkonliPacksFilter ikonliPacksFilter = new IkonliPacksFilter();
        ikonliPacksFilter.sizeProperty().bind(sizeComboBox.valueProperty());

        ShowcaseFilterView appsFilterView = new ShowcaseFilterView();
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

        LibrariesFilterView librariesFilterView = new LibrariesFilterView();
        librariesFilterView.sizeProperty().bind(sizeComboBox.valueProperty());

        box.getChildren().addAll(
                createTitledBox("Videos", videosFilterView),
                createTitledBox("Filter Icon", ikonliIconsFilter),
                createTitledBox("Filter Pack", ikonliPacksFilter),
                createTitledBox("People", peopleFilterView),
                createTitledBox("Companies", companiesFilterView),
                createTitledBox("App", appsFilterView),
                createTitledBox("Blogs", blogsFilterView),
                createTitledBox("Books", booksFilterView),
                createTitledBox("Downloads", downloadsFilterView),
                createTitledBox("Tips And Tricks", tipsAndTricksFilterView),
                createTitledBox("Tools", toolsFilterView),
                createTitledBox("Tutorials", tutorialsFilterView),
                createTitledBox("Libraries", librariesFilterView),
                createTitledBox("Pull Requests", pullRequestsFilterView));

        return new ScrollPane(box);
    }

    private VBox createTitledBox(String title, Node node) {
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
