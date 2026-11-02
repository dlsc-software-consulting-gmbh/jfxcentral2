package com.dlsc.jfxcentral.data;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.Member;
import com.dlsc.jfxcentral.data.model.News;
import com.dlsc.jfxcentral.data.model.Utility;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import com.dlsc.jfxcentral.data.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayInputStream;
import com.codename1.io.Storage;
import com.codename1.io.rest.Rest;
import java.util.Objects;

public class ImageManager {


    private static final String MISSING_USER = Objects.requireNonNull(ImageManager.class.getResource("missing-user.png")).toExternalForm();

    private static final Image MISSING_USER_IMAGE = new Image(MISSING_USER);

    private static final String MISSING = Objects.requireNonNull(ImageManager.class.getResource("missing-image.jpg")).toExternalForm();

    private static final Image MISSING_IMAGE = new Image(MISSING);

    private static final String MISSING_VIDEO = Objects.requireNonNull(ImageManager.class.getResource("missing-video.png")).toExternalForm();

    private static final Image MISSING_VIDEO_IMAGE = new Image(MISSING_VIDEO);

    private static final ImageManager instance = new ImageManager();

    private ImageManager() {
    }

    public static ImageManager getInstance() {
        return instance;
    }

    public String newsBannerFile(News news) {
        return localImageFile(DataRepository.getInstance().getNewsDirectory(news) + "/", "banner.jpg");
    }

    public String downloadBannerFile(Download download) {
        return localImageFile("downloads/" + download.getId() + "/", "banner.jpg");
    }

    public String tipBannerFile(Tip tip) {
        return localImageFile("tips/" + tip.getId() + "/", "banner.jpg");
    }

    public String blogPageFile(Blog blog) {
        return localImageFile("blogs/" + blog.getId() + "/", "page-small.png");
    }

    public String blogIconFile(Blog blog) {
        return localImageFile("blogs/" + blog.getId() + "/", "icon.png");
    }

    public String realWorldAppFile(RealWorldApp app) {
        return localImageFile("realworld/" + app.getId() + "/", "small.jpg");
    }

    public String realWorldAppLargeFile(RealWorldApp app) {
        return localImageFile("realworld/" + app.getId() + "/", "large.jpg");
    }

    public String realWorldAppBannerFile(RealWorldApp app) {
        return localImageFile("realworld/" + app.getId() + "/", "banner.jpg");
    }

    public String blogPageLargeFile(Blog blog) {
        return localImageFile("blogs/" + blog.getId() + "/", "page.png");
    }

    public String personFile(Person person) {
        return localImageFile("people/" + person.getId() + "/", "photo.jpeg");
    }

    public String memberFile(Member member) {
        return localImageFile("members/" + member.getId() + "/", "photo.jpeg");
    }

    public String toolFile(Tool tool) {
        return localImageFile("tools/" + tool.getId() + "/", "logo.png");
    }

    public String utilityFile(Utility utility) {
        return localImageFile("utilities/" + utility.getId() + "/", "logo.png");
    }

    public String companyFile(Company company) {
        return localImageFile("companies/" + company.getId() + "/", "logo.png");
    }

    public String tutorialFile(Tutorial tutorial) {
        return localImageFile("tutorials/" + tutorial.getId() + "/", "small.png");
    }

    public String tutorialLargeFile(Tutorial tutorial) {
        return localImageFile("tutorials/" + tutorial.getId() + "/", "large.png");
    }

    public String bookCoverFile(Book book) {
        return localImageFile("books/" + book.getId() + "/", "cover.jpg");
    }

    public String libraryFile(Library library) {
        return localImageFile("libraries/" + library.getId() + "/", "logo.png");
    }

    public String documentationFile(Documentation doc) {
        return localImageFile("documentation/" + doc.getId() + "/", "logo.png");
    }

    private String localImageFile(String directory, String photoFileName) {
        if (StringUtils.isBlank(photoFileName)) {
            return null;
        }
        return directory + photoFileName;
    }

    public String libraryFile(Library library, String imagePath) {
        return localImageFile("libraries/" + library.getId() + "/", imagePath);
    }

    public ObjectProperty<Image> newsBannerImageProperty(News news) {
        return fileToImageProperty(newsBannerFile(news), MISSING_IMAGE);
    }

