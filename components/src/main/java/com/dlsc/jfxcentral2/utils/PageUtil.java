package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral.data.pull.PullRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PageUtil {
    private static final Logger LOGGER = LogManager.getLogger(PageUtil.class);

    public static View getViewFromURL(String url) {
        if (!url.startsWith("/")) return null;
        int secondSlash = url.indexOf("/", "/".length());
        if (secondSlash == -1) secondSlash = url.length();
        String viewString = url.substring("/".length(), secondSlash);
        int paramIndex = viewString.indexOf("?"); // cut of parameters
        if (paramIndex != -1) {
            viewString = viewString.substring(0, paramIndex);
        }
        try {
            return View.valueOf(viewString.toUpperCase());
        } catch (IllegalArgumentException ex) {
            LOGGER.warn("Failed to derive a view from URL: {}", url);
            return null;
        }
    }

    public static String getIdFromURL(String url) {
        if (!url.startsWith("/")) return null;
        int secondSlash = url.indexOf("/", "/".length());
        if (secondSlash == -1) return null;
        String idString = url.substring(secondSlash + 1);
        int paramIndex = idString.indexOf("?"); // cut of parameters
        if (paramIndex != -1) {
            idString = idString.substring(0, paramIndex);
        }
        return idString;
    }

    public static String getLink(View view) {
        return "/" + view.name().toLowerCase();
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
        } else {
            return View.HOME;
        }
    }

    public static Class getClassOfView(View view) {
        if (view == View.OPENJFX) return PullRequest.class;
        if (view == View.PEOPLE) return Person.class;
        if (view == View.TUTORIALS) return Tutorial.class;
        if (view == View.REAL_WORLD) return RealWorldApp.class;
        if (view == View.COMPANIES) return Company.class;
        if (view == View.TOOLS) return Tool.class;
        if (view == View.LIBRARIES) return Library.class;
        if (view == View.BLOGS) return Blog.class;
        if (view == View.BOOKS) return Book.class;
        if (view == View.VIDEOS) return Video.class;
        if (view == View.DOWNLOADS) return Download.class;
        if (view == View.TIPS) return Tip.class;
        if (view == View.ICONS) return IkonliPack.class;
        if (view == View.HOME) return null;
        String errorMessage = "No Class associated with the view: " + view;
        LOGGER.error(errorMessage);
        throw new RuntimeException(errorMessage);
    }
}
