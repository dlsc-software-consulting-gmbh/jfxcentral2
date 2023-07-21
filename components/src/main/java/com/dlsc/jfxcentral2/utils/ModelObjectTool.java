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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import org.kordamp.ikonli.Ikon;

public class ModelObjectTool {

    private ModelObjectTool() {
    }

    /**
     * Returns the image property for the given model object.
     */
    public static ObjectProperty<Image> getModelPreviewImageProperty(ModelObject modelObject, boolean largerImageFirst) {
        if (modelObject instanceof Tool tool) {
            return ImageManager.getInstance().toolImageProperty(tool);
        } else if (modelObject instanceof Blog blog) {
            return largerImageFirst ? ImageManager.getInstance().blogPageLargeImageProperty(blog) : ImageManager.getInstance().blogIconImageProperty(blog);
        } else if (modelObject instanceof Download download) {
            return ImageManager.getInstance().downloadBannerImageProperty(download);
        } else if (modelObject instanceof Library library) {
            return ImageManager.getInstance().libraryImageProperty(library);
        } else if (modelObject instanceof Video video) {
            return ImageManager.getInstance().youTubeImageProperty(video);
        } else if (modelObject instanceof Book book) {
            return ImageManager.getInstance().bookCoverImageProperty(book);
        } else if (modelObject instanceof Tip tip) {
            return ImageManager.getInstance().tipBannerImageProperty(tip);
        } else if (modelObject instanceof Company company) {
            return ImageManager.getInstance().companyImageProperty(company);
        } else if (modelObject instanceof Person person) {
            return ImageManager.getInstance().personImageProperty(person);
        } else if (modelObject instanceof Tutorial tutorial) {
            return largerImageFirst ? ImageManager.getInstance().tutorialImageLargeProperty(tutorial) : ImageManager.getInstance().tutorialImageProperty(tutorial);
        } else if (modelObject instanceof RealWorldApp app) {
            return largerImageFirst ? ImageManager.getInstance().realWorldAppLargeImageProperty(app) : ImageManager.getInstance().realWorldAppImageProperty(app);
        } else if (modelObject instanceof News news) {
            return ImageManager.getInstance().newsBannerImageProperty(news);
        }

        return new SimpleObjectProperty<>(null);
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
