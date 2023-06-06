package com.dlsc.jfxcentral2.iconfont;

import org.kordamp.ikonli.Ikon;

public enum JFXCentralIcon implements Ikon {

    APP("jfx-icon-app", '\ue800'),
    ARROW_LEFT_RIGHT("jfx-icon-arrow-left-right", '\ue801'),
    BLOGS("jfx-icon-blogs", '\ue802'),
    BOOKS("jfx-icon-books", '\ue803'),
    BULLET_POINT("jfx-icon-bullet-point", '\ue804'),
    CALENDAR("jfx-icon-calendar", '\ue805'),
    CAMERA("jfx-icon-camera", '\ue806'),
    CHAMPION("jfx-icon-champion", '\ue807'),
    CHEVRON_BOTTOM("jfx-icon-chevron-bottom", '\ue808'),
    CHEVRON_MENU_BOTTOM("jfx-icon-chevron-menu-bottom", '\ue809'),
    CHEVRON_MENU_TOP("jfx-icon-chevron-menu-top", '\ue80a'),
    CHEVRON_TOP("jfx-icon-chevron-top", '\ue80b'),
    COMMENTS("jfx-icon-comments", '\ue80c'),
    COMPANIES("jfx-icon-companies", '\ue80d'),
    COORDINATES("jfx-icon-coordinates", '\ue80e'),
    COSS("jfx-icon-coss", '\ue80f'),
    DELETE("jfx-icon-delete", '\ue810'),
    DOWNLOAD("jfx-icon-download", '\ue811'),
    EDIT("jfx-icon-edit", '\ue812'),
    EMAIL("jfx-icon-email", '\ue813'),
    EYE_CLOSED("jfx-icon-eye-closed", '\ue814'),
    EYE_OPEN("jfx-icon-eye-open", '\ue815'),
    FACEBOOK("jfx-icon-facebook", '\ue816'),
    FLOPPY("jfx-icon-floppy", '\ue817'),
    GITHUB("jfx-icon-github", '\ue818'),
    HEART("jfx-icon-heart", '\ue819'),
    IMAGE("jfx-icon-image", '\ue81a'),
    INSTALL_LOCALLY("jfx-icon-install-locally", '\ue81b'),
    LIBRARIES("jfx-icon-libraries", '\ue81c'),
    LINKEDIN("jfx-icon-linkedin", '\ue81d'),
    LINKS_OF_THE_WEEK("jfx-icon-links-of-the-week", '\ue81e'),
    LINKS("jfx-icon-links", '\ue81f'),
    LOG_IN("jfx-icon-log-in", '\ue820'),
    LOG_OUT("jfx-icon-log-out", '\ue821'),
    MENU("jfx-icon-menu", '\ue822'),
    OPEN_LINK("jfx-icon-open-link", '\ue823'),
    OVERVIEW("jfx-icon-overview", '\ue824'),
    PEOPLE("jfx-icon-people", '\ue825'),
    PLAY("jfx-icon-play", '\ue826'),
    ROCKSTAR("jfx-icon-rockstar", '\ue827'),
    SEARCH("jfx-icon-search", '\ue828'),
    SHARE("jfx-icon-share", '\ue829'),
    SHOWCASES("jfx-icon-showcases", '\ue82a'),
    THUMB_UP_FILLED("jfx-icon-thumb-up-filled", '\ue82b'),
    THUMB_UP("jfx-icon-thumb-up", '\ue82c'),
    TIMER("jfx-icon-timer", '\ue82d'),
    TIPS_TRICKS("jfx-icon-tips-tricks", '\ue82e'),
    TOOLS("jfx-icon-tools", '\ue82f'),
    TUTORIALS("jfx-icon-tutorials", '\ue830'),
    TWITTER("jfx-icon-twitter", '\ue831'),
    VIDEOS("jfx-icon-videos", '\ue832'),
    WWW("jfx-icon-www", '\ue833'),
    YOUTUBE("jfx-icon-youtube", '\ue834');

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
