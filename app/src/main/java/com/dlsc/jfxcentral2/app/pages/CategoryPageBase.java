package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import org.kordamp.ikonli.Ikon;

public abstract class CategoryPageBase<T extends ModelObject> extends PageBase {

    public CategoryPageBase(ObjectProperty<Size> size) {
        super(size, TopMenuBar.Mode.DARK);
    }

    protected CategoryHeader createCategoryHeader(String title, Ikon ikon) {
        CategoryHeader categoryHeader = new CategoryHeader();
        categoryHeader.setTitle(title);
        categoryHeader.setIkon(ikon);
        categoryHeader.sizeProperty().bind(sizeProperty());
        return categoryHeader;
    }
}
