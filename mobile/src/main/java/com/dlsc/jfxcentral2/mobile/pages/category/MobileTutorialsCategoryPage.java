package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.components.tiles.TutorialTileView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobileTutorialsCategoryPage extends MobileCategoryPageBase<Tutorial> {

    public MobileTutorialsCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected String getCategoryTitle() {
        return "Tutorials";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Tutorial.class);
    }

    @Override
    protected Callback<Tutorial, TileViewBase<Tutorial>> getTileViewProvider() {
        return TutorialTileView::new;
    }

    @Override
    protected String getSearchPrompText() {
        return "Search for a tutorial";
    }

    @Override
    protected ObservableList<Tutorial> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository2.getInstance().getTutorials());
    }
}
