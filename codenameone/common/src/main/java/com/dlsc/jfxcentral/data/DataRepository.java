package com.dlsc.jfxcentral.data;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LibraryInfo;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.Member;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.News;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.Post;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Utility;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral.data.util.StringUtils;

import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.mapping.Mapper;
import com.codename1.mapping.Mappers;
import com.codename1.ui.CN;
import com.codename1.xml.Element;
import com.codename1.xml.XMLParser;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/// The JFXCentral content, read from the
/// [jfxcentral-data](https://github.com/dlsc-software-consulting-gmbh/jfxcentral-data)
/// repository.
///
/// The desktop app cloned that repository (about 240 MB) and read it from disk.
/// A phone fetches only what it shows: the collection files when the app starts,
/// and a readme or an image when a page asks for it, straight from GitHub. Each
/// file is kept in `Storage`, so a page seen once opens offline and a refresh
/// replaces the copy. JSON is mapped by the build-time `@Mapped` mappers and the
/// feeds are read as XML (RSS or Atom); nothing here uses reflection.
public class DataRepository {

    /// Where the content is read from. The `live` branch is what jfxcentral.com serves.
    public static final String BASE_URL = "https://raw.githubusercontent.com/dlsc-software-consulting-gmbh/jfxcentral-data/live/";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static boolean testing;
    private static DataRepository instance;
    private final List<Library> libraries = new ArrayList<>();
    private final List<Blog> blogs = new ArrayList<>();
    private final List<News> news = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();
    private final List<LinksOfTheWeek> linksOfTheWeek = new ArrayList<>();
    private final List<Tip> tips = new ArrayList<>();
    private final List<Tutorial> tutorials = new ArrayList<>();
    private final List<Video> videos = new ArrayList<>();
    private final List<Download> downloads = new ArrayList<>();
    private final List<RealWorldApp> realWorldApps = new ArrayList<>();
    private final List<Tool> tools = new ArrayList<>();
    private final List<Utility> utilities = new ArrayList<>();
    private final List<Company> companies = new ArrayList<>();
    private final List<Person> people = new ArrayList<>();
    private final List<IkonliPack> ikonliPacks = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<Documentation> documentation = new ArrayList<>();
    private final List<LearnJavaFX> learnJavaFX = new ArrayList<>();
    private final List<LearnMobile> learnMobile = new ArrayList<>();
    private final List<LearnRaspberryPi> learnRaspberryPi = new ArrayList<>();
    private String homeText;
    private String openJFXText;

    private DataRepository() {
    }

