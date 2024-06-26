package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.components.tiles.PersonTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobilePeopleCategoryPage extends MobileCategoryPageBase<Person> {

    public MobilePeopleCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected String getCategoryTitle() {
        return "People";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Person.class);
    }

    @Override
    protected Callback<Person, TileViewBase<Person>> getTileViewProvider() {
        return PersonTileView::new;
    }

    @Override
    protected String getSearchPrompText() {
        return "Search for a JFX person";
    }

    @Override
    protected ObservableList<Person> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getPeople());
    }
}
