package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.detailsbox.AppsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.BlogsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.BooksDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.CompaniesDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.DownloadsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.LibrariesDetailsBox;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.detailsbox.TipsAndTricksDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.ToolsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.VideosDetailsBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.details.AppsDetailsObject;
import com.dlsc.jfxcentral2.model.details.BlogsDetailsObject;
import com.dlsc.jfxcentral2.model.details.BooksDetailsObject;
import com.dlsc.jfxcentral2.model.details.CompaniesDetailsObject;
import com.dlsc.jfxcentral2.model.details.DetailsObject;
import com.dlsc.jfxcentral2.model.details.DownloadsDetailsObject;
import com.dlsc.jfxcentral2.model.details.LibrariesDetailsObject;
import com.dlsc.jfxcentral2.model.details.TipsAndTricksDetailsObject;
import com.dlsc.jfxcentral2.model.details.ToolsDetailsObject;
import com.dlsc.jfxcentral2.model.details.VideosDetailsObject;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

public class HelloDetailsBox extends JFXCentralSampleBase {

    private SizeComboBox sizeComboBox = new SizeComboBox();

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

    private VideosDetailsBox createVideosDetailsBox() {
        VideosDetailsBox videosDetailsBox = new VideosDetailsBox();
        videosDetailsBox.getItems().addAll(
                new VideosDetailsObject(
                        "Video Title",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/video-thumbnail-01.png").toExternalForm()),
                        "5 mins",
                        false,
                        true
                ),
                new VideosDetailsObject(
                        "Video Title",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/video-thumbnail-01.png").toExternalForm()),
                        "5 mins",
                        false,
                        true
                ),
                new VideosDetailsObject(
                        "Video Title",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/video-thumbnail-01.png").toExternalForm()),
                        "5 mins",
                        false,
                        true
                ),
                new VideosDetailsObject(
                        "Video Title",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/video-thumbnail-01.png").toExternalForm()),
                        "5 mins",
                        false,
                        true
                ),
                new VideosDetailsObject(
                        "Video Title",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/video-thumbnail-01.png").toExternalForm()),
                        "5 mins",
                        false,
                        true
                ),
                new VideosDetailsObject(
                        "Video Title",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/video-thumbnail-01.png").toExternalForm()),
                        "5 mins",
                        false,
                        true
                ),
                new VideosDetailsObject(
                        "Video Title",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/video-thumbnail-01.png").toExternalForm()),
                        "5 mins",
                        false,
                        true
                ),
                new VideosDetailsObject(
                        "Video Title",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/video-thumbnail-01.png").toExternalForm()),
                        "5 mins",
                        false,
                        true
                ),
                new VideosDetailsObject(
                        "Video Title",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/video-thumbnail-01.png").toExternalForm()),
                        "5 mins",
                        false,
                        true
                )

        );
        return videosDetailsBox;
    }

    private CompaniesDetailsBox createCompaniesDetailsBox() {
        CompaniesDetailsBox companiesDetailsBox = new CompaniesDetailsBox();
        companiesDetailsBox.getItems().addAll(
                new CompaniesDetailsObject(
                        "Company name",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit", false),
                        new Image(companiesDetailsBox.getClass().getResource("/com/dlsc/jfxcentral2/components/logos/jpro.png").toExternalForm()),
                        false,
                        true)
        );
        return companiesDetailsBox;
    }

    private TipsAndTricksDetailsBox createTipsAndTricksDetailsBox() {
        TipsAndTricksDetailsBox tipsAndTricksDetailsBox = new TipsAndTricksDetailsBox();
        tipsAndTricksDetailsBox.getItems().addAll(
                new TipsAndTricksDetailsObject(
                        "Lorem ipsum dolor sit amet",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/tips-tricks-thumbnail-01.png").toExternalForm()),
                        false,
                        true),
                new TipsAndTricksDetailsObject(
                        "Lorem ipsum dolor sit amet",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/tips-tricks-thumbnail-01.png").toExternalForm()),
                        true,
                        false),
                new TipsAndTricksDetailsObject(
                        "Lorem ipsum dolor sit amet",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/tips-tricks-thumbnail-01.png").toExternalForm()),
                        true,
                        true),
                new TipsAndTricksDetailsObject(
                        "Lorem ipsum dolor sit amet",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/tips-tricks-thumbnail-01.png").toExternalForm()),
                        false,
                        false),
                new TipsAndTricksDetailsObject(
                        "Lorem ipsum dolor sit amet",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/tips-tricks-thumbnail-01.png").toExternalForm()),
                        false,
                        false),
                new TipsAndTricksDetailsObject(
                        "Lorem ipsum dolor sit amet",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/tips-tricks-thumbnail-01.png").toExternalForm()),
                        true,
                        false)
        );
        return tipsAndTricksDetailsBox;
    }

