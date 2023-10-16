package com.dlsc.jfxcentral2.utils.images;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class CentralImageManager {

    private static final Logger LOGGER = LogManager.getLogger(CentralImageManager.class);
    private static final URL MISSING_IMAGE = com.dlsc.jfxcentral.data.ImageManager.class.getResource("missing-image.jpg");

    public static Image getPreviewImage(File file, boolean large) {
        try (FileInputStream in = new FileInputStream(file)) {
            return new Image(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getRealWorldAppBannerImage2(RealWorldApp app) {
        try (FileInputStream in = new FileInputStream(realWorldAppBannerImageFile(app))) {
            return new Image(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File realWorldAppBannerImageFile(RealWorldApp app) {
        return com.dlsc.jfxcentral.data.ImageManager.getInstance().realWorldAppBannerFile(app);
    }

    public static Image getDownloadImage(Download download) {
        File file = com.dlsc.jfxcentral.data.ImageManager.getInstance().downloadBannerFile(download);
        try (FileInputStream in = new FileInputStream(file)) {
            return new Image(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getBookCoverImage1(Book book) {
        try (FileInputStream in = new FileInputStream(bookCoverImageFile(book))) {
            return new Image(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getBookCoverImage2(Book book) {
        try (FileInputStream in = new FileInputStream(bookCoverImageFile(book))) {
            return new Image(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File bookCoverImageFile(Book book) {
        File f = new File(DataRepository.getInstance().getRepositoryDirectory(), "books/" + book.getId() + "/" + "cover.jpg");
        if (f.exists()) {
            return f;
        } else {
            return new File(Objects.requireNonNull(MISSING_IMAGE).getFile());
        }
    }

    public static File getImage(String folder, String filename) {
        File f = new File(DataRepository.getInstance().getRepositoryDirectory(), folder + filename);
        if (f.exists()) {
            return f;
        } else {
            File missing = new File(Objects.requireNonNull(MISSING_IMAGE).getFile());
            if (missing.exists()) {
                return missing;
            } else {
                String errorMessage = "MISSING IS MISSING: failed to find both the main and missing image files.";
                LOGGER.error(errorMessage);
                throw new RuntimeException(errorMessage);
            }
        }
    }
}
