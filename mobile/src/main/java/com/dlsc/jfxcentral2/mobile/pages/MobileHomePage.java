package com.dlsc.jfxcentral2.mobile.pages;

import com.dlsc.gemsfx.SearchTextField;
import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.mobile.home.CategoryAdvancedView;
import com.dlsc.jfxcentral2.mobile.home.CategoryPreviewView;
import com.dlsc.jfxcentral2.mobile.home.CategoryView;
import com.dlsc.jfxcentral2.mobile.home.WeekLinksView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MobileHomePage extends VBox {

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public MobileHomePage(ObjectProperty<Size> size) {
        this();
        sizeProperty().bind(size);
    }

    public MobileHomePage() {
        getStyleClass().add("mobile-home-page");

        SearchTextField searchTextField = new SearchTextField(true);
        searchTextField.setPromptText("Search ...");

        CategoryAdvancedView categoryAdvancedView = new CategoryAdvancedView();
        categoryAdvancedView.sizeProperty().bind(sizeProperty());

        List<LinksOfTheWeek> linksOfTheWeek = DataRepository2.getInstance().getLinksOfTheWeek();

        WeekLinksView weekLinksView = new WeekLinksView();
        weekLinksView.sizeProperty().bind(sizeProperty());
        weekLinksView.setLinksOfTheWeek(linksOfTheWeek.get(linksOfTheWeek.size() - 1));

        CategoryPreviewView showCasePreviewView = createShowCasePreviewView();
        showCasePreviewView.sizeProperty().bind(sizeProperty());

        CategoryPreviewView tipsPreviewView = createTipsPreviewView();
        tipsPreviewView.sizeProperty().bind(sizeProperty());

        CategoryPreviewView peoplePreviewView = createPeoplePreviewView();
        peoplePreviewView.sizeProperty().bind(sizeProperty());

        CategoryView categoryView = new CategoryView();
        categoryView.sizeProperty().bind(sizeProperty());

        getChildren().addAll(searchTextField, categoryView, categoryAdvancedView, weekLinksView, showCasePreviewView, tipsPreviewView, peoplePreviewView);
    }

    // Size support

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    private CategoryPreviewView createShowCasePreviewView() {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Real World Apps");
        view.setShowAllUrl(PagePath.SHOWCASES);
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        List<RealWorldApp> apps = DataRepository2.getInstance().getRealWorldApps();

        List<RealWorldApp> randomApps = getRandomSample(apps, 2);
        for (RealWorldApp app : randomApps) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    app.getName(),
                    app.getSummary(),
                    ImageManager.getInstance().realWorldAppBannerImageProperty(app),
                    ModelObjectTool.getModelLink(app),
                    123,
                    57,
                    2048

            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    private CategoryPreviewView createPeoplePreviewView() {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("People");
        view.setShowAllUrl(PagePath.PEOPLE);
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        List<Person> people = DataRepository2.getInstance().getPeople();

        List<Person> randomPeople = getRandomSample(people, 2);
        for (Person person : randomPeople) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    person.getName(),
                    DataRepository2.getInstance().getPersonReadMe(person),
                    ImageManager.getInstance().personImageProperty(person),
                    ModelObjectTool.getModelLink(person),
                    399,
                    258,
                    3096

            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    private CategoryPreviewView createTipsPreviewView() {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Tips");
        view.setShowAllUrl(PagePath.TIPS);
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        List<Tip> tips = DataRepository2.getInstance().getTips();

        List<Tip> randomTips = getRandomSample(tips, 2);
        for (Tip tip : randomTips) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    tip.getName(),
                    tip.getSummary(),
                    ImageManager.getInstance().tipBannerImageProperty(tip),
                    ModelObjectTool.getModelLink(tip),
                    123,
                    57,
                    2048

            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    private <T extends ModelObject> List<T> getRandomSample(List<T> list, int sampleSize) {
        if (sampleSize > list.size()) {
            throw new IllegalArgumentException("Sample size must be less than or equal to the size of the list");
        }

        List<T> copy = new ArrayList<>(list);
        Collections.shuffle(copy.subList(0, sampleSize + 1));
        return copy.subList(0, sampleSize);
    }

}