    private BooksDetailsBox createBooksDetailsBox() {
        BooksDetailsBox booksDetailsBox = new BooksDetailsBox();
        booksDetailsBox.getItems().addAll(
                new BooksDetailsObject(
                        "Book Title",
                        new DetailsObject.Description(
                                """
                                        For the purposes of this Privacy Policy:

                                        - __Account__ means a unique account created for You to access our Service or parts of our Service.
                                        - __Affiliate__ means an entity that controls, is controlled by or is under common control with a party, where "control" means ownership of 50% or more of the shares, equity interest or other securities entitled to vote for election of directors or other managing authority.
                                        - __Company__ (referred to as either "the Company", "We", "Us" or "Our" in this Agreement) refers to DLSC GmbH, Asylweg 28, 8134 Adliswil.
                                        - __Cookies__ are small files that are placed on Your computer, mobile device or any other device by a website, containing the details of Your browsing history on that website among its many uses.
                                        - __Country__ refers to:  Switzerland
                                        - __Device__ means any device that can access the Service such as a computer, a cellphone or a digital tablet.
                                        - __Personal Data__ is any information that relates to an identified or identifiable individual.
                                        - __Service__ refers to the Website.
                                        - __Service Provider__ means any natural or legal person who processes the data on behalf of the Company. It refers to third-party companies or individuals employed by the Company to facilitate the Service, to provide the Service on behalf of the Company, to perform services related to the Service or to assist the Company in analyzing how the Service is used.
                                        """
                                , true),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/book-thumbnail-01.png").toExternalForm()),
                        false,
                        true),
                new BooksDetailsObject(
                        "Book Title",
                        new DetailsObject.Description("2Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/book-thumbnail-01.png").toExternalForm()),
                        false,
                        true),
                new BooksDetailsObject(
                        "Book Title",
                        new DetailsObject.Description("3Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/book-thumbnail-01.png").toExternalForm()),
                        false,
                        true)
        );

        return booksDetailsBox;
    }

    private AppsDetailsBox createAppsDetailsBox() {
        AppsDetailsBox appsDetailsBox = new AppsDetailsBox();
        appsDetailsBox.getItems().addAll(
                new AppsDetailsObject(
                        "App Name",
                        new DetailsObject.Description("1Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                        false,
                        true),
                new AppsDetailsObject(
                        "App Name",
                        new DetailsObject.Description("2Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-main-preview0.png").toExternalForm()),
                        false,
                        true
                ),
                new AppsDetailsObject(
                        "App Name",
                        new DetailsObject.Description("3Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                        true,
                        true),
                new AppsDetailsObject(
                        "App Name",
                        new DetailsObject.Description("4Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-main-preview0.png").toExternalForm()),
                        false,
                        false
                ),
                new AppsDetailsObject(
                        "App Name",
                        new DetailsObject.Description("5Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                        true,
                        false),
                new AppsDetailsObject(
                        "App Name",
                        new DetailsObject.Description("6Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false),
                        new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-main-preview0.png").toExternalForm()),
                        false,
                        true
                )
        );
        return appsDetailsBox;
    }

    private LibrariesDetailsBox createLibrariesDetailsBox() {
        LibrariesDetailsBox librariesDetailsBox = new LibrariesDetailsBox();
        librariesDetailsBox.getItems().addAll(
                new LibrariesDetailsObject(
                        null,
                        new Image(LibrariesDetailsBox.class.getResource("/com/dlsc/jfxcentral2/components/logos/navielektro.png").toExternalForm()),
                        new DetailsObject.Description("1Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", false),
                        List.of(
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm())
                        ),
                        false,
                        true),
                new LibrariesDetailsObject(
                        "Library Name",
                        null,
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false),
                        List.of(
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm())
                        ),
                        false,
                        true)
        );

        return librariesDetailsBox;
    }

    private DownloadsDetailsBox createDownloadsDetailsBox() {
        DownloadsDetailsBox downloadsDetailsBox = new DownloadsDetailsBox();
        downloadsDetailsBox.getItems().addAll(
                new DownloadsDetailsObject(
                        "Extreme GUI Makeover"
                        , new DetailsObject.Description("1Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false)
                        , new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm())
                        , false, true),
                new DownloadsDetailsObject(
                        "Extreme GUI Makeover"
                        , new DetailsObject.Description("2Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.", false)
                        , new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-main-preview0.png").toExternalForm())
                        , true, false),
                new DownloadsDetailsObject(
                        "Extreme GUI Makeover"
                        , new DetailsObject.Description("3Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false)
                        , new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm())
                        , false, true),
                new DownloadsDetailsObject(
                        "Extreme GUI Makeover"
                        , new DetailsObject.Description("4Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false)
                        , new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm())
                        , false, true),
                new DownloadsDetailsObject(
                        "Extreme GUI Makeover"
                        , new DetailsObject.Description("5Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.", false)
                        , new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-main-preview0.png").toExternalForm())
                        , true, false),
                new DownloadsDetailsObject(
                        "Extreme GUI Makeover"
                        , new DetailsObject.Description("6Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false)
                        , new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm())
                        , false, true),
                new DownloadsDetailsObject(
                        "Extreme GUI Makeover"
                        , new DetailsObject.Description("7Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false)
                        , new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm())
                        , false, true),
                new DownloadsDetailsObject(
                        "Extreme GUI Makeover"
                        , new DetailsObject.Description("8Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.", false)
                        , new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-main-preview0.png").toExternalForm())
                        , true, false),
                new DownloadsDetailsObject(
                        "Extreme GUI Makeover"
                        , new DetailsObject.Description("9Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false)
                        , new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm())
                        , false, true)

        );

        return downloadsDetailsBox;
    }

    private BlogsDetailsBox createBlogsDetailsBox() {
        BlogsDetailsBox blogsDetailsBox = new BlogsDetailsBox();
        blogsDetailsBox.getItems().addAll(
                new BlogsDetailsObject(
                        "Pixel Perfect"
                        , new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", false)
                        , false, true)
        );

        return blogsDetailsBox;
    }

    private ToolsDetailsBox creatToolDetailsBox() {
        ToolsDetailsBox detailsBox = new ToolsDetailsBox();
        detailsBox.getItems().addAll(
                new ToolsDetailsObject(
                        "Calendar FX",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", false),
                        List.of(
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm())),
                        true,
                        false
                )

                , new ToolsDetailsObject(
                        "FlexGanttFX",
                        new DetailsObject.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", false),
                        List.of(
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm()),
                                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/details-box-preview0.png").toExternalForm())),
                        false,
                        false
                )
        );
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
