package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.model.ImageQuickLink;
import com.dlsc.jfxcentral2.model.NormalQuickLink;
import com.dlsc.jfxcentral2.model.QuickLink;
import com.dlsc.jfxcentral2.model.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class QuickLinksGenerator {

    private QuickLinksGenerator() {
    }

    public static List<QuickLink> generateQuickLinks(Size size) {
        if (size != Size.SMALL) {
            return getLargeOrMediumQuickLinks(size);
        } else {
            return getSmallQuickLinks();
        }
    }

    private static List<QuickLink> createQuickLinks(int count) {
        List<QuickLink> list = new ArrayList<>();
        List<ModelObject> dataList = createShuffledSublist(count);
        for (int i = 0; i < count; i++) {
            ModelObject mo = dataList.get(i);
            list.add(new NormalQuickLink(getTitle(mo), mo.getName(), IkonUtil.getModelIkon(mo), PageUtil.getLink(mo)));
        }
        return list;
    }

    private static List<QuickLink> getSmallQuickLinks() {
        List<QuickLink> list = createQuickLinks(4);
        Collections.shuffle(list);
        return list;
    }

    private static List<QuickLink> getLargeOrMediumQuickLinks(Size size) {
        // Randomly generate 4 to 6 QuickLinks
        int count = new Random().nextInt(3) + 4;
        List<QuickLink> list = createQuickLinks(count);

        //Randomly generate 1~3 QuickLinks for image placeholders
        int count2 = new Random().nextInt(3) + 1;
        List<String> imageUrlList = new ArrayList<>();

        //currently there are only 3 images for testing
        for (int i = 0; i < 3; i++) {
            imageUrlList.add("/com/dlsc/jfxcentral2/test/images/quick-link-" + (size == Size.LARGE ? "lg" : "md") + i + ".png");
        }

        Collections.shuffle(imageUrlList);
        imageUrlList.subList(0, count2).forEach(url -> {
            list.add(new ImageQuickLink(QuickLinksGenerator.class.getResource(url).toExternalForm()));
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

    private static String getTitle(ModelObject mo) {
        Objects.requireNonNull(mo, "model object can not be null");

        if (mo instanceof Video video) {
            if (video.getMinutes() > 0) {
                return video.getMinutes() + " min";
            }
            return "Video";
        } else if (mo instanceof RealWorldApp) {
            return "Showcase";
        } else if (mo instanceof Library) {
            return "Library";
        } else if (mo instanceof Tool) {
            return "Tool";
        } else if (mo instanceof Book) {
            return "Book";
        } else if (mo instanceof Tip) {
            return "Tip";
        }

        return null;
    }

    public static boolean checkArrayList(List<QuickLink> list) {
        int rows = 3;
        int cols = 3;
        // Cannot have 3 null values or 3 QuickLinks without images in the same row
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
        // Cannot have 3 null values or 3 QuickLinks without images in the same row
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

    private static List<ModelObject> createShuffledSublist(int size) {
        List<ModelObject> allModelObjects = new ArrayList<>();
        allModelObjects.addAll(DataRepository.getInstance().getTips());
        allModelObjects.addAll(DataRepository.getInstance().getLibraries());
        allModelObjects.addAll(DataRepository.getInstance().getRealWorldApps());
        allModelObjects.addAll(DataRepository.getInstance().getTools());
        allModelObjects.addAll(DataRepository.getInstance().getBooks());
        Collections.shuffle(allModelObjects);

        /*
         * For now we do not feature videos, tutorials, people, or blogs.
         */
//        allModelObjects.addAll(DataRepository.getInstance().getVideos());
//        allModelObjects.addAll(DataRepository.getInstance().getTutorials());
//        allModelObjects.addAll(DataRepository.getInstance().getPeople());
//        allModelObjects.addAll(DataRepository.getInstance().getBlogs());


        return allModelObjects.subList(0, size);
    }
}
