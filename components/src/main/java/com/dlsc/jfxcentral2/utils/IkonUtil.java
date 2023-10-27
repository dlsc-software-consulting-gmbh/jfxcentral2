package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.News;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Utility;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.carbonicons.CarbonIcons;
import org.kordamp.ikonli.codicons.Codicons;
import org.kordamp.ikonli.coreui.CoreUiBrands;
import org.kordamp.ikonli.coreui.CoreUiFree;
import org.kordamp.ikonli.dashicons.Dashicons;
import org.kordamp.ikonli.evaicons.Evaicons;
import org.kordamp.ikonli.fluentui.FluentUiFilledAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.hawcons.HawconsStroke;
import org.kordamp.ikonli.ionicons4.Ionicons4IOS;
import org.kordamp.ikonli.lineawesome.LineAwesomeSolid;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignN;
import org.kordamp.ikonli.remixicon.RemixiconAL;
import org.kordamp.ikonli.runestroicons.Runestroicons;
import org.kordamp.ikonli.simplelineicons.SimpleLineIcons;
import org.kordamp.ikonli.subway.Subway;
import org.kordamp.ikonli.themify.Themify;
import org.kordamp.ikonli.unicons.UniconsLine;
import org.kordamp.ikonli.whhg.WhhgMZ;
import org.kordamp.ikonli.win10.Win10;

public interface IkonUtil {

    Ikon link = JFXCentralIcon.OPEN_LINK;
    Ikon timer = JFXCentralIcon.TIMER;
    Ikon champion = JFXCentralIcon.CHAMPION;
    Ikon rockstar = HawconsStroke.ROCK_N_ROLL;
    Ikon floppy = LineAwesomeSolid.SAVE;
    Ikon arrowLeft = MaterialDesign.MDI_ARROW_LEFT;
    Ikon arrowRight = MaterialDesign.MDI_ARROW_RIGHT;
    Ikon website = JFXCentralIcon.WWW;
    Ikon edit = JFXCentralIcon.EDIT;
    Ikon delete = JFXCentralIcon.DELETE;
    Ikon close = JFXCentralIcon.COSS;
    Ikon people = JFXCentralIcon.PEOPLE;
    Ikon copy = MaterialDesign.MDI_CONTENT_COPY;

    Ikon play = JFXCentralIcon.PLAY;
    Ikon github = JFXCentralIcon.GITHUB;
    Ikon twitter = JFXCentralIcon.TWITTER;
    Ikon mastodon = CoreUiBrands.MASTODON;
    Ikon reddit = MaterialDesign.MDI_REDDIT;
    Ikon facebook = JFXCentralIcon.FACEBOOK;
    Ikon linkedin = JFXCentralIcon.LINKEDIN;
    Ikon mail = JFXCentralIcon.EMAIL;
    Ikon app = JFXCentralIcon.APP;
    Ikon blog = JFXCentralIcon.BLOGS;
    Ikon book = JFXCentralIcon.BOOKS;
    Ikon company = JFXCentralIcon.COMPANIES;
    Ikon download = JFXCentralIcon.DOWNLOAD;
    Ikon library = JFXCentralIcon.LIBRARIES;
    Ikon person = JFXCentralIcon.PEOPLE;
    Ikon tip = JFXCentralIcon.TIPS_TRICKS;
    Ikon tool = JFXCentralIcon.TOOLS;
    Ikon tutorial = JFXCentralIcon.TUTORIALS;
    Ikon video = JFXCentralIcon.VIDEOS;
    Ikon icons = MaterialDesign.MDI_EMOTICON;
    Ikon documentation = Codicons.BOOK;
    Ikon utility = FluentUiRegularAL.DOCUMENT_TOOLBOX_20;
    Ikon learn = SimpleLineIcons.GRADUATION;
    Ikon learnJavaFX = FontAwesomeBrands.JAVA;
    Ikon learnMobile = FontAwesomeSolid.MOBILE_ALT;
    Ikon learnRaspberryPi = WhhgMZ.RASPBERRYPI;
    Ikon news = MaterialDesignN.NEWSPAPER_VARIANT_OUTLINE;
    Ikon linkOfTheWeek = JFXCentralIcon.LINKS_OF_THE_WEEK;

    Ikon width = AntDesignIconsOutlined.COLUMN_WIDTH;
    Ikon height = AntDesignIconsOutlined.COLUMN_HEIGHT;
    Ikon radius = AntDesignIconsOutlined.RADIUS_UPRIGHT;
    Ikon color = Evaicons.COLOR_PALETTE_OUTLINE;
    Ikon offsetX = RemixiconAL.ARROW_LEFT_RIGHT_FILL;
    Ikon offsetY = RemixiconAL.ARROW_UP_DOWN_FILL;
    Ikon spread = Dashicons.EDITOR_DISTRACTIONFREE;
    Ikon blur = FluentUiFilledAL.BLUR_16;
    Ikon threshold = CarbonIcons.THRESHOLD;
    Ikon brightness = BootstrapIcons.BRIGHTNESS_HIGH;
    Ikon contrast = Ionicons4IOS.CONTRAST;
    Ikon saturation = BootstrapIcons.DROPLET_HALF;
    Ikon hue = UniconsLine.RAINBOW;
    Ikon opacity = CoreUiFree.OPACITY;
    Ikon blendMode = BoxiconsRegular.INTERSECT;
    Ikon scaleX = Win10.RESIZE_HORIZONTAL;
    Ikon scaleY = Win10.RESIZE_VERTICAL;
    Ikon wrap = Themify.LOOP;
    Ikon iterations = Material2MZ.WAVES;
    Ikon level = CarbonIcons.BRIGHTNESS_CONTRAST;
    Ikon disabled = Material.DO_NOT_DISTURB;
    Ikon x = CarbonIcons.X_AXIS;
    Ikon y = CarbonIcons.Y_AXIS;
    Ikon z = MaterialDesignA.AXIS_X_ARROW;
    Ikon azimuth = Runestroicons.COMPASS;
    Ikon elevation = BootstrapIcons.SUNRISE;
    Ikon image = CarbonIcons.IMAGE;
    Ikon angle = MaterialDesignA.ANGLE_ACUTE;
    Ikon llx = Subway.RECTANGLE_3;
    Ikon lly = Subway.RECTANGLE_3;
    Ikon lrx = Subway.RECTANGLE_4;
    Ikon lry = Subway.RECTANGLE_4;
    Ikon ulx = Subway.RECTANGLE_1;
    Ikon uly = Subway.RECTANGLE_1;
    Ikon urx = Subway.RECTANGLE_2;
    Ikon ury = Subway.RECTANGLE_2;
    Ikon fraction = CarbonIcons.REFLECT_VERTICAL;
    Ikon topOffset = AntDesignIconsOutlined.VERTICAL_ALIGN_TOP;
    Ikon font = Material2AL.FORMAT_SIZE;

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
        } else if (clazz == IkonliPack.class) {
            return icons;
        } else if (clazz == News.class) {
            return news;
        } else if (clazz == Documentation.class) {
            return documentation;
        } else if (clazz == Utility.class) {
            return utility;
        } else if (clazz == LearnJavaFX.class){
          return learnJavaFX;
        } else if (clazz == LearnMobile.class) {
            return learnMobile;
        } else if (clazz == LearnRaspberryPi.class) {
            return learnRaspberryPi;
        } else if (clazz == Learn.class) {
            return learn;
        } else {
            return null;
        }
    }

}
