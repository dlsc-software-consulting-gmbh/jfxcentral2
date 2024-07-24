package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.mobile.components.LearnListCell;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

import java.util.Comparator;

public abstract class MobileLearnCategoryPage extends MobileCategoryPageBase<Learn> {

    public MobileLearnCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Learn.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for a lesson";
    }

    protected Callback<ListView<Learn>, ListCell<Learn>> cellFactory() {
        return param -> new LearnListCell();
    }

    @Override
    protected Comparator<Learn> getModelComparator() {
        return null;
    }

}