    public ObjectProperty<Image> downloadBannerImageProperty(Download download) {
        return fileToImageProperty(downloadBannerFile(download), MISSING_IMAGE);
    }

    public ObjectProperty<Image> tipBannerImageProperty(Tip tip) {
        return fileToImageProperty(tipBannerFile(tip), MISSING_IMAGE);
    }

    public ObjectProperty<Image> blogPageImageProperty(Blog blog) {
        return fileToImageProperty(blogPageFile(blog), MISSING_IMAGE);
    }

    public ObjectProperty<Image> blogIconImageProperty(Blog blog) {
        return fileToImageProperty(blogIconFile(blog), MISSING_IMAGE);
    }

    public ObjectProperty<Image> realWorldAppImageProperty(RealWorldApp app) {
        return fileToImageProperty(realWorldAppFile(app), MISSING_IMAGE);
    }

    public ObjectProperty<Image> realWorldAppLargeImageProperty(RealWorldApp app) {
        return fileToImageProperty(realWorldAppLargeFile(app), MISSING_IMAGE);
    }

    public ObjectProperty<Image> realWorldAppBannerImageProperty(RealWorldApp app) {
        return fileToImageProperty(realWorldAppBannerFile(app), MISSING_IMAGE);
    }

    public ObjectProperty<Image> blogPageLargeImageProperty(Blog blog) {
        return fileToImageProperty(blogPageLargeFile(blog), MISSING_IMAGE);
    }

    public ObjectProperty<Image> personImageProperty(Person person) {
        return fileToImageProperty(personFile(person), null);
    }

    public ObjectProperty<Image> memberImageProperty(Member member) {
        return fileToImageProperty(memberFile(member), null);
    }

    public ObjectProperty<Image> toolImageProperty(Tool tool) {
        return fileToImageProperty(toolFile(tool), null);
    }

    public ObjectProperty<Image> utilityImageProperty(Utility utility) {
        return fileToImageProperty(utilityFile(utility), null);
    }

    public ObjectProperty<Image> companyImageProperty(Company company) {
        return fileToImageProperty(companyFile(company), MISSING_IMAGE);
    }

    public ObjectProperty<Image> tutorialImageProperty(Tutorial tutorial) {
        return fileToImageProperty(tutorialFile(tutorial), MISSING_IMAGE);
    }

    public ObjectProperty<Image> tutorialImageLargeProperty(Tutorial tutorial) {
        return fileToImageProperty(tutorialLargeFile(tutorial), MISSING_IMAGE);
    }

    public ObjectProperty<Image> bookCoverImageProperty(Book book) {
        return fileToImageProperty(bookCoverFile(book), MISSING_IMAGE);
    }

    public ObjectProperty<Image> libraryImageProperty(Library library) {
        if (!library.isLogoAvailable()) {
            // original behaviour
            return new SimpleObjectProperty<>();
        }
        return fileToImageProperty(libraryFile(library), MISSING_IMAGE);
    }

    public ObjectProperty<Image> libraryImageProperty(Library library, String imagePath) {
        return fileToImageProperty(libraryFile(library, imagePath), MISSING_IMAGE);
    }

    public ObjectProperty<Image> libraryFeaturedImageProperty(Library library) {
        if (StringUtils.isBlank(library.getFeaturedImageName())) {
            return new SimpleObjectProperty<>();
        }
        return libraryImageProperty(library, library.getFeaturedImageName());
    }

    public ObjectProperty<Image> documentationImageProperty(Documentation doc) {
        return fileToImageProperty(documentationFile(doc), null);
    }

    /// A repository image, by its path under [DataRepository#BASE_URL]. The
    /// property holds the placeholder, if any, until the image arrives.
    public ObjectProperty<Image> fileToImageProperty(String path, Image placeholderImage) {
        if (StringUtils.isBlank(path)) {
            return new SimpleObjectProperty<>(placeholderImage);
        }
        return remoteImageProperty(DataRepository.BASE_URL + path, "", path, placeholderImage, 0, 0);
    }

    /*
     * REMOTE IMAGE SUPPORT.
     */

    public String youTubeImageURL(Video video) {
        return "https://img.youtube.com/vi/" + video.getId() + "/0.jpg";
    }

