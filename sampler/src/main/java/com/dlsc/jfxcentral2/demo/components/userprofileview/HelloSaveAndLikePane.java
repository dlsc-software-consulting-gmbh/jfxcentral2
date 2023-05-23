package com.dlsc.jfxcentral2.demo.components.userprofileview;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.userprofile.SaveAndLikePane;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.SaveAndLikeModel;
import com.dlsc.jfxcentral2.model.Size;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloSaveAndLikePane extends JFXCentralSampleBase {

    private SaveAndLikePane saveAndLikePane;

    @Override
    protected Region createControl() {
        saveAndLikePane = new SaveAndLikePane();
        saveAndLikePane.getItems().setAll(createModelList());
        return new ScrollPane(saveAndLikePane);
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
        saveAndLikePane.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "SaveAndLikePane";
    }
}
