package com.example.stickgame;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;


class Mythread extends Thread{

    private AnchorPane gameroot;
    Mythread(AnchorPane root){
        this.gameroot = root;
    }
    @Override
    public void run() {
        AnimationTimer rotationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try
                {
                    System.out.println("Showing background");
                    BackgroundFill backgroundFill = new BackgroundFill(javafx.scene.paint.Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
                    Background background = new Background(backgroundFill);
                    gameroot.setBackground(background);
                    gameroot.setPrefWidth(470);
                    gameroot.setPrefHeight(700);
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Error in display game");
                }
            }
        };
        rotationTimer.start();
//        System.out.println("Showing Background .....");
//        try
//        {
//
//            BackgroundFill backgroundFill = new BackgroundFill(javafx.scene.paint.Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
//            Background background = new Background(backgroundFill);
//            gameroot.setBackground(background);
//            gameroot.setPrefWidth(470);
//            gameroot.setPrefHeight(700);
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println("Error in display game");
//        }

    }
}

class Mythread2 extends Thread{
    private AnchorPane gameroot;
    Mythread2(AnchorPane root){
        this.gameroot = root;
    }
    @Override
    public void run() {

    }

}
public class MultithreadingGame {

    private int berries = 0;
    private AnchorPane gameroot = new AnchorPane();


    private Scenecontroller scenecontroller;
//    private SpawnPillars spawnPillars;

    private Gamebuild gamebuild ;
    private static Scene gamescene;
    public MultithreadingGame(Scenecontroller controller){
        scenecontroller = controller;
    }

    public void displaygame(){

        System.out.println("Entered Game...");

        Mythread thread1 = new Mythread(gameroot);
        thread1.start();


//        spawnPillars = new SpawnPillars();
//        spawnPillars.setGameroot(gameroot);
//        spawnPillars.firstspawn();

        gamescene = new Scene(gameroot,470,700);
        scenecontroller.switchScene(gamescene);
        scenecontroller.show();
    }


}