    public ObjectProperty<Image> youTubeImageProperty(Video video) {
        // 480 x 360
        return remoteImageProperty(youTubeImageURL(video), video.getId(), MISSING_VIDEO_IMAGE, 480, 360);
    }

    public ObjectProperty<Image> githubAvatarImageProperty(String loginName) {
        return remoteImageProperty("https://github.com/" + loginName + ".png", "?size=100", "github-" + loginName, MISSING_USER_IMAGE, 100, 100);
    }

    /// Any image on the web, by its URL; the property holds the placeholder until it arrives.
    public ObjectProperty<Image> remoteImageProperty(String url, Image placeholderImage) {
        return remoteImageProperty(url, "", url, placeholderImage, 0, 0);
    }

    /// The images of the pages seen most recently. Bounded, so browsing
    /// hundreds of people does not keep every avatar in memory; one that falls
    /// out is read back from `Storage`.
    private final Map<String, ObjectProperty<Image>> remoteImageCache =
            new java.util.LinkedHashMap<String, ObjectProperty<Image>>(64, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, ObjectProperty<Image>> eldest) {
                    return size() > 200;
                }
            };

    /// How long a stored image is shown before it is fetched again (in the
    /// background, with the stored one on screen meanwhile).
    private static final long IMAGE_MAX_AGE = 7L * 24 * 60 * 60 * 1000;

    private ObjectProperty<Image> remoteImageProperty(String baseURL, String photoKey, Image placeholderImage, int width, int height) {
        return remoteImageProperty(baseURL, "", photoKey, placeholderImage, width, height);
    }

    /// Fetches an image with Codename One's network layer, keeps its bytes in
    /// `Storage` so it is there offline and on the next start, and builds the
    /// JavaFX image from them. The JavaFX layer does not open `http:` URLs
    /// itself, and a phone should not refetch an avatar on every visit anyway.
    private ObjectProperty<Image> remoteImageProperty(String imageUrl, String append, String photoKey, Image placeholderImage, int width, int height) {
        if (StringUtils.isBlank(imageUrl) || StringUtils.isBlank(photoKey)) {
            return new SimpleObjectProperty<>(placeholderImage);
        }
        ObjectProperty<Image> known = remoteImageCache.get(photoKey);
        if (known != null) {
            return known;
        }
        ObjectProperty<Image> property = new SimpleObjectProperty<>(placeholderImage);
        remoteImageCache.put(photoKey, property);
        String storageKey = storageKey(photoKey);
        String stampKey = storageKey + ".time";
        boolean fresh = false;
        Object cached = Storage.getInstance().readObject(storageKey);
        if (cached instanceof byte[]) {
            Image image = new Image(new ByteArrayInputStream((byte[]) cached));
            if (image.isError()) {
                // Bytes that do not decode are dropped and fetched again.
                Storage.getInstance().deleteStorageFile(storageKey);
            } else {
                property.set(image);
                Object stamp = Storage.getInstance().readObject(stampKey);
                fresh = stamp instanceof Long && System.currentTimeMillis() - (Long) stamp < IMAGE_MAX_AGE;
            }
        }
        if (!fresh) {
            Rest.get(imageUrl + append).onErrorCodeBytes(r -> { }).onError(evt -> evt.consume()).fetchAsBytes(response -> {
                byte[] data = response.getResponseData();
                Image image = response.getResponseCode() == 200 && data != null && data.length > 0
                        ? new Image(new ByteArrayInputStream(data)) : null;
                if (image != null && !image.isError()) {
                    Storage.getInstance().writeObject(storageKey, data);
                    Storage.getInstance().writeObject(stampKey, Long.valueOf(System.currentTimeMillis()));
                    property.set(image);
                } else if (property.get() == placeholderImage) {
                    // Nothing to show: forget the attempt so the next visit
                    // tries again instead of keeping the placeholder all session.
                    remoteImageCache.remove(photoKey);
                }
            });
        }
        return property;
    }

    /// A flat `Storage` name: the key's hash, then its characters made safe.
    private static String storageKey(String key) {
        StringBuilder safe = new StringBuilder("img-").append(Integer.toHexString(key.hashCode())).append('-');
        for (int i = 0; i < key.length() && i < 60; i++) {
            char c = key.charAt(i);
            safe.append(Character.isLetterOrDigit(c) || c == '-' || c == '.' ? c : '_');
        }
        return safe.toString();
    }
}
