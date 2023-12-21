package com.example.stickgame;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HomePage  {
    private AnchorPane root = new AnchorPane();
    private Scene home_scene ;
    private static HomePage instance;
    private Scenecontroller scenecontroller;
    private static Map<String, User> instances =
            new HashMap<String, User>();
    private static Game game ;
    private HomePage(Scenecontroller controller){
        scenecontroller = controller;
    }

    public void display_home() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        Volume sound = new Volume();
        sound.setFilePath("sounds/music1.wav");
        sound.play();
//        Stick HERO LABEL
        Image image2 = new Image("file:images/text2.png");
        ImageView stickgame = new ImageView(image2);
        stickgame.setX(157);
        stickgame.setY(80);

//        PLAY BUTTON
        Button play_button = new Button("PLAY");
        play_button.setPrefWidth(120 );
        play_button.setPrefHeight(120 );
        play_button.setLayoutX(180);
        play_button.setLayoutY(250);
        play_button.setShape(new Circle(40));

        play_button.setStyle(
                "-fx-background-color: #FF0000; " +
                        "-fx-text-fill: white;"+
                        "-fx-font-size: 30px;");
        play_button.setCursor(Cursor.HAND);
        play_button.setOnAction(e->{
            System.out.println("Clicked on Play Button");
            try {
                sound.stop();
                game.displaygame();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


//      SKIN BUTTON
        Button skin_button = new Button();
        skin_button.setShape(new Circle(10));
        skin_button.setPrefHeight(70);
        skin_button.setPrefWidth(70);
        skin_button.setLayoutX(40);
        skin_button.setLayoutY(400);
        skin_button.setCursor(Cursor.HAND);
        skin_button.setOnAction(e->{

        });
        skin_button.setGraphic(new ImageView(new Image("file:images/gwesfsgs.png")));
        skin_button.setPadding(Insets.EMPTY);

//      CHERRIES BUTTON
        Button cherries_button = new Button();
        cherries_button.setShape(new Circle(10));
        cherries_button.setPrefHeight(70);
        cherries_button.setPrefWidth(70);
        cherries_button.setLayoutX(40);
        cherries_button.setLayoutY(500);
        cherries_button.setCursor(Cursor.HAND);
        cherries_button.setOnAction(e->{

        });
        cherries_button.setGraphic(new ImageView(new Image("file:images/fcssdgdgdg.png")));
        cherries_button.setPadding(Insets.EMPTY);

//        VOLUME BUTTON
        Button volume_button = new Button();
        volume_button.setShape(new Circle(10));
        volume_button.setPrefHeight(70);
        volume_button.setPrefWidth(70);
        volume_button.setLayoutX(365);
        volume_button.setLayoutY(400);
        volume_button.setCursor(Cursor.HAND);
        volume_button.setOnAction(e->{

        });
        volume_button.setGraphic(new ImageView(new Image("file:images/srgg.png")));
        volume_button.setPadding(Insets.EMPTY);

//        SETTING BUTTON
        Button setting_button = new Button();
        setting_button.setShape(new Circle(10 ));
        setting_button.setPrefHeight(70);
        setting_button.setPrefWidth(70);
        setting_button.setLayoutX(365);
        setting_button.setLayoutY(500);
        setting_button.setCursor(Cursor.HAND);
        setting_button.setOnAction(e->{

        });
        setting_button.setGraphic(new ImageView(new Image("file:images/wefesgesg.png")));
        setting_button.setPadding(Insets.EMPTY);

//        ROOT-IMAGE
        try
        {
            Image image = new Image("file:images/aloo.png.", 470, 700, false, true);
            ImageView background = new ImageView(image);
            root.getChildren().add(background);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }



        root.getChildren().addAll(play_button , stickgame,skin_button , cherries_button , volume_button , setting_button);
        home_scene = new Scene(root,470,700);
        scenecontroller.switchScene(home_scene);
        scenecontroller.show();

    }


    public static HomePage getInstance(Scenecontroller scenecontroller) {
        if(instance == null) {
            instance = new HomePage(scenecontroller);
            game = new Game(scenecontroller);
//            game= new MultithreadingGame(scenecontroller);
        }
        return instance;
    }


    public String toString(){
        return "User class !!";
    }


}
