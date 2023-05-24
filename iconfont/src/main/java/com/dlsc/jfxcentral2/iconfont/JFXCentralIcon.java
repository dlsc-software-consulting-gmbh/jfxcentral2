package com.dlsc.jfxcentral2.iconfont;

import org.kordamp.ikonli.Ikon;

public enum JFXCentralIcon implements Ikon {
    APP("jfxc-icon-app", '\ue900'),
    ARROW_LEFT_RIGHT("jfxc-icon-arrow-left-right", '\ue901'),
    BLOGS("jfxc-icon-blogs", '\ue902'),
    BOOKS("jfxc-icon-books", '\ue903'),
    BULLET_POINT("jfxc-icon-bullet-point", '\ue904'),
    CALENDAR("jfxc-icon-calendar", '\ue905'),
    CAMERA("jfxc-icon-camera", '\ue906'),
    CHAMPION("jfxc-icon-champion", '\ue907'),
    CHEVRON_BOTTOM("jfxc-icon-chevron-bottom", '\ue908'),
    CHEVRON_MENU_BOTTOM("jfxc-icon-chevron-menu-bottom", '\ue909'),
    CHEVRON_MENU_TOP("jfxc-icon-chevron-menu-top", '\ue90a'),
    CHEVRON_TOP("jfxc-icon-chevron-top", '\ue90b'),
    COMMENTS("jfxc-icon-comments", '\ue90c'),
    COMPANIES("jfxc-icon-companies", '\ue90d'),
    COORDINATES("jfxc-icon-coordinates", '\ue90e'),
    COSS("jfxc-icon-coss", '\ue90f'),
    DELETE("jfxc-icon-delete", '\ue910'),
    DOWNLOAD("jfxc-icon-download", '\ue911'),
    EDIT("jfxc-icon-edit", '\ue912'),
    EMAIL("jfxc-icon-email", '\ue913'),
    EYE_CLOSED("jfxc-icon-eye-closed", '\ue914'),
    EYE_OPEN("jfxc-icon-eye-open", '\ue915'),
    FACEBOOK("jfxc-icon-facebook", '\ue916'),
    FLOPPY("jfxc-icon-floppy", '\ue917'),
    GITHUB("jfxc-icon-github", '\ue918'),
    HEART("jfxc-icon-heart", '\ue919'),
    IMAGE("jfxc-icon-image", '\ue91a'),
    INSTALL_LOCALLY("jfxc-icon-install-locally", '\ue91b'),
    LIBRARIES("jfxc-icon-libraries", '\ue91c'),
    LINKEDIN("jfxc-icon-linkedin", '\ue91d'),
    LINKS_OF_THE_WEEK("jfxc-icon-links-of-the-week", '\ue91e'),
    LINKS("jfxc-icon-links", '\ue91f'),
    LOG_IN("jfxc-icon-log-in", '\ue920'),
    LOG_OUT("jfxc-icon-log-out", '\ue921'),
    MENU("jfxc-icon-menu", '\ue922'),
    OPEN_LINK("jfxc-icon-open-link", '\ue923'),
    OVERVIEW("jfxc-icon-overview", '\ue924'),
    PEOPLE("jfxc-icon-people", '\ue925'),
    PLAY("jfxc-icon-play", '\ue926'),
    ROCKSTAR("jfxc-icon-rockstar", '\ue927'),
    SEARCH("jfxc-icon-search", '\ue928'),
    SHARE("jfxc-icon-share", '\ue929'),
    SHOWCASES("jfxc-icon-showcases", '\ue92a'),
    THIMB_UP_FILLED("jfxc-icon-thimb-up-filled", '\ue92b'),
    THUMB_UP("jfxc-icon-thumb-up", '\ue92c'),
    TIMER("jfxc-icon-timer", '\ue92d'),
    TIPS_TRICKS("jfxc-icon-tips-tricks", '\ue92e'),
    TOOLS("jfxc-icon-tools", '\ue92f'),
    TUTORIALS("jfxc-icon-tutorials", '\ue930'),
    TWITTER("jfxc-icon-twitter", '\ue931'),
    VIDEOS("jfxc-icon-videos", '\ue932'),
    WWW("jfxc-icon-www", '\ue933'),
    YOUTUBE("jfxc-icon-youtube", '\ue934');
    private final String description;
    private final int icon;




    JFXCentralIcon(String description, int icon) {
        this.description = description;
        this.icon = icon;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getCode() {
        return icon;
    }

    public static JFXCentralIcon findByDescription(String description) {
        for (JFXCentralIcon icon : values()) {
            if (icon.description.equals(description)) {
                return icon;
            }
        }
        throw new IllegalArgumentException("Icon not supported: " + description);
    }

}
