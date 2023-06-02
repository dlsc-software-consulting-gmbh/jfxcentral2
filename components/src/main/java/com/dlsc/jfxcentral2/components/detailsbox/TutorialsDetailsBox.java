package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.util.Callback;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class TutorialsDetailsBox extends DetailsBoxBase<Tutorial> {

    public TutorialsDetailsBox() {
        getStyleClass().add("tutorials-details-box");
        setTitle("TUTORIALS");
        setIkon(IkonUtil.tutorial);
        setVisitUrlProvider(Tutorial::getUrl);
    }

    @Override
    protected List<Node> createActionButtons(Tutorial tutorial) {
        Button visitBlogButton = new Button("VISIT TUTORIAL", new FontIcon(IkonUtil.link));
        LinkUtil.setExternalLink(visitBlogButton, getVisitUrlProvider().call(tutorial));
        return List.of(createDetailsButton(tutorial), visitBlogButton);
    }

    private final ObjectProperty<Callback<Tutorial, String>> visitUrlProvider = new SimpleObjectProperty<>(this, "onVisitTutorial");

    public Callback<Tutorial, String> getVisitUrlProvider() {
        return visitUrlProvider.get();
    }

    public ObjectProperty<Callback<Tutorial, String>> visitUrlProviderProperty() {
        return visitUrlProvider;
    }

    public void setVisitUrlProvider(Callback<Tutorial, String> visitUrlProvider) {
        this.visitUrlProvider.set(visitUrlProvider);
    }
}
