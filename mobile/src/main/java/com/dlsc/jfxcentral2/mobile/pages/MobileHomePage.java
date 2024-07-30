package com.dlsc.jfxcentral2.mobile.pages;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.MobileSearchTextField;
import com.dlsc.jfxcentral2.components.MobilePageBase;

import com.dlsc.jfxcentral2.mobile.components.LearnCategoryBox;
import com.dlsc.jfxcentral2.mobile.components.MobileSearchView;
import com.dlsc.jfxcentral2.mobile.home.CategoryAdvancedView;
import com.dlsc.jfxcentral2.mobile.home.CategoryPreviewView;
import com.dlsc.jfxcentral2.mobile.home.HomePageHeader;
import com.dlsc.jfxcentral2.mobile.home.WeekLinksView;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MobileHomePage extends MobilePageBase {

    private static MobileHomePage instance;

    private final MobileSearchTextField searchTextField;

    public enum ContentType {
        NORMAL, SEARCH
    }

    public static synchronized MobileHomePage getInstance() {
        if (instance == null) {
            instance = new MobileHomePage();
        }
        return instance;
    }

    private MobileHomePage() {
        getStyleClass().add("mobile-home-page");

        // content
        Node normalView = createNormalView();
        normalView.managedProperty().bind(normalView.visibleProperty());
        VBox.setVgrow(normalView, Priority.ALWAYS);

        MobileSearchView searchView = new MobileSearchView(sizeProperty());
        searchView.managedProperty().bind(searchView.visibleProperty());
        searchView.visibleProperty().bind(Bindings.createBooleanBinding(() -> getContentType() == ContentType.SEARCH, contentTypeProperty()));
        normalView.visibleProperty().bind(searchView.visibleProperty().not());
        VBox.setVgrow(searchView, Priority.ALWAYS);

        // header
        HomePageHeader header = new HomePageHeader();
        header.sizeProperty().bind(sizeProperty());

        // search field
        searchTextField = new MobileSearchTextField();
        searchTextField.setRight(createSearchCancelButton());
        searchTextField.setPromptText("Search for anything...");
        searchView.searchTextProperty().bindBidirectional(searchTextField.textProperty());
        searchTextField.setOnMousePressed(event -> setContentType(ContentType.SEARCH));
        searchTextField.textProperty().addListener(it -> setContentType(ContentType.SEARCH));

        HBox.setHgrow(searchTextField, Priority.ALWAYS);
        HBox searchWrapper = new HBox(searchTextField);
        searchWrapper.getStyleClass().add("search-wrapper");

        getChildren().addAll(header, searchWrapper, normalView, searchView);

        setViewWillAppear(()-> setContentType(ContentType.NORMAL));
    }

    private Button createSearchCancelButton() {
        Button button = new Button("Search");
        button.textProperty().bind(Bindings.createStringBinding(() -> {
            if (getContentType() == ContentType.SEARCH) {
                return "Cancel";
            } else {
                return "Search";
            }
        }, contentTypeProperty()));
        button.setOnAction(event -> {
            if (getContentType() == ContentType.SEARCH) {
                searchTextField.clear();
                setContentType(ContentType.NORMAL);
            } else {
                setContentType(ContentType.SEARCH);
            }
        });
        return button;
    }

    private Node createNormalView() {
        CategoryAdvancedView categoryAdvancedView = new CategoryAdvancedView();
        categoryAdvancedView.sizeProperty().bind(sizeProperty());

        LearnCategoryBox learnCategoryBox = new LearnCategoryBox();

        List<LinksOfTheWeek> linksOfTheWeek = DataRepository2.getInstance().getLinksOfTheWeek();

        WeekLinksView weekLinksView = new WeekLinksView();
        weekLinksView.sizeProperty().bind(sizeProperty());
        weekLinksView.setLinksOfTheWeek(linksOfTheWeek.get(linksOfTheWeek.size() - 1));

        List<RealWorldApp> randomApps = getRandomSample(DataRepository2.getInstance().getRealWorldApps(), 3);
        CategoryPreviewView showCasePreviewView = CategoryPreviewView.createShowCasePreviewView(randomApps, PagePath.SHOWCASES);
        showCasePreviewView.sizeProperty().bind(sizeProperty());

        List<Tip> randomTips = getRandomSample(DataRepository2.getInstance().getTips(), 2);
        CategoryPreviewView tipsPreviewView = CategoryPreviewView.createTipsPreviewView(randomTips, PagePath.TIPS);
        tipsPreviewView.sizeProperty().bind(sizeProperty());

        List<Person> randomPeople = getRandomSample(DataRepository2.getInstance().getPeople(), 3);
        CategoryPreviewView peoplePreviewView = CategoryPreviewView.createPeoplePreviewView(randomPeople, PagePath.PEOPLE);
        peoplePreviewView.sizeProperty().bind(sizeProperty());

        List<Book> randomBooks = getRandomSample(DataRepository2.getInstance().getBooks(), 3);
        CategoryPreviewView booksPreviewView = CategoryPreviewView.createBooksPreviewView(randomBooks, PagePath.BOOKS);
        booksPreviewView.sizeProperty().bind(sizeProperty());

        List<Library> randomLibraries = getRandomSample(DataRepository2.getInstance().getLibraries(), 3);
        CategoryPreviewView libraryPreviewView = CategoryPreviewView.createLibraryPreviewView(randomLibraries, PagePath.LIBRARIES);
        libraryPreviewView.sizeProperty().bind(sizeProperty());

        List<Video> randomVideos = getRandomSample(DataRepository2.getInstance().getVideos(), 3);
        CategoryPreviewView videoPreviewView = CategoryPreviewView.createVideosPreviewView(randomVideos, PagePath.VIDEOS);
        videoPreviewView.sizeProperty().bind(sizeProperty());

        List<Blog> randomBlogs = getRandomSample(DataRepository2.getInstance().getBlogs(), 2);
        CategoryPreviewView blogPreviewView = CategoryPreviewView.createBlogPreviewView(randomBlogs, PagePath.BLOGS);
        blogPreviewView.sizeProperty().bind(sizeProperty());

        VBox normalView = new VBox(categoryAdvancedView, weekLinksView, showCasePreviewView, peoplePreviewView, libraryPreviewView, booksPreviewView, videoPreviewView, blogPreviewView, tipsPreviewView, learnCategoryBox);
        normalView.getStyleClass().add("content-box");

        ScrollPane ScrollPane = new ScrollPane(normalView);
        ScrollPane.getStyleClass().add("mobile");
        VBox.setVgrow(ScrollPane, Priority.ALWAYS);
        return ScrollPane;
    }

    private <T extends ModelObject> List<T> getRandomSample(List<T> list, int sampleSize) {
        if (sampleSize > list.size()) {
            throw new IllegalArgumentException("Sample size must be less than or equal to the size of the list");
        }

        List<T> copy = new ArrayList<>(list);
        Collections.shuffle(copy);
        return copy.subList(0, sampleSize);
    }

    // content type

    private final ObjectProperty<ContentType> contentType = new ReadOnlyObjectWrapper<>(this, "contentType", ContentType.NORMAL);

    public final ContentType getContentType() {
        return contentType.get();
    }

    public final ObjectProperty<ContentType> contentTypeProperty() {
        return contentType;
    }

    public final void setContentType(ContentType contentType) {
        this.contentType.set(contentType);
    }

}
