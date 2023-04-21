package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.model.Feature;
import com.dlsc.jfxcentral2.model.ImageQuickLink;
import com.dlsc.jfxcentral2.model.NormalQuickLink;
import com.dlsc.jfxcentral2.model.QuickLink;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenerateTestDataUtil {

    private GenerateTestDataUtil() {
    }

    public static List<Feature> generateFeatures() {
        return FXCollections.observableArrayList(
                new Feature("Video", "[1] Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Featured", "5 min video", MaterialDesign.MDI_TIMER, Feature.Type.VIDEO, new Image(GenerateTestDataUtil.class.getResource("/com/dlsc/jfxcentral2/test/images/feature-img.png").toExternalForm()), "url ..."),
                new Feature("Video", "[2] Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Featured", "5 min video", MaterialDesign.MDI_TIMER, Feature.Type.VIDEO, new Image(GenerateTestDataUtil.class.getResource("/com/dlsc/jfxcentral2/test/images/feature-img.png").toExternalForm()), "url ..."),
                new Feature("Video", "[3] Having Fun with Java and JavaFX on the Raspberry PI lorem ipsum whatever long text", "Featured", "5 min video", MaterialDesign.MDI_TIMER, Feature.Type.VIDEO, new Image(GenerateTestDataUtil.class.getResource("/com/dlsc/jfxcentral2/test/images/feature-img.png").toExternalForm()), "url ...")
        );
    }

    public static List<QuickLink> generateQuickLinks(Size size) {
        if (size != Size.SMALL) {
            return getLargeOrMediumQuickLinks(size);
        } else {
            return getSmallQuickLinks();
        }
    }

    private static List<QuickLink> getSmallQuickLinks() {
        List<QuickLink> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new NormalQuickLink("Tools", "Lorem ipsum dolor sit amet, consectetur. Lorem ipsum dolor sit amet, consectetur", MaterialDesignT.TOOLS, "xxx url..."));
        }
        Collections.shuffle(list);
        return list;
    }

    private static List<QuickLink> getLargeOrMediumQuickLinks(Size size) {
        ArrayList<QuickLink> list = new ArrayList<>();
        // Randomly generate 4 to 6 QuickLinks
        int count = new Random().nextInt(3) + 4;
        for (int i = 0; i < count; i++) {
            list.add(new NormalQuickLink("Tools", i + " Lorem ipsum dolor sit amet, consectetur. Lorem ipsum dolor sit amet, consectetur", MaterialDesignT.TOOLS, "xxx url..."));
        }
        //Randomly generate 1~3 QuickLinks for image placeholders
        int count2 = new Random().nextInt(3) + 1;
        List<String> imageUrlList = new ArrayList<>();
        //currently there are only 3 images for testing
        for (int i = 0; i < 3; i++) {
            imageUrlList.add("/com/dlsc/jfxcentral2/test/images/quick-link-" + (size == Size.LARGE ? "lg" : "md") + i + ".png");
        }
        Collections.shuffle(imageUrlList);
        imageUrlList.subList(0, count2).forEach(url -> {
            list.add(new ImageQuickLink(GenerateTestDataUtil.class.getResource(url).toExternalForm()));
        });

        //If the above QuickLinks are less than 9, fill null
        for (int i = 0; i < 9 - count - count2; i++) {
            list.add(null);
        }
        // shuffle the links
        do {
            Collections.shuffle(list);
        } while (!checkArrayList(list));
        return list;
    }

    public static boolean checkArrayList(ArrayList<QuickLink> list) {
        int rows = 3;
        int cols = 3;
        //Cannot have 3 null values or 3 QuickLinks without images in the same row
        for (int i = 0; i < rows; i++) {
            int normalView = 0;
            int imageView = 0;
            int nullCount = 0;
            for (int j = 0; j < cols; j++) {
                int index = i * cols + j;
                if (list.get(index) == null) {
                    nullCount++;
                } else if (list.get(index) instanceof NormalQuickLink) {
                    normalView++;
                } else {
                    imageView++;
                }
            }
            if (normalView == cols || nullCount == cols || imageView == cols) {
                return false;
            }
        }
        //Cannot have 3 null values or 3 QuickLinks without images in the same row
        for (int j = 0; j < cols; j++) {
            int nullCount = 0;
            int imageView = 0;
            int normalView = 0;
            for (int i = 0; i < rows; i++) {
                int index = i * cols + j;
                if (list.get(index) == null) {
                    nullCount++;
                } else if (list.get(index) instanceof NormalQuickLink) {
                    normalView++;
                } else {
                    imageView++;
                }
            }
            if (nullCount == rows || normalView == rows || imageView == rows) {
                return false;
            }
        }
        return true;
    }

}
