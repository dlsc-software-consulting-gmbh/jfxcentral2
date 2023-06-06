package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.JFXCentralUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.kordamp.ikonli.javafx.FontIcon;

public class LoginView extends PaneBase{

    private final StackPane background;
    private final StackPane descriptionStackPane;
    private final VBox formVBox;

    public LoginView() {
        getStyleClass().add("login-view");

        /*background*/
        Region bgImage = new Region();
        bgImage.getStyleClass().add("bg-image");

        Region bgOverlay = new Region();
        bgOverlay.getStyleClass().add("bg-overlay");

        background = new StackPane(bgImage, bgOverlay);
        background.getStyleClass().add("background");

        /*description stack-pane*/
        descriptionStackPane = new StackPane();
        descriptionStackPane.getStyleClass().add("description-stack-pane");

        Label title = new Label("Log In");
        title.getStyleClass().add("title");
        StackPane.setAlignment(title, Pos.TOP_LEFT);

        Text text1 = new Text("Enter your details to save ");
        text1.getStyleClass().add("description-text");

        FontIcon floppy = new FontIcon(IkonUtil.floppy);
        floppy.getStyleClass().add("floppy");

        Text text2 = new Text(" and like ");
        text2.getStyleClass().add("description-text");

        FontIcon heart = new FontIcon(JFXCentralIcon.HEART);
        heart.getStyleClass().add("heart");

        Text text3 = new Text(" pages, add comments ");
        text3.getStyleClass().add("description-text");

        FontIcon comment = new FontIcon(JFXCentralIcon.COMMENTS);
        comment.getStyleClass().add("comment");

        Text text4 = new Text(" and edit your personal details");
        text4.getStyleClass().add("description-text");

        TextFlow textFlow = new TextFlow(text1, floppy, text2, heart, text3, comment, text4);
        textFlow.getStyleClass().add("description-text-flow");
        StackPane.setAlignment(textFlow, Pos.BOTTOM_LEFT);
        descriptionStackPane.getChildren().addAll(title, textFlow);


        /*login form v-box*/
        ImageView googleLogo = new ImageView();
        googleLogo.getStyleClass().add("google-logo");
        Button googleLoginButton = new Button("LOG IN WITH GOOGLE", googleLogo);
        googleLoginButton.getStyleClass().add("button");
        googleLoginButton.setOnAction(event -> JFXCentralUtil.run(onGoogleLogin));

        Separator leftSeparator = new Separator();
        leftSeparator.getStyleClass().add("separator");
        HBox.setHgrow(leftSeparator, Priority.ALWAYS);
        Label orLabel = new Label("or");
        orLabel.getStyleClass().add("separator-label");
        Separator rightSeparator = new Separator();
        HBox.setHgrow(rightSeparator, Priority.ALWAYS);
        rightSeparator.getStyleClass().add("separator");
        HBox separator = new HBox(leftSeparator, orLabel, rightSeparator);
        separator.getStyleClass().add("h-box-separator");

        ImageView microsoftLogo = new ImageView();
        microsoftLogo.getStyleClass().add("microsoft-logo");
        Button microsoftLoginButton = new Button("LOG IN WITH MICROSOFT", microsoftLogo);
        microsoftLoginButton.getStyleClass().add("button");
        microsoftLoginButton.setOnAction(event -> JFXCentralUtil.run(onMicrosoftLogin));

        Text registerLabel = new Text("Don't you have an account? " );
        Hyperlink registerLink = new Hyperlink("Sign up now");
        registerLink.getStyleClass().add("link");
        registerLink.setOnAction(event -> JFXCentralUtil.run(onRegister));
        TextFlow register = new TextFlow(registerLabel, registerLink);
        register.getStyleClass().add("registration");
        formVBox = new VBox(googleLoginButton, separator, microsoftLoginButton, register);
        formVBox.getStyleClass().add("form-v-box");
    }

    @Override
    protected void layoutBySize() {
        super.layoutBySize();
        if(isLarge()){
            HBox hBox = new HBox(descriptionStackPane, formVBox);
            hBox.getStyleClass().add("h-box");
            setAlignment(hBox, Pos.CENTER);
            getChildren().setAll(hBox, background);
            hBox.toFront();
        }else{
            VBox vBox = new VBox(descriptionStackPane, formVBox);
            vBox.getStyleClass().add("v-box");
            setAlignment(vBox, Pos.CENTER);
            getChildren().setAll(vBox, background);
            vBox.toFront();
        }
    }

    private final ObjectProperty<Runnable> onGoogleLogin = new SimpleObjectProperty<>(this, "onGoogleLogin");
    public Runnable getOnGoogleLogin(){return onGoogleLogin.get();}
    public ObjectProperty<Runnable> onGoogleLoginProperty(){return onGoogleLogin;}
    public void setOnGoogleLogin(Runnable onGoogleLogin) {this.onGoogleLogin.set(onGoogleLogin);}

    private final ObjectProperty<Runnable> onMicrosoftLogin = new SimpleObjectProperty<>(this, "onMicrosoftLogin");
    public Runnable getOnMicrosoftLogin(){return onMicrosoftLogin.get();}
    public ObjectProperty<Runnable> onMicrosoftLoginProperty(){return onMicrosoftLogin;}
    public void setOnMicrosoftLogin(Runnable onMicrosoftLogin) {this.onMicrosoftLogin.set(onMicrosoftLogin);}

    private final ObjectProperty<Runnable> onRegister = new SimpleObjectProperty<>(this, "onRegister");
    public Runnable getOnRegister(){return onRegister.get();}
    public ObjectProperty<Runnable> onRegisterProperty(){return onRegister;}
    public void setOnRegister(Runnable onRegister) {this.onRegister.set(onRegister);}

}
