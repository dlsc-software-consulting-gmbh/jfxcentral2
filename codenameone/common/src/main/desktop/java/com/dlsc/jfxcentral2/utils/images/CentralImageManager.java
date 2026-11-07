package com.dlsc.jfxcentral2.utils.images;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;

/// Preview images for the lists. The desktop build resized and re-encoded them
/// with JPro's image manager (through AWT, which a phone does not have); here
/// they are loaded as they are, in the background, and the image views scale them.
public class CentralImageManager {

    private CentralImageManager() {
    }

    /// A repository image by its path, empty until it arrives.
    public static ObjectProperty<Image> getPreviewImage(String path, boolean large) {
        return ImageManager.getInstance().fileToImageProperty(path, null);
    }

    public static ObjectProperty<Image> getRealWorldAppBannerImage2(RealWorldApp app) {
        return ImageManager.getInstance().realWorldAppBannerImageProperty(app);
    }

    public static ObjectProperty<Image> getBookCoverImage1(Book book) {
        return ImageManager.getInstance().bookCoverImageProperty(book);
    }

    public static ObjectProperty<Image> getBookCoverImage2(Book book) {
        return ImageManager.getInstance().bookCoverImageProperty(book);
    }
}
