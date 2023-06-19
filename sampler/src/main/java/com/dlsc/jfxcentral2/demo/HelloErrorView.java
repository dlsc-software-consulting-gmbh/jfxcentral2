package com.dlsc.jfxcentral2.demo;

import com.dlsc.jfxcentral2.components.ErrorView;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloErrorView extends JFXCentralSampleBase {

    private ErrorView errorView;

    @Override
    protected Region createControl() {

        errorView = new ErrorView();

        //errorView.setMessage("We could not reach the page you were searching for Error 404: page not found");
        //Image image = new Image(ErrorView.class.getResource("/com/dlsc/jfxcentral2/components/jfx-logo-primary-black.png").toExternalForm(), 50, 50, true, true, true);
        //errorView.setGraphic(new ImageView(image));
        //errorView.setOnClose(() -> System.out.println("close"));

        errorView.setActions(FXCollections.observableArrayList(
                new ErrorView.Action("IT'S OK", () -> System.out.println("OK"))
                //new ErrorView.Action("Warning", () -> System.out.println("warning"), ErrorView.BtnStyle.ORANGE),
                //new ErrorView.Action("Error", () -> System.out.println("error"), ErrorView.BtnStyle.RED)
        ));

        ScrollPane scrollPane = new ScrollPane(errorView);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        return scrollPane;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        errorView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "ErrorView";
    }
}