    public static synchronized DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }

        return instance;
    }

    public static void setTesting(boolean testing) {
        DataRepository.testing = testing;
    }

    public static boolean isTesting() {
        return DataRepository.testing;
    }

    /// Loads the content, from `Storage` where an earlier run kept it and from
    /// the network otherwise. Call it off the UI thread; it answers whether the
    /// core content (the people list) is there.
    public boolean load() {
        return doLoadData("initial loading of data", false);
    }

    /// Fetches everything again, replacing the copies in `Storage`. Call it off
    /// the UI thread; it answers whether the core content arrived.
    public boolean reload() {
        return doLoadData("explicit call to refresh method", true);
    }

    public void clearData() {
        Log.p("clearing data");
        runOnUiThread(() -> {
            homeText = "";
            openJFXText = "";
            for (List<?> list : allLists()) {
                list.clear();
            }
        });
    }

    private List<List<?>> allLists() {
        List<List<?>> all = new ArrayList<>();
        Collections.addAll(all, people, libraries, books, news, videos, blogs, companies, tools, utilities,
                realWorldApps, downloads, tutorials, tips, linksOfTheWeek, ikonliPacks, members, documentation,
                learnJavaFX, learnMobile, learnRaspberryPi);
        return all;
    }

    /// Builds every list off the UI thread, then swaps them into the ones the
    /// pages read in a single step on the UI thread: pages never see a list
    /// half filled, and are never iterating one while it changes.
    private boolean doLoadData(String reason, boolean refresh) {
        Log.p("loading data, reason = " + reason);
        try {
            final String homeTextNew = loadString("intro.md", refresh);
            final String openJFXTextNew = loadString("openjfx/intro.md", refresh);
            final List<Person> peopleNew = new ArrayList<>(loadList("people/people.json", Person.class, refresh));
            final List<Book> booksNew = new ArrayList<>(loadList("books/books.json", Book.class, refresh));
            final List<Video> videosNew = new ArrayList<>(loadList("videos/videos.json", Video.class, refresh));
            final List<Library> librariesNew = new ArrayList<>(loadList("libraries/libraries.json", Library.class, refresh));
            final List<News> newsNew = new ArrayList<>(loadList("news/news.json", News.class, refresh));
            final List<Tutorial> tutorialsNew = new ArrayList<>(loadList("tutorials/tutorials.json", Tutorial.class, refresh));
            final List<Blog> blogsNew = new ArrayList<>(loadList("blogs/blogs.json", Blog.class, refresh));
            final List<Company> companiesNew = new ArrayList<>(loadList("companies/companies.json", Company.class, refresh));
            final List<Tool> toolsNew = new ArrayList<>(loadList("tools/tools.json", Tool.class, refresh));
            final List<Utility> utilitiesNew = new ArrayList<>(loadList("utilities/utilities.json", Utility.class, refresh));
            final List<RealWorldApp> realWorldAppsNew = new ArrayList<>(loadList("realworld/realworld.json", RealWorldApp.class, refresh));
            final List<Download> downloadsNew = new ArrayList<>(loadList("downloads/downloads.json", Download.class, refresh));
            final List<Tip> tipsNew = new ArrayList<>(loadList("tips/tips.json", Tip.class, refresh));
            final List<LinksOfTheWeek> linksOfTheWeekNew = new ArrayList<>(loadList("links/links.json", LinksOfTheWeek.class, refresh));
            final List<IkonliPack> ikonliPacksNew = new ArrayList<>(loadList("ikonlipacks/ikonlipacks.json", IkonliPack.class, refresh));
            final List<Member> membersNew = new ArrayList<>(loadList("members/members.json", Member.class, refresh));
            final List<Documentation> documentationNew = new ArrayList<>(loadList("documentation/documentation.json", Documentation.class, refresh));
            final List<LearnJavaFX> learnJavaFXNew = new ArrayList<>(loadList("learn/javafx/learn.json", LearnJavaFX.class, refresh));
            final List<LearnMobile> learnMobileNew = new ArrayList<>(loadList("learn/mobile/learn.json", LearnMobile.class, refresh));
            final List<LearnRaspberryPi> learnRaspberryPiNew = new ArrayList<>(loadList("learn/raspberrypi/learn.json", LearnRaspberryPi.class, refresh));
            runOnUiThread(() -> {
                homeText = homeTextNew;
                openJFXText = openJFXTextNew;
                replace(people, peopleNew);
                replace(books, booksNew);
                replace(videos, videosNew);
                replace(libraries, librariesNew);
                replace(news, newsNew);
                replace(tutorials, tutorialsNew);
                replace(blogs, blogsNew);
                replace(companies, companiesNew);
                replace(tools, toolsNew);
                replace(utilities, utilitiesNew);
                replace(realWorldApps, realWorldAppsNew);
                replace(downloads, downloadsNew);
                replace(tips, tipsNew);
                replace(linksOfTheWeek, linksOfTheWeekNew);
                replace(ikonliPacks, ikonliPacksNew);
                replace(members, membersNew);
                replace(documentation, documentationNew);
                replace(learnJavaFX, learnJavaFXNew);
                replace(learnMobile, learnMobileNew);
                replace(learnRaspberryPi, learnRaspberryPiNew);
            });
            Log.p("data loading finished");
            // The People list and its search read the readmes; fetch them now,
            // off the EDT, rather than one by one while the list scrolls.
            com.codename1.ui.Display.getInstance().callSerially(() -> {
                if (refresh) {
                    forgetPeopleReadMes();
                }
                prefetchPeopleReadMes(null);
            });
            return !peopleNew.isEmpty();
        } catch (RuntimeException e) {
            Log.e(e);
            return false;
        }
    }

    private static <T> void replace(List<T> target, List<T> values) {
        target.clear();
        target.addAll(values);
    }

    private static void runOnUiThread(Runnable r) {
        if (CN.isEdt()) {
            r.run();
        } else {
            CN.callSeriallyAndWait(r);
        }
    }



    public Optional<Person> getPersonById(String id) {
        return people.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<Company> getCompanyById(String id) {
        return companies.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<Library> getLibraryById(String id) {
        return libraries.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<Book> getBookById(String id) {
        return books.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<LearnJavaFX> getLearnJavaFXById(String id) {
        return learnJavaFX.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<LearnMobile> getLearnMobileById(String id) {
        return learnMobile.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<LearnRaspberryPi> getLearnRaspberryPiById(String id) {
        return learnRaspberryPi.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<Blog> getBlogById(String id) {
        return blogs.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<RealWorldApp> getRealWorldAppById(String id) {
        return realWorldApps.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<Tool> getToolById(String id) {
        return tools.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<Utility> getUtilityById(String id) {
        return utilities.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<Download> getDownloadById(String id) {
        return downloads.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<News> getNewsById(String id) {
        return news.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<Video> getVideoById(String id) {
        return videos.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<Tutorial> getTutorialById(String id) {
        return tutorials.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<Tip> getTipById(String id) {
        return tips.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<LinksOfTheWeek> getLinksOfTheWeekById(String id) {
        return linksOfTheWeek.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<IkonliPack> getIkonliPackById(String id) {
        return ikonliPacks.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<Member> getMemberById(String id) {
        return members.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Optional<Documentation> getDocumentationById(String id) {
        return documentation.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public <T extends ModelObject> List<T> getLinkedObjects(ModelObject modelObject, Class<T> clazz) {
        List<T> itemList = getList(clazz);
        List<String> idsList = getIdList(modelObject, clazz);
        return itemList.stream().filter(item -> idsList.contains(item.getId()) || getIdList(item, modelObject.getClass()).contains(modelObject.getId())).collect(Collectors.toList());
    }

    private <T extends ModelObject> List<String> getIdList(ModelObject modelObject, Class<T> clazz) {
        if (clazz.equals(Video.class)) {
            return modelObject.getVideoIds();
        } else if (clazz.equals(Book.class)) {
            return modelObject.getBookIds();
        } else if (clazz.equals(Library.class)) {
            return modelObject.getLibraryIds();
        } else if (clazz.equals(Tutorial.class)) {
            return modelObject.getTutorialIds();
        } else if (clazz.equals(Download.class)) {
            return modelObject.getDownloadIds();
        } else if (clazz.equals(Person.class)) {
            return modelObject.getPersonIds();
        } else if (clazz.equals(Tool.class)) {
            return modelObject.getToolIds();
        } else if (clazz.equals(Utility.class)) {
            return modelObject.getUtilityIds();
        } else if (clazz.equals(RealWorldApp.class)) {
            return modelObject.getAppIds();
        } else if (clazz.equals(News.class)) {
            return modelObject.getNewsIds();
        } else if (clazz.equals(Blog.class)) {
            return modelObject.getBlogIds();
        } else if (clazz.equals(Company.class)) {
            return modelObject.getCompanyIds();
        } else if (clazz.equals(Tip.class)) {
            return modelObject.getTipIds();
        } else if (clazz.equals(LinksOfTheWeek.class)) {
            return modelObject.getLinksOfTheWeekIds();
        } else if (clazz.equals(IkonliPack.class)) {
            return modelObject.getIkonliPackIds();
        } else if (clazz.equals(Member.class)) {
            return modelObject.getMemberIds();
        } else if (clazz.equals(Documentation.class)) {
            return modelObject.getDocumentationIds();
        } else if (clazz.equals(LearnJavaFX.class)) {
            return modelObject.getLearnJavaFXIds();
        } else if (clazz.equals(LearnMobile.class)) {
            return modelObject.getLearnMobileIds();
        } else if (clazz.equals(LearnRaspberryPi.class)) {
            return modelObject.getLearnRaspberryPiIds();
        }

        throw new IllegalArgumentException("unsupported class type: " + clazz.getSimpleName());
    }

    public <T extends ModelObject> List<T> getList(Class<T> clazz) {
        if (clazz.equals(Video.class)) {
            return (List<T>) videos;
        } else if (clazz.equals(Book.class)) {
            return (List<T>) books;
        } else if (clazz.equals(Library.class)) {
            return (List<T>) libraries;
        } else if (clazz.equals(Tutorial.class)) {
            return (List<T>) tutorials;
        } else if (clazz.equals(Download.class)) {
            return (List<T>) downloads;
        } else if (clazz.equals(Person.class)) {
            return (List<T>) people;
        } else if (clazz.equals(Tool.class)) {
            return (List<T>) tools;
        } else if (clazz.equals(Utility.class)) {
            return (List<T>) utilities;
        } else if (clazz.equals(RealWorldApp.class)) {
            return (List<T>) realWorldApps;
        } else if (clazz.equals(News.class)) {
            return (List<T>) news;
        } else if (clazz.equals(Blog.class)) {
            return (List<T>) blogs;
        } else if (clazz.equals(Company.class)) {
            return (List<T>) companies;
        } else if (clazz.equals(Tip.class)) {
            return (List<T>) tips;
        } else if (clazz.equals(LinksOfTheWeek.class)) {
            return (List<T>) linksOfTheWeek;
        } else if (clazz.equals(IkonliPack.class)) {
            return (List<T>) ikonliPacks;
        } else if (clazz.equals(Member.class)) {
            return (List<T>) members;
        } else if (clazz.equals(Documentation.class)) {
            return (List<T>) documentation;
        } else if (clazz.equals(LearnJavaFX.class)) {
            return (List<T>) learnJavaFX;
        } else if (clazz.equals(LearnMobile.class)) {
            return (List<T>) learnMobile;
        } else if (clazz.equals(LearnRaspberryPi.class)) {
            return (List<T>) learnRaspberryPi;
        }

        throw new IllegalArgumentException("unsupported class type: " + clazz.getSimpleName());
    }

    public <T extends ModelObject> T getByID(Class<T> clz, String id) {
        return getList(clz).stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean isValidId(Class<? extends ModelObject> clz, String id) {
        return getList(clz).stream().anyMatch(item -> item.getId().equals(id));
    }

    public List<Video> getVideosByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, Video.class);
    }

    public List<Download> getDownloadsByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, Download.class);
    }

    public List<Book> getBooksByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, Book.class);
    }

    public List<Tutorial> getTutorialsByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, Tutorial.class);
    }

    public List<Blog> getBlogsByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, Blog.class);
    }

    public List<Library> getLibrariesByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, Library.class);
    }

    public List<Tool> getToolsByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, Tool.class);
    }

    public List<Utility> getUtilitiesByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, Utility.class);
    }

    public List<News> getNewsByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, News.class);
    }

    public List<Company> getCompaniesByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, Company.class);
    }

    public List<RealWorldApp> getRealWorldAppsByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, RealWorldApp.class);
    }

    public List<Person> getPeopleByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, Person.class);
    }

    public List<Tip> getTipsByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, Tip.class);
    }

    public List<LearnJavaFX> getLearnJavaFXByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, LearnJavaFX.class);
    }

    public List<LearnMobile> getLearnMobileByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, LearnMobile.class);
    }

    public List<LearnRaspberryPi> getLearnRaspberryPiByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, LearnRaspberryPi.class);
    }

    public List<LinksOfTheWeek> getLinksOfTheWeekByModelObject(ModelObject modelObject) {
        return getLinkedObjects(modelObject, LinksOfTheWeek.class);
    }




    public String getLinksOfTheWeekReadMe(LinksOfTheWeek links) {
        return loadString("links/" + links.getId() + "/readme.md");
    }


    public String getTutorialReadMe(Tutorial tutorial) {
        return loadString("tutorials/" + tutorial.getId() + "/readme.md");
    }

    public String getDownloadReadMe(Download download) {
        return loadString("downloads/" + download.getId() + "/readme.md");
    }

    public String getBookReadMe(Book book) {
        return loadString("books/" + book.getId() + "/readme.md");
    }

    public String getPersonReadMe(Person person) {
        return loadString("people/" + person.getId() + "/readme.md");
    }

    public String getMemberReadMe(Member member) {
        return loadString("members/" + member.getId() + "/readme.md");
    }

    public String getToolReadMe(Tool tool) {
        return loadString("tools/" + tool.getId() + "/readme.md");
    }

    public String getUtilityReadMe(Utility utility) {
        return loadString("utilities/" + utility.getId() + "/readme.md");
    }

    public String getTipReadMe(Tip tip) {
        return loadString("tips/" + tip.getId() + "/readme.md");
    }

    public String getRealWorldReadMe(RealWorldApp app) {
        return loadString("realworld/" + app.getId() + "/readme.md");
    }

    public String getCompanyReadMe(Company company) {
        return loadString("companies/" + company.getId() + "/readme.md");
    }

    public String getLibraryReadMe(Library library) {
        return loadString("libraries/" + library.getId() + "/readme.md");
    }

    public String getLearnJavaFXReadMe(LearnJavaFX learnJavaFX) {
        return loadString("learn/javafx/" + learnJavaFX.getId() + "/readme.md");
    }

    public String getLearnMobileReadMe(LearnMobile learnMobile) {
        return loadString("learn/mobile/" + learnMobile.getId() + "/readme.md");
    }

    public String getLearnRaspberryPiReadMe(LearnRaspberryPi learnRaspberryPi) {
        return loadString("learn/raspberrypi/" + learnRaspberryPi.getId() + "/readme.md");
    }


    public List<Library> getLibraries() {
        return libraries;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public List<News> getNews() {
        return news;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<LinksOfTheWeek> getLinksOfTheWeek() {
        return linksOfTheWeek;
    }

    public List<Tip> getTips() {
        return tips;
    }

    public List<Tutorial> getTutorials() {
        return tutorials;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public List<Download> getDownloads() {
        return downloads;
    }

    public List<RealWorldApp> getRealWorldApps() {
        return realWorldApps;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public List<Utility> getUtilities() {
        return utilities;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public List<Person> getPeople() {
        return people;
    }

    public List<IkonliPack> getIkonliPacks() {
        return ikonliPacks;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Documentation> getDocumentation() {
        return documentation;
    }

    public List<LearnJavaFX> getLearnJavaFX() {
        return learnJavaFX;
    }

    public List<LearnMobile> getLearnMobile() {
        return learnMobile;
    }

    public List<LearnRaspberryPi> getLearnRaspberryPi() {
        return learnRaspberryPi;
    }

    public String getHomeText() {
        return homeText;
    }

    public String getOpenJFXText() {
        return openJFXText;
    }







    public LibraryInfo getLibraryInfo(Library library) {
        String json = loadString("libraries/" + library.getId() + "/info.json");
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return Mappers.fromJson(json, LibraryInfo.class);
        } catch (RuntimeException ex) {
            Log.e(ex);
            return null;
        }
    }

    /// The news item's folder, relative to [#BASE_URL].
    public String getNewsDirectory(News news) {
        return "news/" + DATE_FORMATTER.format(news.getCreatedOn()) + "-" + news.getId();
    }

    public String getNewsText(News news) {
        return loadString(getNewsDirectory(news) + "/text.md");
    }

    public String getRepositoryDirectoryURL() {
        return BASE_URL;
    }

    /// Reads a whole collection file -- a top-level JSON array -- through the
    /// type's generated mapper.
    private <T> Collection<T> loadList(String path, Class<T> type, boolean refresh) {
        String json = loadString(path, refresh);
        List<T> result = new ArrayList<>();
        if (StringUtils.isBlank(json)) {
            return result;
        }
        try {
            Map<String, Object> root = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(("{\"root\":" + json + "}").getBytes("UTF-8")), "UTF-8"));
            Object list = root.get("root");
            if (list instanceof List) {
                Mapper<T> mapper = Mappers.require(type);
                for (Object o : (List<?>) list) {
                    if (o instanceof Map) {
                        T item = mapper.fromMap((Map<String, Object>) o);
                        if (item instanceof ModelObject) {
                            ((ModelObject) item).readBaseFields((Map<String, Object>) o);
                        }
                        result.add(item);
                    }
                }
            }
        } catch (Exception ex) {
            Log.p("Error reading " + path + ": " + ex);
        }
        return result;
    }

    /// The text of a repository file: from GitHub when it can be reached, from
    /// the copy in `Storage` when it cannot, and empty when there is neither.
    /// Blocks the calling thread only; on the EDT the UI keeps running, since
    /// `Rest`'s synchronous calls go through `invokeAndBlock`.
    /// A repository file as text, cached in `Storage` as UTF-8 bytes (a String
    /// would go through `writeUTF`, which cannot hold more than 64KB).
    /// How long a fetched file is used before it is asked for again. A refresh
    /// asks for the collections regardless; this keeps the files fetched one at
    /// a time (readmes, library info) from staying as they were first seen.
    private static final long MAX_AGE = 24L * 60 * 60 * 1000;

    private String loadString(String path) {
        return loadString(path, false);
    }

    /// `refresh` skips the cached copy, for a reload the user asked for.
    private String loadString(String path, boolean refresh) {
        if (!refresh) {
            String known = memo(path);
            if (known != null) {
                return known;
            }
        }
        boolean[] answered = new boolean[1];
        String loaded = fetchString(path, refresh, answered);
        // An offline fallback is not kept for the run, so the next call tries
        // the network again.
        if (answered[0]) {
            remember(path, loaded);
        }
        return loaded;
    }

    /// Text files already read in this run. A list cell asks for its text on
    /// every update, and reading storage, let alone the network, there is
    /// what made scrolling and searching stall.
    private final java.util.Map<String, String> strings = new java.util.HashMap<>();

    private String memo(String path) {
        synchronized (strings) {
            return strings.get(path);
        }
    }

    private void remember(String path, String text) {
        synchronized (strings) {
            strings.put(path, text);
        }
    }

    /// Readme paths storage was asked for and did not have, so a list cell
    /// does not ask storage again on every update. Touched on the EDT only.
    private final java.util.Set<String> absent = new java.util.HashSet<>();

    private static String personReadMePath(Person person) {
        return "people/" + person.getId() + "/readme.md";
    }

    private static String storageKey(String path) {
        return "jfxcentral-" + path.replace('/', '_');
    }

    /// A person's readme if this run or an earlier one has it, however old,
    /// and `null` otherwise. Never touches the network, so a list cell or a
    /// search filter can call it on every update; what is missing is fetched
    /// by [#prefetchPeopleReadMes]. Call it on the EDT.
    public String peekPersonReadMe(Person person) {
        String path = personReadMePath(person);
        String known = memo(path);
        if (known == null && !absent.contains(path)) {
            known = readCached(storageKey(path));
            if (known != null) {
                remember(path, known);
            } else {
                absent.add(path);
            }
        }
        return known;
    }

    private boolean prefetching;
    private boolean prefetched;
    private boolean prefetchRefresh;
    /// Moves on every forget, so a worker started before it neither keeps
    /// what it reads nor counts as the prefetch that follows it.
    private int prefetchGeneration;
    private final List<Runnable> afterPrefetch = new ArrayList<>();

    /// Drops the readmes read so far, after the user asked for fresh data:
    /// the next prefetch goes to the network for every person.
    private void forgetPeopleReadMes() {
        synchronized (strings) {
            strings.keySet().removeIf(k -> k.startsWith("people/") && k.endsWith("/readme.md"));
        }
        absent.clear();
        prefetched = false;
        prefetchRefresh = true;
        prefetchGeneration++;
    }

    /// Brings every person's readme into this run, from storage when it is
    /// fresh and from the network otherwise, and then runs `done` on the EDT
    /// (later, also when that already happened). A call while one runs waits
    /// for it. Call it on the EDT.
    ///
    /// Only the downloads run on a thread of their own. The prefetch reads
    /// and writes storage on the EDT, which also reads it for the list,
    /// because `Storage` is not safe to use from two threads at once. (The
    /// main data load, off the EDT, still writes the collections' files
    /// itself, as the original app's loader did.)
    public void prefetchPeopleReadMes(Runnable done) {
        com.codename1.ui.Display display = com.codename1.ui.Display.getInstance();
        if (prefetched) {
            if (done != null) {
                display.callSerially(done);
            }
            return;
        }
        if (done != null) {
            afterPrefetch.add(done);
        }
        if (prefetching) {
            return;
        }
        prefetching = true;
        boolean refresh = prefetchRefresh;
        prefetchRefresh = false;
        int generation = prefetchGeneration;
        List<Person> people = new ArrayList<>(getPeople());
        Thread worker = new Thread(() -> {
            try {
                for (Person person : people) {
                    if (!prefetchReadMe(personReadMePath(person), refresh, generation)) {
                        break;
                    }
                }
            } catch (RuntimeException ex) {
                Log.e(ex);
            } finally {
                display.callSerially(() -> {
                    prefetching = false;
                    if (generation != prefetchGeneration) {
                        // Forgotten while it ran: start the one asked for since.
                        prefetchPeopleReadMes(null);
                        return;
                    }
                    prefetched = !people.isEmpty();
                    List<Runnable> waiting = new ArrayList<>(afterPrefetch);
                    afterPrefetch.clear();
                    for (Runnable r : waiting) {
                        r.run();
                    }
                });
            }
        }, "readme-prefetch");
        worker.start();
    }

    /// One readme for the prefetch: runs on its worker thread. Answers false
    /// once the readmes were forgotten, which ends this worker's run.
    private boolean prefetchReadMe(String path, boolean refresh, int generation) {
        com.codename1.ui.Display display = com.codename1.ui.Display.getInstance();
        String key = storageKey(path);
        boolean[] fresh = new boolean[1];
        boolean[] current = {true};
        display.callSeriallyAndWait(() -> {
            if (generation != prefetchGeneration) {
                current[0] = false;
                return;
            }
            if (!refresh && memo(path) != null) {
                fresh[0] = true;
                return;
            }
            String cached = readCached(key);
            Object stamp = Storage.getInstance().readObject(key + ".time");
            if (!refresh && cached != null && stamp instanceof Long
                    && System.currentTimeMillis() - (Long) stamp < MAX_AGE) {
                remember(path, cached);
                absent.remove(path);
                fresh[0] = true;
            }
        });
        if (!current[0] || fresh[0]) {
            return current[0];
        }
        byte[] data = download(path);
        if (data == null) {
            // Offline: the list keeps showing whatever storage has.
            return true;
        }
        display.callSeriallyAndWait(() -> {
            if (generation == prefetchGeneration) {
                store(key, data);
                remember(path, decode(data));
                absent.remove(path);
            }
        });
        return true;
    }

    /// The file's bytes, empty when the repository has no such file (most
    /// entries have no readme, and a 404 is an answer), or `null` when the
    /// network or the server failed. Touches no storage.
    private static byte[] download(String path) {
        try {
            Response<byte[]> response = Rest.get(BASE_URL + path)
                    .onErrorCodeBytes(r -> { })
                    .onError(evt -> evt.consume())
                    .getAsBytes();
            int code = response.getResponseCode();
            if (code == 200 && response.getResponseData() != null) {
                return response.getResponseData();
            }
            if (code == 404) {
                return new byte[0];
            }
        } catch (RuntimeException ex) {
            Log.p("Could not fetch " + path + ": " + ex);
        }
        return null;
    }

    private static void store(String key, byte[] data) {
        Storage.getInstance().writeObject(key, data);
        Storage.getInstance().writeObject(key + ".time", Long.valueOf(System.currentTimeMillis()));
    }

    /// `answered[0]` says whether the text is an answer of this run -- fresh
    /// storage or the server -- rather than an offline fallback.
    private String fetchString(String path, boolean refresh, boolean[] answered) {
        String key = storageKey(path);
        if (!refresh) {
            String cached = readCached(key);
            Object stamp = Storage.getInstance().readObject(key + ".time");
            boolean fresh = stamp instanceof Long && System.currentTimeMillis() - (Long) stamp < MAX_AGE;
            if (cached != null && fresh) {
                answered[0] = true;
                return cached;
            }
        }
        byte[] data = download(path);
        if (data != null) {
            store(key, data);
            answered[0] = true;
            return decode(data);
        }
        // Offline, or the server failed: whatever an earlier run kept, however old.
        String cached = readCached(key);
        return cached == null ? "" : cached;
    }

    private static String readCached(String key) {
        if (!Storage.getInstance().exists(key)) {
            return null;
        }
        Object cached = Storage.getInstance().readObject(key);
        return cached instanceof byte[] ? decode((byte[]) cached) : null;
    }

    /// UTF-8, without the byte order mark some of the repository's files start with.
    private static String decode(byte[] data) {
        try {
            String s = new String(data, "UTF-8");
            return s.length() > 0 && s.charAt(0) == '\ufeff' ? s.substring(1) : s;
        } catch (java.io.UnsupportedEncodingException ex) {
            return new String(data);
        }
    }

    /// A blog's posts, from an RSS 2.0 or an Atom feed. The feeds in the
    /// repository are both kinds, so the document is read as XML and either
    /// `item` or `entry` elements are taken.
    public List<Post> loadPosts(Blog blog) {
        String url = blog.getFeed();
        if (StringUtils.isBlank(url)) {
            return Collections.emptyList();
        }
        List<Post> posts = new ArrayList<>();
        try {
            Response<byte[]> response = Rest.get(url)
                    .onErrorCodeBytes(r -> { })
                    .onError(evt -> evt.consume())
                    .getAsBytes();
            if (response.getResponseCode() != 200 || response.getResponseData() == null) {
                return posts;
            }
            // Case sensitive, or the parser folds `pubDate` to `pubdate`.
            XMLParser parser = new XMLParser();
            parser.setCaseSensitive(true);
            Element root = parser.parse(new InputStreamReader(
                    new ByteArrayInputStream(response.getResponseData()), "UTF-8"));
            if (root == null) {
                return posts;
            }
            List<Element> entries = new ArrayList<>();
            collect(root, entries);
            for (Element e : entries) {
                boolean atom = "entry".equals(e.getTagName());
                String link = atom ? atomLink(e) : text(e, "link");
                String author = atom ? text(first(e, "author"), "name")
                        : firstNonBlank(text(e, "dc:creator"), text(e, "author"));
                String date = atom ? firstNonBlank(text(e, "published"), text(e, "updated"))
                        : firstNonBlank(text(e, "pubDate"), text(e, "dc:date"));
                posts.add(new Post(blog, text(e, "title"), author, link, parseDate(date)));
                if (posts.size() == 50) {
                    break;
                }
            }
        } catch (Exception ex) {
            Log.e(ex);
        }
        return posts;
    }

    private static void collect(Element e, List<Element> out) {
        String tag = e.getTagName();
        if ("item".equals(tag) || "entry".equals(tag)) {
            out.add(e);
            return;
        }
        for (int i = 0; i < e.getNumChildren(); i++) {
            Element c = e.getChildAt(i);
            if (!c.isTextElement()) {
                collect(c, out);
            }
        }
    }

    private static Element first(Element parent, String tag) {
        if (parent == null) {
            return null;
        }
        for (int i = 0; i < parent.getNumChildren(); i++) {
            Element c = parent.getChildAt(i);
            if (!c.isTextElement() && tag.equals(c.getTagName())) {
                return c;
            }
        }
        return null;
    }

    private static String text(Element parent, String tag) {
        Element e = first(parent, tag);
        if (e == null) {
            return null;
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < e.getNumChildren(); i++) {
            Element c = e.getChildAt(i);
            if (c.isTextElement()) {
                b.append(c.getText());
            }
        }
        String t = b.toString().trim();
        return t.isEmpty() ? null : t;
    }

    /// An Atom entry's page: the `alternate` link, or the first without a `rel`.
    private static String atomLink(Element entry) {
        String fallback = null;
        for (int i = 0; i < entry.getNumChildren(); i++) {
            Element c = entry.getChildAt(i);
            if (c.isTextElement() || !"link".equals(c.getTagName())) {
                continue;
            }
            String rel = c.getAttribute("rel");
            if ("alternate".equals(rel)) {
                return c.getAttribute("href");
            }
            if (rel == null && fallback == null) {
                fallback = c.getAttribute("href");
            }
        }
        return fallback;
    }

    private static String firstNonBlank(String a, String b) {
        return StringUtils.isNotBlank(a) ? a : b;
    }

    private static final String[] MONTHS = {"jan", "feb", "mar", "apr", "may", "jun",
            "jul", "aug", "sep", "oct", "nov", "dec"};

    /// RFC 822 (`Tue, 03 Jun 2025 10:00:00 GMT`, any zone name or offset, the
    /// weekday optional) or ISO 8601 (`2025-06-03T10:00:00Z`, `+02:00`, a
    /// fraction, or a date alone). Parsed by hand: a pattern formatter accepts
    /// one of these spellings, and the feeds use all of them.
    static Date parseDate(String date) {
        if (date == null) {
            return null;
        }
        String d = date.trim();
        try {
            if (d.length() >= 10 && d.charAt(4) == '-' && d.charAt(7) == '-') {
                int y = Integer.parseInt(d.substring(0, 4));
                int mo = Integer.parseInt(d.substring(5, 7));
                int da = Integer.parseInt(d.substring(8, 10));
                int h = 0, mi = 0, se = 0, offset = 0;
                if (d.length() >= 19 && (d.charAt(10) == 'T' || d.charAt(10) == ' ')) {
                    h = Integer.parseInt(d.substring(11, 13));
                    mi = Integer.parseInt(d.substring(14, 16));
                    se = Integer.parseInt(d.substring(17, 19));
                    int z = 19;
                    if (z < d.length() && d.charAt(z) == '.') {
                        z++;
                        while (z < d.length() && Character.isDigit(d.charAt(z))) {
                            z++;
                        }
                    }
                    offset = zoneOffset(d.substring(z));
                }
                return utc(y, mo, da, h, mi, se, offset);
            }
            String[] p = d.replace(",", " ").trim().split("\\s+");
            int i = Character.isDigit(p[0].charAt(0)) ? 0 : 1;
            int da = Integer.parseInt(p[i]);
            int mo = month(p[i + 1]);
            int y = Integer.parseInt(p[i + 2]);
            if (y < 100) {
                y += 2000;
            }
            String[] t = p[i + 3].split(":");
            int se = t.length > 2 ? Integer.parseInt(t[2]) : 0;
            int offset = p.length > i + 4 ? zoneOffset(p[i + 4]) : 0;
            return utc(y, mo, da, Integer.parseInt(t[0]), Integer.parseInt(t[1]), se, offset);
        } catch (RuntimeException ex) {
            return null;
        }
    }

    private static int month(String name) {
        String m = name.length() >= 3 ? name.substring(0, 3) : name;
        for (int i = 0; i < MONTHS.length; i++) {
            if (MONTHS[i].equalsIgnoreCase(m)) {
                return i + 1;
            }
        }
        throw new IllegalArgumentException(name);
    }

    /// A zone's offset from UTC in minutes: `Z`, `+hh:mm`, `+hhmm`, or an RFC 822 name.
    private static int zoneOffset(String zone) {
        String z = zone.trim();
        if (z.isEmpty() || z.equalsIgnoreCase("Z") || z.equalsIgnoreCase("GMT")
                || z.equalsIgnoreCase("UT") || z.equalsIgnoreCase("UTC")) {
            return 0;
        }
        char sign = z.charAt(0);
        if (sign == '+' || sign == '-') {
            String digits = z.substring(1).replace(":", "");
            int v = Integer.parseInt(digits.substring(0, 2)) * 60
                    + (digits.length() >= 4 ? Integer.parseInt(digits.substring(2, 4)) : 0);
            return sign == '-' ? -v : v;
        }
        String[] names = {"EST", "EDT", "CST", "CDT", "MST", "MDT", "PST", "PDT"};
        int[] hours = {-5, -4, -6, -5, -7, -6, -8, -7};
        for (int i = 0; i < names.length; i++) {
            if (names[i].equalsIgnoreCase(z)) {
                return hours[i] * 60;
            }
        }
        return 0;
    }

    private static Date utc(int y, int mo, int da, int h, int mi, int se, int offsetMinutes) {
        java.util.Calendar c = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("GMT"));
        c.set(java.util.Calendar.YEAR, y);
        c.set(java.util.Calendar.MONTH, mo - 1);
        c.set(java.util.Calendar.DAY_OF_MONTH, da);
        c.set(java.util.Calendar.HOUR_OF_DAY, h);
        c.set(java.util.Calendar.MINUTE, mi);
        c.set(java.util.Calendar.SECOND, se);
        c.set(java.util.Calendar.MILLISECOND, 0);
        return new Date(c.getTime().getTime() - offsetMinutes * 60000L);
    }
}
