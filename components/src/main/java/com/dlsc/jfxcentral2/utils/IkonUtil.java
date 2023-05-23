package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.materialdesign2.MaterialDesignB;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;
import org.kordamp.ikonli.materialdesign2.MaterialDesignN;
import org.kordamp.ikonli.materialdesign2.MaterialDesignS;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;
import org.kordamp.ikonli.materialdesign2.MaterialDesignV;

public interface IkonUtil {

    Ikon link = MaterialDesign.MDI_ARROW_TOP_RIGHT;
    Ikon timer = MaterialDesign.MDI_TIMER;
    Ikon champion = Material2OutlinedMZ.VERIFIED;
    Ikon rockstar = MaterialDesignS.STAR_FOUR_POINTS_OUTLINE;
    Ikon arrowLeft = MaterialDesign.MDI_ARROW_LEFT;
    Ikon arrowRight = MaterialDesign.MDI_ARROW_RIGHT;
    Ikon website = MaterialDesign.MDI_WEB;
    Ikon edit = MaterialDesign.MDI_PENCIL;
    Ikon delete = MaterialDesignT.TRASH_CAN_OUTLINE;
    Ikon close = MaterialDesign.MDI_CLOSE;
    Ikon people = Material2OutlinedAL.ACCOUNT_BOX;

    Ikon play = MaterialDesign.MDI_PLAY;
    Ikon github = MaterialDesign.MDI_GITHUB_CIRCLE;
    Ikon twitter = MaterialDesign.MDI_TWITTER;
    Ikon linkedin = MaterialDesign.MDI_LINKEDIN_BOX;
    Ikon mail = MaterialDesign.MDI_SEND;
    Ikon app = MaterialDesign.MDI_APPS;
    Ikon blog = MaterialDesign.MDI_NEWSPAPER;
    Ikon book = MaterialDesignB.BOOK_OPEN_VARIANT;
    Ikon company = Material2AL.BUSINESS;
    Ikon download = MaterialDesign.MDI_DOWNLOAD;
    Ikon library = Material2OutlinedAL.LIBRARY_BOOKS;
    Ikon person = MaterialDesign.MDI_ACCOUNT;
    Ikon tip = MaterialDesignL.LIGHTBULB_ON_OUTLINE;
    Ikon tool = MaterialDesignT.TOOLS;
    Ikon tutorial = MaterialDesign.MDI_PROJECTOR_SCREEN;
    Ikon video = MaterialDesignV.VIDEO_OUTLINE;
    Ikon news = MaterialDesignN.NEWSPAPER_VARIANT_OUTLINE;
    Ikon linkOfTheWeek = MaterialDesign.MDI_LINK;

    static Ikon getModelIkon(ModelObject mo) {
        return getModelIkon(mo.getClass());
    }

    static Ikon getModelIkon(Class<? extends ModelObject> clazz) {
        if (clazz == RealWorldApp.class) {
            return app;
        } else if (clazz == Blog.class) {
            return blog;
        } else if (clazz == Book.class) {
            return book;
        } else if (clazz == Company.class) {
            return company;
        } else if (clazz == Download.class) {
            return download;
        } else if (clazz == Library.class) {
            return library;
        } else if (clazz == Person.class) {
            return person;
        } else if (clazz == Tip.class) {
            return tip;
        } else if (clazz == Tool.class) {
            return tool;
        } else if (clazz == Tutorial.class) {
            return tutorial;
        } else if (clazz == Video.class) {
            return video;
        } else if (clazz == LinksOfTheWeek.class) {
            return linkOfTheWeek;
        } else {
            return null;
        }
    }

}
