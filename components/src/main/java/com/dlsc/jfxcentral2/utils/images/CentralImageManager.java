package com.dlsc.jfxcentral2.utils.images;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import javafx.scene.image.Image;
import one.jpro.utils.imagemanager.ImageDefinition;
import one.jpro.utils.imagemanager.ImageManager;
import one.jpro.utils.imagemanager.imageencoder.ImageEncoder;
import one.jpro.utils.imagemanager.imageencoder.ImageEncoderJPG;
import one.jpro.utils.imagemanager.imagesource.ImageSource;
import one.jpro.utils.imagemanager.imagesource.ImageSourceFile;
import one.jpro.utils.imagemanager.imagetransformer.ImageTransformer;
import one.jpro.utils.imagemanager.imagetransformer.ImageTransformerFitHeight;
import one.jpro.utils.imagemanager.imagetransformer.ImageTransformerScaleToArea;
import one.jpro.utils.imagemanager.imagetransformer.ImageTransformerWH;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class CentralImageManager {
    private static final Logger LOGGER = LogManager.getLogger(CentralImageManager.class);
    private static final URL MISSING_IMAGE = com.dlsc.jfxcentral.data.ImageManager.class.getResource("missing-image.jpg");

    private static final ImageManager manager = ImageManager.getInstance();

    static int HEIGHT_PREVIEW_LARGE = 147;
    static int HEIGHT_PREVIEW_SMALL = 71;

    public static Image getPreviewImage(File file, boolean large) {
        ImageEncoder encoding = new ImageEncoderJPG(0.85);
        int height = large ? HEIGHT_PREVIEW_LARGE : HEIGHT_PREVIEW_SMALL;
        ImageTransformer transformer = new ImageTransformerFitHeight(height, 2);
        ImageSource source = new ImageSourceFile(file);
        return manager.loadImage(new ImageDefinition(source, transformer, encoding)).toFXImage();
    }

    public static Image getRealWorldAppBannerImage2(RealWorldApp app) {
        ImageEncoder encoding = new ImageEncoderJPG(0.85);
        ImageTransformer transformer = new ImageTransformerWH(413, 233, 2);
        ImageSource source = new ImageSourceFile(realWorldAppBannerImageFile(app));
        return manager.loadImage(new ImageDefinition(source, transformer, encoding)).toFXImage();
    }

    public static File realWorldAppBannerImageFile(RealWorldApp app) {
        return com.dlsc.jfxcentral.data.ImageManager.getInstance().realWorldAppBannerFile(app);
    }

    public static Image getDownloadImage(Download download) {
        ImageEncoder encoding = new ImageEncoderJPG(0.85);
        ImageTransformer transformer = new ImageTransformerWH(413, 233, 2);
        File file = com.dlsc.jfxcentral.data.ImageManager.getInstance().downloadBannerFile(download);
        ImageSource source = new ImageSourceFile(file);
        return manager.loadImage(new ImageDefinition(source, transformer, encoding)).toFXImage();
    }

    public static Image getBookCoverImage1(Book book) {
        ImageEncoder encoding = new ImageEncoderJPG(0.8);
        ImageTransformer transformer = new ImageTransformerScaleToArea(180, 250);
        ImageSource source = new ImageSourceFile(bookCoverImageFile(book));
        return manager.loadImage(new ImageDefinition(source, transformer, encoding)).toFXImage();
    }

    public static Image getBookCoverImage2(Book book) {
        ImageEncoder encoding = new ImageEncoderJPG(0.8);
        ImageTransformer transformer = new ImageTransformerScaleToArea(500, 500);
        ImageSource source = new ImageSourceFile(bookCoverImageFile(book));
        return manager.loadImage(new ImageDefinition(source, transformer, encoding)).toFXImage();
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
