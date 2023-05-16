package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.detailsbox.AppsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.BlogsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.BooksDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.CompaniesDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.DownloadsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.LibrariesDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.TipsAndTricksDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.ToolsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.VideosDetailsBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Random;

public class HelloDetailsBox extends JFXCentralSampleBase {

    private SizeComboBox sizeComboBox = new SizeComboBox();
    private Random random = new Random();

    @Override
    protected Region createControl() {
        ToolsDetailsBox toolsBox = creatToolDetailsBox();
        toolsBox.sizeProperty().bind(sizeComboBox.valueProperty());

        BlogsDetailsBox blogsBox = createBlogsDetailsBox();
        blogsBox.sizeProperty().bind(sizeComboBox.valueProperty());

        DownloadsDetailsBox downloadsBox = createDownloadsDetailsBox();
        downloadsBox.sizeProperty().bind(sizeComboBox.valueProperty());

        LibrariesDetailsBox librariesBox = createLibrariesDetailsBox();
        librariesBox.sizeProperty().bind(sizeComboBox.valueProperty());

        VideosDetailsBox videosBox = createVideosDetailsBox();
        videosBox.sizeProperty().bind(sizeComboBox.valueProperty());

        AppsDetailsBox appsBox = createAppsDetailsBox();
        appsBox.sizeProperty().bind(sizeComboBox.valueProperty());

        BooksDetailsBox booksBox = createBooksDetailsBox();
        booksBox.sizeProperty().bind(sizeComboBox.valueProperty());

        TipsAndTricksDetailsBox tipsAndTricksBox = createTipsAndTricksDetailsBox();
        tipsAndTricksBox.sizeProperty().bind(sizeComboBox.valueProperty());

        CompaniesDetailsBox companiesBox = createCompaniesDetailsBox();
        companiesBox.sizeProperty().bind(sizeComboBox.valueProperty());

        return new ScrollPane(new VBox(15,
                toolsBox, blogsBox, downloadsBox,
                librariesBox, appsBox, videosBox,
                booksBox, tipsAndTricksBox, companiesBox));
    }

    private Video createVideo() {
        Video video = new Video();
        video.setName("Video Title");
        video.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        video.setMinutes(random.nextInt(5, 30));
        return video;
    }

    private VideosDetailsBox createVideosDetailsBox() {
        VideosDetailsBox videosDetailsBox = new VideosDetailsBox();
        videosDetailsBox.getItems().addAll(
                createVideo(), createVideo(), createVideo(), createVideo(), createVideo(), createVideo(), createVideo(), createVideo(), createVideo()
        );
        return videosDetailsBox;
    }

    private Company createCompany() {
        Company company = new Company();
        company.setName("Company name");
        company.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        return company;
    }

    private CompaniesDetailsBox createCompaniesDetailsBox() {
        CompaniesDetailsBox companiesDetailsBox = new CompaniesDetailsBox();
        companiesDetailsBox.getItems().addAll(createCompany());
        return companiesDetailsBox;
    }

    private Tip createTip() {
        Tip tip = new Tip();
        tip.setName("Lorem ipsum dolor sit amet");
        tip.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        return tip;
    }

    private TipsAndTricksDetailsBox createTipsAndTricksDetailsBox() {
        TipsAndTricksDetailsBox tipsAndTricksDetailsBox = new TipsAndTricksDetailsBox();
        tipsAndTricksDetailsBox.getItems().addAll(createTip(), createTip(), createTip(), createTip(), createTip(), createTip());
        return tipsAndTricksDetailsBox;
    }

    private Book createBook() {
        Book book = new Book();
        book.setName("Book Title");

        book.setDescription("""
                For the purposes of this Privacy Policy:

                - __Account__ means a unique account created for You to access our Service or parts of our Service.
                - __Company__ (referred to as either "the Company", "We", "Us" or "Our" in this Agreement) refers to DLSC GmbH, Asylweg 28, 8134 Adliswil.
                - __Country__ refers to:  Switzerland
                - __Device__ means any device that can access the Service such as a computer, a cellphone or a digital tablet.
                - __Personal Data__ is any information that relates to an identified or identifiable individual.
                - __Service__ refers to the Website.
                """);
        return book;
    }

    private BooksDetailsBox createBooksDetailsBox() {
        BooksDetailsBox booksDetailsBox = new BooksDetailsBox();
        booksDetailsBox.getItems().addAll(createBook(), createBook(), createBook());

        return booksDetailsBox;
    }

    private RealWorldApp createApp() {
        RealWorldApp app = new RealWorldApp();
        app.setName("App Name");
        app.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        return app;
    }

    private AppsDetailsBox createAppsDetailsBox() {
        AppsDetailsBox appsDetailsBox = new AppsDetailsBox();
        appsDetailsBox.getItems().addAll(createApp(), createApp(), createApp(), createApp(), createApp(), createApp());
        return appsDetailsBox;
    }

    private Library createLibrary() {
        Library library = new Library();
        library.setName("Library Name");
        library.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        return library;
    }

    private LibrariesDetailsBox createLibrariesDetailsBox() {
        LibrariesDetailsBox librariesDetailsBox = new LibrariesDetailsBox();
        librariesDetailsBox.getItems().addAll(createLibrary(), createLibrary());
        return librariesDetailsBox;
    }

    private Download createDownload() {
        Download download = new Download();
        download.setName("Extreme GUI Makeover");
        download.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        return download;
    }

    private DownloadsDetailsBox createDownloadsDetailsBox() {
        DownloadsDetailsBox downloadsDetailsBox = new DownloadsDetailsBox();
        downloadsDetailsBox.getItems().addAll(createDownload(), createDownload(), createDownload(), createDownload(), createDownload(), createDownload(), createDownload(), createDownload(), createDownload());

        return downloadsDetailsBox;
    }

    private Blog createBlog() {
        Blog blog = new Blog();
        blog.setName("Pixel Perfect");
        blog.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        return blog;
    }
    private BlogsDetailsBox createBlogsDetailsBox() {
        BlogsDetailsBox blogsDetailsBox = new BlogsDetailsBox();
        blogsDetailsBox.getItems().addAll(createBlog());
        return blogsDetailsBox;
    }

    private Tool createTool() {
        Tool tool = new Tool();
        tool.setName("Calendar FX");
        tool.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        return tool;
    }
    private ToolsDetailsBox creatToolDetailsBox() {
        ToolsDetailsBox detailsBox = new ToolsDetailsBox();
        detailsBox.getItems().addAll(createTool(), createTool());
        return detailsBox;
    }

    @Override
    public Node getControlPanel() {
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "DetailsBox";
    }
}
