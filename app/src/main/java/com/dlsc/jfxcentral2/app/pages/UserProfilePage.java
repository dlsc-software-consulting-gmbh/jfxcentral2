package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.components.userprofile.UserProfileView;
import com.dlsc.jfxcentral2.model.Comment;
import com.dlsc.jfxcentral2.model.RegisteredUser;
import com.dlsc.jfxcentral2.model.SaveAndLikeModel;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserProfilePage extends PageBase {

    public UserProfilePage(ObjectProperty<Size> size) {
        super(size, TopMenuBar.Mode.LIGHT);
    }

    @Override
    public String title() {
        return "User Profile";
    }

    @Override
    public String description() {
        return "User profile data, saved items, password";
    }

    @Override
    public Node content() {
        // user profile view
        UserProfileView userProfileView = new UserProfileView();
        userProfileView.sizeProperty().bind(sizeProperty());
        userProfileView.getComments().setAll(createComments());
        userProfileView.getSaveAndLikeModels().setAll(createSavesAndLikes());
        userProfileView.setUser(new RegisteredUser(null, "Dirk Lemmermann", "dlemmermann@gmail.com"));

        // wrap in strip view
        StripView stripView = new StripView(userProfileView);
        stripView.sizeProperty().bind(sizeProperty());
        VBox.setVgrow(stripView, Priority.ALWAYS);

        return wrapContent(stripView);
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

    private List<SaveAndLikeModel> createSavesAndLikes() {
        List<Class<? extends ModelObject>> classList = List.of(Library.class, RealWorldApp.class, Person.class, Tool.class, Tutorial.class, Book.class);
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
}
