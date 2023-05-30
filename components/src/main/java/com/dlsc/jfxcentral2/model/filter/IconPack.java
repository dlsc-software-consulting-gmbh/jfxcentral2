package com.dlsc.jfxcentral2.model.filter;

import com.dlsc.jfxcentral2.model.IkonData;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;

public enum IconPack {
    ALL("Select All"),
    ANTDESIGNICONSFILLED("AntDesignIconsFilled"),
    ANTDESIGNICONSOUTLINED("AntDesignIconsOutlined"),
    BOOTSTRAPICONS("BootstrapIcons"),
    BOXICONSLOGOS("BoxiconsLogos"),
    BOXICONSREGULAR("BoxiconsRegular"),
    BOXICONSSOLID("BoxiconsSolid"),
    BPMN("Bpmn"),
    CAPTAINICON("Captainicon"),
    CARBONICONS("CarbonIcons"),
    CODICONS("Codicons"),
    COREUIBRANDS("CoreUiBrands"),
    COREUIFREE("CoreUiFree"),
    DASHICONS("Dashicons"),
    DEVICONS("Devicons"),
    ELUSIVE("Elusive"),
    ENTYPO("Entypo"),
    EVAICONS("Evaicons"),
    FEATHER("Feather"),
    FILEICONS("FileIcons"),
    FLUENTUIFILLEDAL("FluentUiFilledAL"),
    FLUENTUIFILLEDMZ("FluentUiFilledMZ"),
    FLUENTUIREGULARAL("FluentUiRegularAL"),
    FLUENTUIREGULARMZ("FluentUiRegularMZ"),
    FONTAWESOME("FontAwesome"),
    FONTAWESOMEBRANDS("FontAwesomeBrands"),
    FONTAWESOMEREGULAR("FontAwesomeRegular"),
    FONTAWESOMESOLID("FontAwesomeSolid"),
    FONTELICO("Fontelico"),
    FOUNDATION("Foundation"),
    HAWCONSFILLED("HawconsFilled"),
    HAWCONSSTROKE("HawconsStroke"),
    ICOMOON("Icomoon"),
    IKONLI("Ikonli"),
    IONICONS("Ionicons"),
    IONICONS4IOS("Ionicons4IOS"),
    IONICONS4LOGO("Ionicons4Logo"),
    IONICONS4MATERIAL("Ionicons4Material"),
    JAM("Jam"),
    LIGATURESYMBOLS("LigatureSymbols"),
    LINEAWESOMEBRANDS("LineAwesomeBrands"),
    LINEAWESOMEREGULAR("LineAwesomeRegular"),
    LINEAWESOMESOLID("LineAwesomeSolid"),
    LINECONS("Linecons"),
    MAKI("Maki"),
    MAKI2("Maki2"),
    MAPICONS("Mapicons"),
    MATERIAL("Material"),
    MATERIAL2AL("Material2AL"),
    MATERIAL2MZ("Material2MZ"),
    MATERIAL2OUTLINEDAL("Material2OutlinedAL"),
    MATERIAL2OUTLINEDMZ("Material2OutlinedMZ"),
    MATERIAL2ROUNDAL("Material2RoundAL"),
    MATERIAL2ROUNDMZ("Material2RoundMZ"),
    MATERIAL2SHARPAL("Material2SharpAL"),
    MATERIAL2SHARPMZ("Material2SharpMZ"),
    MATERIALDESIGN("MaterialDesign"),
    MATERIALDESIGNA("MaterialDesignA"),
    MATERIALDESIGNB("MaterialDesignB"),
    MATERIALDESIGNC("MaterialDesignC"),
    MATERIALDESIGND("MaterialDesignD"),
    MATERIALDESIGNE("MaterialDesignE"),
    MATERIALDESIGNF("MaterialDesignF"),
    MATERIALDESIGNG("MaterialDesignG"),
    MATERIALDESIGNH("MaterialDesignH"),
    MATERIALDESIGNI("MaterialDesignI"),
    MATERIALDESIGNJ("MaterialDesignJ"),
    MATERIALDESIGNK("MaterialDesignK"),
    MATERIALDESIGNL("MaterialDesignL"),
    MATERIALDESIGNM("MaterialDesignM"),
    MATERIALDESIGNN("MaterialDesignN"),
    MATERIALDESIGNO("MaterialDesignO"),
    MATERIALDESIGNP("MaterialDesignP"),
    MATERIALDESIGNQ("MaterialDesignQ"),
    MATERIALDESIGNR("MaterialDesignR"),
    MATERIALDESIGNS("MaterialDesignS"),
    MATERIALDESIGNT("MaterialDesignT"),
    MATERIALDESIGNU("MaterialDesignU"),
    MATERIALDESIGNV("MaterialDesignV"),
    MATERIALDESIGNW("MaterialDesignW"),
    MATERIALDESIGNX("MaterialDesignX"),
    MATERIALDESIGNY("MaterialDesignY"),
    MATERIALDESIGNZ("MaterialDesignZ"),
    MEDICONS("Medicons"),
    METRIZEICONS("MetrizeIcons"),
    MICRONS("Microns"),
    OCIICONS("Ociicons"),
    OCTICONS("Octicons"),
    OPENICONIC("Openiconic"),
    PAYMENTFONT("PaymentFont"),
    PRESTASHOPICONS("PrestaShopIcons"),
    REMIXICONAL("RemixiconAL"),
    REMIXICONMZ("RemixiconMZ"),
    RUNESTROICONS("Runestroicons"),
    SIMPLEICONS("SimpleIcons"),
    SIMPLELINEICONS("SimpleLineIcons"),
    SUBWAY("Subway"),
    THEMIFY("Themify"),
    TYPICONS("Typicons"),
    UNICONSLINE("UniconsLine"),
    UNICONSMONOCHROME("UniconsMonochrome"),
    UNICONSSOLID("UniconsSolid"),
    WEATHERICONS("WeatherIcons"),
    WEBSYMBOLS("Websymbols"),
    WHHGAL("WhhgAL"),
    WHHGMZ("WhhgMZ"),
    WIN10("Win10"),
    ZONDICONS("Zondicons");

    private final String description;
    private IkonData ikonData;

    IconPack(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public IkonData getIkonData() {
        if (ikonData == null) {
            ikonData = IkonliPackUtil.getInstance().getIkonData(description);
        }
        return ikonData;
    }

    @Override
    public String toString() {
        return description;
    }
}
