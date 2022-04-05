package GUI;

import Data.Data;
import Databases.DataBase;
import Databases.LoginData;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class NewUser extends Application {
    private String newUsername;
    private String newPassword;
    private String newEmailID;
    private LoginData loginData;

    public NewUser() {
        start(new Stage());
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 50, 50, 50));

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(20, 20, 20, 30));

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        Label labelUsername = new Label("Set Username");
        final TextField textUsername = new TextField();
        Label labelPassword = new Label("Set Password");
        final PasswordField passwordField = new PasswordField();
        Label confirmPassword = new Label("Confirm Password");
        final PasswordField confirmPasswordField = new PasswordField();
        Label emailID = new Label("Email ID");
        final TextField email = new TextField();
        Button buttonLogin = new Button("Create Account");
        final Label labelMessage = new Label();

        gridPane.add(labelUsername, 0, 0);
        gridPane.add(textUsername, 1, 0);
        gridPane.add(labelPassword, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(confirmPassword, 0, 2);
        gridPane.add(confirmPasswordField, 1, 2);
        gridPane.add(emailID, 0, 3);
        gridPane.add(email, 1, 3);
        gridPane.add(buttonLogin, 3, 3);
        gridPane.add(labelMessage, 2, 4);

        borderPane.setTop(hBox);
        borderPane.setCenter(gridPane);

        buttonLogin.setOnAction(e -> {
            newUsername = textUsername.getText();
            newPassword = passwordField.getText();
            newEmailID = email.getText();
            loginData = new LoginData(newUsername, newPassword, newEmailID);
            if (DataBase.newUserCreation(loginData)) {
                primaryStage.close();
                new LoginWindow(" ");
            }
        });

        Scene scene = new Scene(borderPane);
        primaryStage.setTitle("New Account Creation");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        scene.getStylesheets().add("GUI/darktheme.css");
        primaryStage.show();
    }
}
