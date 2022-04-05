package GUI;

import Databases.DataBase;
import Databases.LoginData;
import Email.OtpGeneration;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class LoginWindow extends Application {
    private String chkUser, chkPassword, emailID;
    public static String username;
    private LoginData loginData;
    public Scene scene;

    public LoginWindow() {

    }

    public LoginWindow(String garbage) {
        start(new Stage());
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 50, 50, 50));

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(20, 20, 20, 30));

        GridPane gridPane = getGridPane();

        Label labelUsername = new Label("Username");
        final TextField textUsername = new TextField();
        Label labelPassword = new Label("Password");
        final PasswordField passwordField = new PasswordField();
        Button buttonLogin = new Button("Login");
        final Label labelMessage = new Label();
        Button buttonNewUser = new Button("New User");

        Hyperlink hyperlink = new Hyperlink("Forgot Password?");

        TextFlow flow = new TextFlow(hyperlink);
        textUsername.setText("username");
        passwordField.setText("password");

        gridPane.add(labelUsername, 0, 0);
        gridPane.add(textUsername, 1, 0);
        gridPane.add(labelPassword, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(buttonLogin, 1, 2);
        gridPane.add(buttonNewUser, 1, 3);
        gridPane.add(labelMessage, 1, 4);
        gridPane.add(flow, 1, 5);

        Text text = new Text("LOGIN WINDOW");

        borderPane.setId("bp");
        gridPane.setId("root");
        buttonLogin.setId("buttonLogin");
        text.setId("text");

        buttonLogin.setOnAction(e -> {
            chkUser = textUsername.getText();
            chkPassword = passwordField.getText();
            loginData = new LoginData(chkUser, chkPassword);
            if (DataBase.authenticateUser(loginData)) {
                username = chkUser;
                primaryStage.close();
                new TradingWindow();
            } else {
                labelMessage.setText("Invalid Credentials");
            }
            textUsername.setText("");
            passwordField.setText("");
        });

        buttonNewUser.setOnAction(e -> {
            primaryStage.close();
            new NewUser();
        });

        hyperlink.setOnAction(e -> {
            primaryStage.close();
            primaryStage.setScene(getNewScene(primaryStage));
            primaryStage.setResizable(false);
            primaryStage.show();
        });

        borderPane.setTop(hBox);
        borderPane.setCenter(gridPane);

        scene = new Scene(borderPane);
        primaryStage.setTitle("LoginWindow");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        scene.getStylesheets().add("GUI/darktheme.css");
        primaryStage.show();
    }

    private GridPane getGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        return gridPane;
    }

    private Scene getNewScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 50, 50, 50));

        GridPane gridPane = getGridPane();

        Label labelUsername = new Label("Username");
        final TextField textUsername = new TextField();
        Button buttonLogin = new Button("Generate OTP");
        Text errorMessage = new Text("Error in sending email try again");
        errorMessage.setFill(Color.RED);
        errorMessage.setVisible(false);

        Text flow = new Text("OTP will be sent to your registered email id");
        flow.setFill(Color.WHITE);
        gridPane.add(labelUsername, 0, 0);
        gridPane.add(textUsername, 1, 0);
        gridPane.add(errorMessage, 0, 1);
        gridPane.add(buttonLogin, 0, 2);

        borderPane.setCenter(gridPane);
        borderPane.setBottom(flow);

        buttonLogin.setOnAction(e -> {
            System.out.println(textUsername.getText());
            username = textUsername.getText();
            emailID = DataBase.getEmailId(textUsername.getText());
            if (OtpGeneration.sendAuthentication(emailID, OtpGeneration.getOTP())) {
                primaryStage.close();
                primaryStage.setScene(getReEnterPassword(primaryStage));
                primaryStage.show();
            } else
                errorMessage.setVisible(true);
        });

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("GUI/darktheme.css");
        return scene;
    }

    private Scene getReEnterPassword(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 50, 50, 50));

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(20, 20, 20, 30));

        GridPane gridPane = getGridPane();

        Label labelOTP = new Label("OTP");
        final PasswordField OTPField = new PasswordField();
        Label labelPassword = new Label("Set Password");
        final PasswordField passwordField = new PasswordField();
        Label confirmPassword = new Label("Confirm Password");
        final PasswordField confirmPasswordField = new PasswordField();
        Button setPassword = new Button("Set Password");
        final Label labelMessage = new Label();

        Text errorMessage = new Text("OTP entered is not matching");
        errorMessage.setFill(Color.RED);
        errorMessage.setVisible(false);

        Text confirmPasswordErr = new Text("Set and Confirm password should match");
        confirmPasswordErr.setFill(Color.RED);
        confirmPasswordErr.setVisible(false);

        gridPane.add(labelOTP, 0, 1);
        gridPane.add(OTPField, 1, 1);

        gridPane.add(labelPassword, 0, 2);
        gridPane.add(passwordField, 1, 2);

        gridPane.add(confirmPassword, 0, 3);
        gridPane.add(confirmPasswordField, 1, 3);

        gridPane.add(setPassword, 3, 4);
        gridPane.add(labelMessage, 2, 5);

        gridPane.add(errorMessage, 0, 6);


        borderPane.setTop(hBox);
        borderPane.setCenter(gridPane);

        setPassword.setOnAction(e -> {
            try {
                System.out.println(OTPField.getText());
                OtpGeneration.ifExists(Integer.parseInt(OTPField.getText()));
                DataBase.updateUser(confirmPasswordField.getText());
                primaryStage.close();
                new LoginWindow("");
            } catch (Exception ex) {
                errorMessage.setVisible(true);
            }
        });

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("GUI/darktheme.css");
        return scene;
    }
}
