package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.QuickLinkView;
import com.dlsc.jfxcentral2.components.QuickLinksContainer;
import com.dlsc.jfxcentral2.components.Size;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HelloQuickLinkContainer extends JFXCentralSampleBase {

    private QuickLinksContainer containerLarge;

    @Override
    protected Region createControl() {
        containerLarge = new QuickLinksContainer();
        containerLarge.setQuickLinks(FXCollections.observableList(generateQuickLinkList(Size.LARGE)));
        ScrollPane scrollPane = new ScrollPane(new StackPane(containerLarge));
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        return scrollPane;
    }

    public List<QuickLinkView.QuickLink> generateQuickLinkList(Size size) {
        if (size != Size.SMALL) {
            return getLargeOrMediumQuickLinks(size);
        } else {
            return getSmallQuickLinks();
        }
    }

    private List<QuickLinkView.QuickLink> getSmallQuickLinks() {
        List<QuickLinkView.QuickLink> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new QuickLinkView.QuickLink("Tools", "Lorem ipsum dolor sit amet, consectetur. Lorem ipsum dolor sit amet, consectetur", MaterialDesignT.TOOLS, "xxx url...", null));
        }
        return list;
    }

    private List<QuickLinkView.QuickLink> getLargeOrMediumQuickLinks(Size size) {
        ArrayList<QuickLinkView.QuickLink> list = new ArrayList<>();
        // Randomly generate 4 to 6 QuickLinks
        int count = new Random().nextInt(3) + 4;
        for (int i = 0; i < count; i++) {
            list.add(new QuickLinkView.QuickLink("Tools", i + " Lorem ipsum dolor sit amet, consectetur. Lorem ipsum dolor sit amet, consectetur", MaterialDesignT.TOOLS, "xxx url...", null));
        }
        //Randomly generate 1~3 QuickLinks for image placeholders
        int count2 = new Random().nextInt(3) + 1;
        List<String> imageUrlList = new ArrayList<>();
        //currently there are only 3 images for testing
        for (int i = 0; i < 3; i++) {
            imageUrlList.add("/com/dlsc/jfxcentral2/demo/components/images/quick-link-" + (size == Size.LARGE ? "lg" : "md") + i + ".png");
        }
        Collections.shuffle(imageUrlList);
        imageUrlList.subList(0, count2).forEach(url -> {
            list.add(new QuickLinkView.QuickLink(null, null, null, null, HelloQuickLinkContainer.class.getResource(url).toExternalForm()));
        });

        //If the aboev QuickLinks are less than 9, fill null
        for (int i = 0; i < 9 - count - count2; i++) {
            list.add(null);
        }
        // shuffle the links
        do {
            Collections.shuffle(list);
        } while (!checkArrayList(list));
        return list;
    }

    public boolean checkArrayList(ArrayList<QuickLinkView.QuickLink> list) {
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
                } else if (list.get(index).imageUrl() == null) {
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
                } else if (list.get(index).imageUrl() == null) {
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

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        sizeComboBox.sizeProperty().addListener((ob, ov, nv) -> {
            containerLarge.setQuickLinks(FXCollections.observableList(generateQuickLinkList(nv)));
            containerLarge.setSize(nv);
        });

        Button button = new Button("Refresh");
        button.setOnAction(event -> {
            containerLarge.setQuickLinks(FXCollections.observableList(generateQuickLinkList(sizeComboBox.getSize())));
        });

        return new VBox(new Label("Change Size"), sizeComboBox, new Label("Refresh Links"), button);
    }

    @Override
    public String getSampleName() {
        return "QuickLinkContainer";
    }
}
