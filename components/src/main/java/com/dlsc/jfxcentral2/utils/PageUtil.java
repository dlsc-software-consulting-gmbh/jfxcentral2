package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Utility;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral.data.pull.PullRequest;

public class PageUtil {

    public static String getLink(View view) {
        return "/" + view.toString().toLowerCase();
    }

    public static String getLink(ModelObject obj) {
        return getLink(getViewOfObject(obj)) + "/" + obj.getId();
    }

    public static View getViewOfObject(Object obj) {
        if (obj instanceof PullRequest) {
            return View.OPENJFX;
        } else if (obj instanceof Person) {
            return View.PEOPLE;
        } else if (obj instanceof Tutorial) {
            return View.TUTORIALS;
        } else if (obj instanceof RealWorldApp) {
            return View.REAL_WORLD;
        } else if (obj instanceof Company) {
            return View.COMPANIES;
        } else if (obj instanceof Tool) {
            return View.TOOLS;
        } else if (obj instanceof Library) {
            return View.LIBRARIES;
        } else if (obj instanceof Blog) {
            return View.BLOGS;
        } else if (obj instanceof Book) {
            return View.BOOKS;
        } else if (obj instanceof Video) {
            return View.VIDEOS;
        } else if (obj instanceof Download) {
            return View.DOWNLOADS;
        } else if (obj instanceof Tip) {
            return View.TIPS;
        } else if (obj instanceof IkonliPack) {
            return View.ICONS;
        } else if (obj instanceof Utility) {
            return View.UTILITIES;
        } else if (obj instanceof LearnJavaFX) {
            return View.LEARN_JAVAFX;
        } else if (obj instanceof LearnMobile) {
            return View.LEARN_MOBILE;
        } else if (obj instanceof LearnRaspberryPi) {
            return View.LEARN_RASPBERRYPI;
        } else {
            return View.HOME;
        }
    }
}
