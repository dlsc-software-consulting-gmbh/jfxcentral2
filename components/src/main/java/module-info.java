import org.kordamp.ikonli.IkonProvider;

open module com.dlsc.jfxcentral2.components {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.graphics;
    requires batik.dom;
    requires batik.svggen;
    requires com.github.weisj.jsvg;
    requires org.apache.commons.validator;
    requires org.apache.commons.logging;

    requires fr.brouillard.oss.cssfx;

    requires com.gluonhq.attach.browser;
    requires com.gluonhq.attach.display;
    requires com.gluonhq.attach.video;
    requires com.gluonhq.attachextended.yt;

    requires jfxcentral.data;
    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires com.dlsc.gemsfx;
    requires one.jpro.platform.mdfx;
    requires org.apache.commons.lang3;
    requires org.apache.commons.io;

    requires jpro.webapi;
    requires one.jpro.platform.routing.core;
    requires java.desktop;
    requires com.dlsc.jfxcentral2.iconfont;
    requires com.google.gson;

    requires one.jpro.platform.image.manager;
    requires one.jpro.platform.utils;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    // ikonli icon packs START
    requires org.kordamp.ikonli.antdesignicons;
    requires org.kordamp.ikonli.bootstrapicons;
    requires org.kordamp.ikonli.boxicons;
    requires org.kordamp.ikonli.bpmn;
    requires org.kordamp.ikonli.captainicon;
    requires org.kordamp.ikonli.carbonicons;
    requires org.kordamp.ikonli.codicons;
    requires org.kordamp.ikonli.coreui;
    requires org.kordamp.ikonli.dashicons;
    requires org.kordamp.ikonli.devicons;
    requires org.kordamp.ikonli.elusive;
    requires org.kordamp.ikonli.entypo;
    requires org.kordamp.ikonli.evaicons;
    requires org.kordamp.ikonli.feather;
    requires org.kordamp.ikonli.fileicons;
    requires org.kordamp.ikonli.fluentui;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.fontelico;
    requires org.kordamp.ikonli.foundation;
    requires org.kordamp.ikonli.hawcons;
    requires org.kordamp.ikonli.icomoon;
    requires org.kordamp.ikonli.ionicons;
    requires org.kordamp.ikonli.ionicons4;
    requires org.kordamp.ikonli.jam;
    requires org.kordamp.ikonli.ligaturesymbols;
    requires org.kordamp.ikonli.lineawesome;
    requires org.kordamp.ikonli.linecons;
    requires org.kordamp.ikonli.maki;
    requires org.kordamp.ikonli.maki2;
    requires org.kordamp.ikonli.mapicons;
    requires org.kordamp.ikonli.material;
    requires org.kordamp.ikonli.material2;
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.medicons;
    requires org.kordamp.ikonli.metrizeicons;
    requires org.kordamp.ikonli.microns;
    requires org.kordamp.ikonli.ociicons;
    requires org.kordamp.ikonli.octicons;
    requires org.kordamp.ikonli.openiconic;
    requires org.kordamp.ikonli.paymentfont;
    requires org.kordamp.ikonli.prestashopicons;
    requires org.kordamp.ikonli.remixicon;
    requires org.kordamp.ikonli.runestroicons;
    requires org.kordamp.ikonli.simpleicons;
    requires org.kordamp.ikonli.simplelineicons;
    requires org.kordamp.ikonli.subway;
    requires org.kordamp.ikonli.swing;
    requires org.kordamp.ikonli.themify;
    requires org.kordamp.ikonli.typicons;
    requires org.kordamp.ikonli.unicons;
    requires org.kordamp.ikonli.weathericons;
    requires org.kordamp.ikonli.websymbols;
    requires org.kordamp.ikonli.whhg;
    requires org.kordamp.ikonli.win10;
    requires org.kordamp.ikonli.zondicons;
    requires com.rometools.rome;
    requires java.prefs;
    requires jpro.utils.treeshowing;
    // ikonli icon packs END

    exports com.dlsc.jfxcentral2.components;
    exports com.dlsc.jfxcentral2.model;
    exports com.dlsc.jfxcentral2.components.skins;
    exports com.dlsc.jfxcentral2.utils;
    exports com.dlsc.jfxcentral2.events;
    exports com.dlsc.jfxcentral2.components.detailsbox;
    exports com.dlsc.jfxcentral2.components.tiles;
    exports com.dlsc.jfxcentral2.components.filters;
    exports com.dlsc.jfxcentral2.components.headers;
    exports com.dlsc.jfxcentral2.components.gridview;
    exports com.dlsc.jfxcentral2.components.overviewbox;
    exports com.dlsc.jfxcentral2.components.userprofile;
    exports com.dlsc.jfxcentral2.components.hamburger;
    exports com.dlsc.jfxcentral2.components.topcontent;
    exports com.dlsc.jfxcentral2.utilities.pathextractor;
    exports com.dlsc.jfxcentral2.utilities.effectdesigner;
    exports com.dlsc.jfxcentral2.utilities.effectdesigner.editor;
    exports com.dlsc.jfxcentral2.utilities.paintpicker.impl;
    exports com.dlsc.jfxcentral2.utilities.paintpicker.impl.datamodel;
    exports com.dlsc.jfxcentral2.utilities.effectdesigner.effect;
    exports com.dlsc.jfxcentral2.utils.images;

    uses IkonProvider;
}