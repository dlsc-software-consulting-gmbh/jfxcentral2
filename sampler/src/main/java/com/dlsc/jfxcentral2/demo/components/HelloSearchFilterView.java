package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.AppsFilterView;
import com.dlsc.jfxcentral2.components.BlogsFilterView;
import com.dlsc.jfxcentral2.components.BooksFilterView;
import com.dlsc.jfxcentral2.components.CompaniesFilterView;
import com.dlsc.jfxcentral2.components.DownloadsFilterView;
import com.dlsc.jfxcentral2.components.PeopleFilterView;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.TipsAndTricksFilterView;
import com.dlsc.jfxcentral2.components.ToolsFilterView;
import com.dlsc.jfxcentral2.components.TutorialsFilterView;
import com.dlsc.jfxcentral2.components.VideosFilterView;
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

        box.getChildren().addAll(createTiledBox("Videos", videosFilterView),
                createTiledBox("People", peopleFilterView),
                createTiledBox("Companies", companiesFilterView),
                createTiledBox("App", appsFilterView),
                createTiledBox("Blogs", blogsFilterView),
                createTiledBox("Books", booksFilterView),
                createTiledBox("Downloads", downloadsFilterView),
                createTiledBox("Tips And Tricks", tipsAndTricksFilterView),
                createTiledBox("Tools", toolsFilterView),
                createTiledBox("Tutorials",tutorialsFilterView));

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
