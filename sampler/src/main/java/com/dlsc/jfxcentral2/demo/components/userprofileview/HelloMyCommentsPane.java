package com.dlsc.jfxcentral2.demo.components.userprofileview;

import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.userprofile.MyCommentsPane;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Comment;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloMyCommentsPane extends JFXCentralSampleBase {

    private MyCommentsPane myCommentsPane;

    @Override
    protected Region createControl() {
        myCommentsPane = new MyCommentsPane();
        myCommentsPane.getComments().setAll(createComments());
        return new ScrollPane(myCommentsPane);
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

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        myCommentsPane.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "MyCommentsPane";
    }
}
