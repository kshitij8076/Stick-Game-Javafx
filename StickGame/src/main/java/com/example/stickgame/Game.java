package com.example.stickgame;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Game {
    private int berries;
    private AnchorPane gameroot = new AnchorPane();


    private User user;

    private Scenecontroller scenecontroller;
    private SpawnPillars spawnPillars;

    private static Scene gamescene;
    public Game(Scenecontroller controller){
        scenecontroller = controller;
    }
    public void displaygame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        System.out.println("Entered Game ");

        spawnPillars = new SpawnPillars(scenecontroller);
        spawnPillars.setGameroot(gameroot);
        spawnPillars.firstspawn();
        System.out.println("frist spawn completed --------------");


        try
        {
            BackgroundFill backgroundFill = new BackgroundFill(javafx.scene.paint.Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
            Background background = new Background(backgroundFill);
            gameroot.setBackground(background);
            gameroot.setPrefWidth(470);
            gameroot.setPrefHeight(700);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in display game");
        }


        gamescene = new Scene(gameroot,470,700);
        scenecontroller.switchScene(gamescene);
        scenecontroller.show();
    }

    public static Scene getGamescene(){
        return gamescene;
    }
}
