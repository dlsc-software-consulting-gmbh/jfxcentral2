package com.dlsc.jfxcentral2.icons;

import com.codename1.ui.FontImage;

/// The Material glyphs the app uses, from the font in the Codename One core.
/// Each one stands in for an icon the desktop build took from one of Ikonli's
/// packs (Material Design, Font Awesome, Codicons and others).
public enum MaterialIcon implements Ikon {

    ARROW_LEFT(FontImage.MATERIAL_ARROW_BACK),
    ARROW_RIGHT(FontImage.MATERIAL_ARROW_FORWARD),
    CHEVRON_LEFT(FontImage.MATERIAL_CHEVRON_LEFT),
    CHEVRON_RIGHT(FontImage.MATERIAL_CHEVRON_RIGHT),
    CONTENT_COPY(FontImage.MATERIAL_CONTENT_COPY),
    DOCUMENTATION(FontImage.MATERIAL_MENU_BOOK),
    EMOTICON(FontImage.MATERIAL_EMOJI_EMOTIONS),
    FORUM(FontImage.MATERIAL_FORUM),
    HEART(FontImage.MATERIAL_FAVORITE),
    HOME(FontImage.MATERIAL_HOME),
    LEARN(FontImage.MATERIAL_SCHOOL),
    LEARN_JAVAFX(FontImage.MATERIAL_COFFEE),
    LEARN_MOBILE(FontImage.MATERIAL_PHONE_ANDROID),
    LEARN_RASPBERRY_PI(FontImage.MATERIAL_DEVELOPER_BOARD),
    LIGHTBULB(FontImage.MATERIAL_LIGHTBULB_OUTLINE),
    MAGNIFY(FontImage.MATERIAL_SEARCH),
    NEWS(FontImage.MATERIAL_NEWSPAPER),
    PLAYLIST(FontImage.MATERIAL_PLAYLIST_ADD),
    REFRESH(FontImage.MATERIAL_REFRESH),
    ROCKSTAR(FontImage.MATERIAL_MUSIC_NOTE),
    SAVE(FontImage.MATERIAL_SAVE),
    SEARCH_INFO(FontImage.MATERIAL_MANAGE_SEARCH),
    SLIDE_SEARCH(FontImage.MATERIAL_TRAVEL_EXPLORE),
    TIMER(FontImage.MATERIAL_TIMER),
    UTILITY(FontImage.MATERIAL_HOME_REPAIR_SERVICE);

    private final char code;

    MaterialIcon(char code) {
        this.code = code;
    }

    @Override
    public String getDescription() {
        return "material-" + name().toLowerCase().replace('_', '-');
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getFontResource() {
        return "/material-design-font.ttf";
    }

    /// Resolves the few Ikonli names the code passes as strings.
    public static Ikon fromDescription(String description) {
        if ("mdi-heart".equals(description)) {
            return HEART;
        }
        for (MaterialIcon icon : values()) {
            if (icon.getDescription().equals(description)) {
                return icon;
            }
        }
        return null;
    }
}
