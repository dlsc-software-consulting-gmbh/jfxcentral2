package com.dlsc.jfxcentral2.mobile.pages;

import com.dlsc.gemsfx.SearchTextField;
import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.mobile.home.CategoryAdvancedView;
import com.dlsc.jfxcentral2.mobile.home.CategoryPreviewView;
import com.dlsc.jfxcentral2.mobile.home.HomePageHeader;
import com.dlsc.jfxcentral2.mobile.home.WeekLinksView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MobileHomePage extends VBox {

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public MobileHomePage(ObjectProperty<Size> size) {
        this();
        sizeProperty().bind(size);
    }

    public MobileHomePage() {
        getStyleClass().add("mobile-home-page");

        HomePageHeader header = new HomePageHeader();
        header.sizeProperty().bind(sizeProperty());

        SearchTextField searchTextField = new SearchTextField(true);
        searchTextField.setRight(new Label("Search"));
        searchTextField.setPromptText("Search for anything...");
        StackPane searchWrapper = new StackPane(searchTextField);
        searchWrapper.getStyleClass().add("search-wrapper");

        CategoryAdvancedView categoryAdvancedView = new CategoryAdvancedView();
        categoryAdvancedView.sizeProperty().bind(sizeProperty());

        List<LinksOfTheWeek> linksOfTheWeek = DataRepository2.getInstance().getLinksOfTheWeek();

        WeekLinksView weekLinksView = new WeekLinksView();
        weekLinksView.sizeProperty().bind(sizeProperty());
        weekLinksView.setLinksOfTheWeek(linksOfTheWeek.get(linksOfTheWeek.size() - 1));

        CategoryPreviewView showCasePreviewView = createShowCasePreviewView();
        showCasePreviewView.sizeProperty().bind(sizeProperty());

        CategoryPreviewView tipsPreviewView = createTipsPreviewView();
        tipsPreviewView.sizeProperty().bind(sizeProperty());

        CategoryPreviewView peoplePreviewView = createPeoplePreviewView();
        peoplePreviewView.sizeProperty().bind(sizeProperty());

        CategoryPreviewView booksPreviewView = createBooksPreviewView();
        booksPreviewView.sizeProperty().bind(sizeProperty());

        CategoryPreviewView libraryPreviewView = createLibraryPreviewView();
        libraryPreviewView.sizeProperty().bind(sizeProperty());

        CategoryPreviewView blogPreviewView = createBlogPreviewView();
        blogPreviewView.sizeProperty().bind(sizeProperty());

        VBox content = new VBox(categoryAdvancedView, weekLinksView, showCasePreviewView, peoplePreviewView, libraryPreviewView, booksPreviewView, blogPreviewView, tipsPreviewView);
        content.getStyleClass().add("content-box");

        PrettyScrollPane scrollPane = new PrettyScrollPane(content);
        scrollPane.getStyleClass().add("mobile");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        getChildren().addAll(header, searchWrapper, scrollPane);
    }

    private CategoryPreviewView createBlogPreviewView() {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Blog");
        view.setShowAllUrl(PagePath.BLOGS);
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        List<Blog> blogs = DataRepository2.getInstance().getBlogs();

        List<Blog> randomBlogs = getRandomSample(blogs, 2);
        for (Blog blog : randomBlogs) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    blog.getName(),
                    blog.getSummary(),
                    ImageManager.getInstance().blogIconImageProperty(blog),
                    ModelObjectTool.getModelLink(blog),
                    123,
                    57,
                    2048

            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    private CategoryPreviewView createLibraryPreviewView() {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Libraries");
        view.setShowAllUrl(PagePath.LIBRARIES);
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        List<Library> books = DataRepository2.getInstance().getLibraries();

        List<Library> randomBooks = getRandomSample(books, 3);
        for (Library library : randomBooks) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    library.getName(),
                    library.getSummary(),
                    ImageManager.getInstance().libraryFeaturedImageProperty(library),
                    ModelObjectTool.getModelLink(library),
                    123,
                    57,
                    2048

            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    private CategoryPreviewView createBooksPreviewView() {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Books");
        view.setShowAllUrl(PagePath.BOOKS);
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        List<Book> books = DataRepository2.getInstance().getBooks();

        List<Book> randomBooks = getRandomSample(books, 2);
        for (Book book : randomBooks) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    book.getName(),
                    book.getSummary(),
                    ImageManager.getInstance().bookCoverImageProperty(book),
                    ModelObjectTool.getModelLink(book),
                    123,
                    57,
                    2048

            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    // Size support

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    private CategoryPreviewView createShowCasePreviewView() {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Real World Apps");
        view.setShowAllUrl(PagePath.SHOWCASES);
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        List<RealWorldApp> apps = DataRepository2.getInstance().getRealWorldApps();

        List<RealWorldApp> randomApps = getRandomSample(apps, 3);
        for (RealWorldApp app : randomApps) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    app.getName(),
                    app.getSummary(),
                    ImageManager.getInstance().realWorldAppBannerImageProperty(app),
                    ModelObjectTool.getModelLink(app),
                    123,
                    57,
                    2048

            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    private CategoryPreviewView createPeoplePreviewView() {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("People");
        view.setShowAllUrl(PagePath.PEOPLE);
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        List<Person> people = DataRepository2.getInstance().getPeople();

        List<Person> randomPeople = getRandomSample(people, 3);
        for (Person person : randomPeople) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    person.getName(),
                    DataRepository2.getInstance().getPersonReadMe(person),
                    ImageManager.getInstance().personImageProperty(person),
                    ModelObjectTool.getModelLink(person),
                    399,
                    258,
                    3096

            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    private CategoryPreviewView createTipsPreviewView() {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Tips");
        view.setShowAllUrl(PagePath.TIPS);
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        List<Tip> tips = DataRepository2.getInstance().getTips();

        List<Tip> randomTips = getRandomSample(tips, 2);
        for (Tip tip : randomTips) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    tip.getName(),
                    tip.getSummary(),
                    ImageManager.getInstance().tipBannerImageProperty(tip),
                    ModelObjectTool.getModelLink(tip),
                    123,
                    57,
                    2048

            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    private <T extends ModelObject> List<T> getRandomSample(List<T> list, int sampleSize) {
        if (sampleSize > list.size()) {
            throw new IllegalArgumentException("Sample size must be less than or equal to the size of the list");
        }

        List<T> copy = new ArrayList<>(list);
        Collections.shuffle(copy);
        return copy.subList(0, sampleSize);
    }

}
