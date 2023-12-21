package com.example.stickgame;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginPage {
    private Stage stage;
    private Scenecontroller scenecontroller;
    private User user;

    private static LoginPage instance;

    private static HomePage homePage;

    public LoginPage(Scenecontroller controller, User user){
        this.scenecontroller =controller;
        this.user = user;
    }
    public void login(){

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #f0f0f0;");

        // Username input
        Label usernameLabel = new Label("Username:");
        usernameLabel.setTextFill(Color.BLACK);
        usernameLabel.setFont(Font.font("Arial", 14));
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Enter your username");
        GridPane.setConstraints(usernameInput, 1, 0);

        // Password input
        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.BLACK);
        passwordLabel.setFont(Font.font("Arial", 14));
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter your password");
        GridPane.setConstraints(passwordInput, 1, 1);


        Label invalidUsername = new Label("!Invalid Username or Password");
        invalidUsername.setTextFill(Color.RED);
        invalidUsername.setFont(Font.font("Arial", 14));
        GridPane.setConstraints(invalidUsername, 2, 2);


        // Login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4285f4; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5px;");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setCursor(Cursor.OPEN_HAND);
        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
//            if(!checkuser(username,password)){
//
//            }
//            else{
//                User user = new User();
//                user.setPassword(password);
//                user.setUsername(username);
//
//            }
            try {
                homePage.display_home();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }


        });

        // Create account button
        Button createAccountButton = new Button("Create Account");
        createAccountButton.setStyle("-fx-background-color: #4285f4; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5px;");
        GridPane.setConstraints(createAccountButton, 1, 3);
        createAccountButton.setOnAction(e -> {

        });

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, createAccountButton);

        Scene loginpagescene = new Scene(grid, 470, 700);
//        try {
//            File file = new File("./saved/save.txt");
//            System.out.println(file);
//            FileWriter fileWriter = new FileWriter(file, true);
//            PrintWriter printWriter = new PrintWriter(fileWriter);
//            printWriter.println("kshitij");
//            printWriter.close();
//            System.out.println("Writing in file successful.");
//        } catch (IOException e) {
//            System.out.println(e);
//            e.printStackTrace();
//        }

        scenecontroller.switchScene(loginpagescene);
        scenecontroller.show();
    }


    public boolean checkuser(String username , String password ){
        File file = new File("./saved/users.txt");
        return true;
    }

    public static LoginPage getInstance(Scenecontroller controller, User user){
        if(instance == null){
            homePage = HomePage.getInstance(controller);
            instance = new LoginPage(controller,user);
        }
        return instance;
    }
}
