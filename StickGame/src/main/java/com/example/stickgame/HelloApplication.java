package com.example.stickgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class HelloApplication extends Application {

//    Initializing all classes
    private User user;
    private Scenecontroller scenecontroller;
    private HomePage homePage;


    private LoginPage loginPage;



    @Override
    public void start(Stage primaryStage) throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        scenecontroller = Scenecontroller.getInstance(primaryStage);
        homePage = HomePage.getInstance(scenecontroller);
        homePage.display_home();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Stick Hero");
    }


    public static void main(String[] args) {
        launch();
    }
}