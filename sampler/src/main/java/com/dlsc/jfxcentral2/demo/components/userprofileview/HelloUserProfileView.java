package com.dlsc.jfxcentral2.demo.components.userprofileview;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.userprofile.UserProfileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Comment;
import com.dlsc.jfxcentral2.model.RegisteredUser;
import com.dlsc.jfxcentral2.model.SaveAndLikeModel;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloUserProfileView extends JFXCentralSampleBase {

    private UserProfileView view;

    @Override
    protected Region createControl() {
        view = new UserProfileView();
        view.setUser(new RegisteredUser(null, "Dirk Lemmermann", "dlemmermann@gmail.com"));
        view.getSaveAndLikeModels().setAll(createModelList());
        view.getComments().setAll(createComments());

        return new ScrollPane(view);
    }

    private List<Comment> createComments() {
        Random random = new Random();
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Comment comment = new Comment("001abc", i+ " Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.", null, ZonedDateTime.now().minusDays(random.nextInt(60)), 125, false, false);
            comments.add(comment);
        }
        return comments;
    }

    private List<SaveAndLikeModel> createModelList() {
        List<Class<? extends ModelObject>> classList = List.of(Library.class, RealWorldApp.class, Person.class,
                Tool.class, Tutorial.class, Book.class);
        Random random = new Random();
        List<SaveAndLikeModel> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            SaveAndLikeModel model = new SaveAndLikeModel();
            model.setTitle("JavaFX Tip 31: Masking Clipping Alpha Channel Lorem ipsum dolor");
            Class<? extends ModelObject> clazz = classList.get(random.nextInt(classList.size()));
            model.setTypeClazz(clazz);
            if (random.nextBoolean()) {
                model.setSaved(true);
                model.setSavedDate(LocalDate.now().minusDays(random.nextInt(100)));
            }
            if (random.nextBoolean()) {
                model.setLiked(true);
                model.setLikedDate(LocalDate.now().minusDays(random.nextInt(100)));
            }

            list.add(model);
        }
        return list;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);
        view.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "UserProfileView";
    }
}
