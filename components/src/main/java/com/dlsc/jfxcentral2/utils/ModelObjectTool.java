package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.News;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.images.CentralImageManager;
import com.jpro.webapi.WebAPI;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import org.kordamp.ikonli.Ikon;

import java.io.File;

public class ModelObjectTool {

    private ModelObjectTool() {
    }


    public static File getModelPreviewFile(ModelObject modelObject, boolean largerImageFirst) {
        // HERE
        if (modelObject instanceof Tool tool) {
            return ImageManager.getInstance().toolFile(tool);
        } else if (modelObject instanceof Blog blog) {
            return largerImageFirst ? ImageManager.getInstance().blogPageLargeFile(blog) : ImageManager.getInstance().blogIconFile(blog);
        } else if (modelObject instanceof Download download) {
            return ImageManager.getInstance().downloadBannerFile(download);
        } else if (modelObject instanceof Library library) {
            return ImageManager.getInstance().libraryFile(library);
        } else if (modelObject instanceof Video video) {
            return null;
            //return ImageManager.getInstance().youTubeImageProperty(video);
        } else if (modelObject instanceof Book book) {
            return ImageManager.getInstance().bookCoverFile(book);
        } else if (modelObject instanceof Tip tip) {
            return ImageManager.getInstance().tipBannerFile(tip);
        } else if (modelObject instanceof Company company) {
            return ImageManager.getInstance().companyFile(company);
        } else if (modelObject instanceof Person person) {
            return ImageManager.getInstance().personFile(person);
        } else if (modelObject instanceof Tutorial tutorial) {
            return largerImageFirst ? ImageManager.getInstance().tutorialLargeFile(tutorial) : ImageManager.getInstance().tutorialFile(tutorial);
        } else if (modelObject instanceof RealWorldApp app) {
            return ImageManager.getInstance().realWorldAppBannerFile(app);
        } else if (modelObject instanceof News news) {
            return ImageManager.getInstance().newsBannerFile(news);
        }

        return null;
    }

    /**
     * Returns the image property for the given model object.
     */
    public static ObjectProperty<Image> getModelPreviewImageProperty(ModelObject modelObject, boolean large) {
        if(modelObject instanceof Video video) {
            if(WebAPI.isBrowser()) {
                return new SimpleObjectProperty<>(WebAPI.createVirtualImage(ImageManager.getInstance().youTubeImageURL(video), 480,360));
            } else {
                return ImageManager.getInstance().youTubeImageProperty(video);
            }
        }
        File file = getModelPreviewFile(modelObject, large);

        if (file.exists()) {
            return new SimpleObjectProperty<>(CentralImageManager.getPreviewImage(file, large));
        } else {
            return new SimpleObjectProperty<>(null);
        }
    }

    public static String getModelLink(ModelObject modelObject) {
        if (modelObject instanceof Tool tool) {
            return "/tools/" + tool.getId();
        } else if (modelObject instanceof Blog blog) {
            return "/blogs/" + blog.getId();
        } else if (modelObject instanceof Download download) {
            return "/downloads/" + download.getId();
        } else if (modelObject instanceof Library library) {
            return "/libraries/" + library.getId();
        } else if (modelObject instanceof Video video) {
            return "/videos/" + video.getId();
        } else if (modelObject instanceof Book book) {
            return "/books/" + book.getId();
        } else if (modelObject instanceof Tip tip) {
            return "/tips/" + tip.getId();
        } else if (modelObject instanceof Company company) {
            return "/companies/" + company.getId();
        } else if (modelObject instanceof Person person) {
            return "/people/" + person.getId();
        } else if (modelObject instanceof Tutorial tutorial) {
            return "/tutorials/" + tutorial.getId();
        } else if (modelObject instanceof RealWorldApp app) {
            return "/showcases/" + app.getId();
        } else if (modelObject instanceof News news) {
            // There is currently no news page
            return "/news/" + news.getId();
        }

        return "";
    }

    public static Ikon getModelIcon(ModelObject object) {
        return IkonUtil.getModelIkon(object);
    }

    public static Ikon getModelIcon(Class<? extends ModelObject> clazz) {
        return IkonUtil.getModelIkon(clazz);
    }

    public static String getModelName(Class<? extends ModelObject> clazz) {
        if (clazz == Tool.class) {
            return "Tools";
        } else if (clazz == Blog.class) {
            return "Blogs";
        } else if (clazz == Download.class) {
            return "Downloads";
        } else if (clazz == Library.class) {
            return "Libraries";
        } else if (clazz == Video.class) {
            return "Videos";
        } else if (clazz == Book.class) {
            return "Books";
        } else if (clazz == Tip.class) {
            return "Tips";
        } else if (clazz == Company.class) {
            return "Companies";
        } else if (clazz == Person.class) {
            return "People";
        } else if (clazz == Tutorial.class) {
            return "Tutorials";
        } else if (clazz == RealWorldApp.class) {
            return "Apps";
        } else if (clazz == News.class) {
            return "News";
        }

        return "ModelObject";
    }


    public static String getModelName(ModelObject modelObject) {
        return getModelName(modelObject.getClass());
    }

}
